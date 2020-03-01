package org.example.controlers;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Category;
import org.example.domain.entityes.Params;
import org.example.domain.entityes.Subject;
import org.example.domain.resources.CategoryForm;
import org.example.domain.resources.ProductInfo;
import org.example.exceptions.NotFoundException;
import org.example.services.attribute.AttributeService;
import org.example.services.category.CategoryService;
import org.example.services.params.ParamsService;
import org.example.services.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    ParamsService paramsService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @RequestMapping(value = "productCategories",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Category> getAllProductCategories() {
        List<Category> allCategories = categoryService.findAll();
        List<Category> productsCategories = new ArrayList<>();
        Category productCategory = categoryService.findById(6l);

        for(Category curCategory: allCategories){
            if(curCategory.getParentCategory() != null && curCategory.getParentCategory().equals(productCategory)){
                productsCategories.add(curCategory);
            }
        }
        return productsCategories;
    }

    @RequestMapping(value = "/{categoryID}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Category update(@PathVariable("categoryID") Category category) {
        return category;
    }

    @RequestMapping(value = "/getProducts/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public  List<ProductInfo> getProductsByCategory(@PathVariable("id") Long id){
        return getProductsInfo(id);
    }

    @RequestMapping(value = "/{id}/getAttributes",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Set<Attribute> getAttributesByCategory(@PathVariable("id") Category category){
        return category.getAttributes();
    }

    @RequestMapping(value = "/{id}/product_list",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Subject> getProductsByCategory(@PathVariable("id") Category category){
        return subjectService.findByCategory(category);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Category addCategory(@RequestBody CategoryForm categoryForm){
        String categoryName = categoryForm.getName();
        Long parentCategoryId = categoryForm.getParentCategoryId();
        Category parentCategory = null;

        if (parentCategoryId != -1){
            parentCategory = categoryService.findById(parentCategoryId);
        }

        Category newCategory = new Category(categoryName, parentCategory);
        categoryService.save(newCategory);

        for(int i = 0; i < categoryForm.getCategAttrs().size(); i++ ){
            if(categoryForm.getChecked().get(i)){
                String attrName = categoryForm.getCategAttrs().get(i);
                Attribute attrToAdd = attributeService.findByName(attrName);
                newCategory.addAttribute(attrToAdd);
            }
        }

        categoryService.save(newCategory);
        return newCategory;
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Category updateAttribute(@RequestBody CategoryForm categoryForm){
        String categoryName = categoryForm.getName();
        Category curCategory = categoryService.findById(categoryForm.getId());
        Long parentCategoryId = categoryForm.getParentCategoryId();
        Category parentCategory = null;

        if (parentCategoryId != -1){
            parentCategory = categoryService.findById(parentCategoryId);
        }

        curCategory.removeAllAttributes();
        curCategory.setName(categoryName);
        curCategory.setParentCategory(parentCategory);
        categoryService.save(curCategory);

        List<Subject> productsByCategory = subjectService.findByCategory(curCategory);

        for(int i = 0; i < categoryForm.getCategAttrs().size(); i++){
            Attribute attribute = attributeService.findByName(categoryForm.getCategAttrs().get(i));

            if(categoryForm.getChecked().get(i)){
                curCategory.addAttribute(attribute);

                for(Subject subject : productsByCategory){
                    List<Params> paramsByProduct = paramsService.findAllByProduct(subject);
                    Params addParams = new Params(subject, attribute, "-");

                    if(!paramsByProduct.contains(addParams)){
                        paramsService.save(addParams);
                        paramsByProduct.add(addParams);
                    }
                }
            }else{
                for(Subject subject : productsByCategory){
                    List<Params> paramsByProduct = paramsService.findAllByProduct(subject);
                    Params curParam = new Params(subject, attribute, "-");

                    if(paramsByProduct.contains(curParam)){
                        Params paramToDelete = paramsService.findByProductAndAttribute(subject, attribute);
                        paramsService.delete(paramToDelete);
                    }
                }
            }
        }
        categoryService.save(curCategory);

        return curCategory;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{categoryID}",
            method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public void deleteCategory(@PathVariable("categoryID") Category category){
        List<Subject> subjects = subjectService.findByCategory(category);

        for (Subject curSubject : subjects){
            Iterable<Params> allProductParams = paramsService.findAllByProduct(curSubject);
            paramsService.deleteAll(allProductParams);
            subjectService.delete(curSubject);
        }

        category.removeAllAttributes();
        categoryService.deleteById(category.getId());
    }

    // --------------------- secondary functions -----------------------------------------
    private List<ProductInfo> getProductsInfo(Long id) {

        Category curCategory = categoryService.findAll()
                .stream().filter(category -> category.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        List<ProductInfo> productInfoList = null;

        if(curCategory.getParentCategory() == categoryService.findById(6l)){
            List<Subject> products = subjectService.findByCategory(curCategory);
            Attribute priceAttr = attributeService.findById(3l);
            Attribute countAttr = attributeService.findById(4l);

            if(!products.isEmpty()) {
                productInfoList = new ArrayList<>();
                for (Subject subject : products) {
                    String priceStr = paramsService.findByProductAndAttribute(subject, priceAttr).getValue();
                    String countStr = paramsService.findByProductAndAttribute(subject, countAttr).getValue();

                    int price = Integer.parseInt(priceStr);
                    int count = Integer.parseInt(countStr);
                    int orderCount = 0;

                    ProductInfo productInfo = new ProductInfo(subject, price, count, orderCount);
                    productInfoList.add(productInfo);
                }
            }
        }

        return productInfoList;
    }
}