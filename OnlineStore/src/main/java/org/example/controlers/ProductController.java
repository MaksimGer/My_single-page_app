package org.example.controlers;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Category;
import org.example.domain.entityes.Params;
import org.example.domain.entityes.Subject;
import org.example.domain.resources.ProductForm;
import org.example.domain.resources.ProductInfo;
import org.example.exceptions.NotFoundException;
import org.example.services.attribute.AttributeService;
import org.example.services.category.CategoryService;
import org.example.services.params.ParamsService;
import org.example.services.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ParamsService paramsService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttributeService attributeService;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Subject getProduct(@PathVariable("id") Long id){
        return subjectService.findAll()
                .stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/getProductParams/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Params> getProductParams(@PathVariable("id") Long id){
        Subject curSubject = subjectService.findAll()
                .stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        return paramsService.findAllByProduct(curSubject);
    }

    @RequestMapping(value = "/getAttr/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Set<Attribute> getProductParams(@PathVariable("id") Subject subject){

        return subject.getCategory().getAttributes();
    }

    @RequestMapping(value = "/{productId}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void deleteProduct(@PathVariable("productId") Subject curSubject){
        Iterable<Params> paramsByProduct = paramsService.findAllByProduct(curSubject);
        List<Subject> allByParent = subjectService.findAllByParent(curSubject);

        for(Subject comment: allByParent){
            List<Params> allByComment = paramsService.findAllByProduct(comment);
            paramsService.deleteAll(allByComment);
        }

        subjectService.deleteAll(allByParent);
        paramsService.deleteAll(paramsByProduct);
        subjectService.delete(curSubject);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Subject addProduct(@RequestBody ProductForm productForm){
        Long categoryId = productForm.getCategoryId();
        Category category = categoryService.findById(categoryId);

        String productName = productForm.getName();

        Subject newSubject = new Subject(productName, category);
        subjectService.save(newSubject);

        for(int i = 0; i < productForm.getProdAttrs().size(); i++){
            String attributeName = productForm.getProdAttrs().get(i);
            Attribute curAttr = attributeService.findByName(attributeName);

            String value = productForm.getValues().get(i);
            Params params = new Params(newSubject, curAttr, value);

            paramsService.save(params);
        }

        subjectService.save(newSubject);
        return newSubject;
    }

    @RequestMapping(value = "/productInfo/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductInfo getProductInfo(@PathVariable("productId") Subject subject){
        ProductInfo productInfo = null;

        if(subject != null && subject.getCategory().getParentCategory().equals(categoryService.findById(6l))){
            productInfo = new ProductInfo();
            Attribute priceAttr = attributeService.findById(3l);
            Attribute countAttr = attributeService.findById(4l);

            String priceStr = paramsService.findByProductAndAttribute(subject, priceAttr).getValue();
            String countStr = paramsService.findByProductAndAttribute(subject, countAttr).getValue();

            int price = Integer.parseInt(priceStr);
            int count = Integer.parseInt(countStr);

            productInfo.setProduct(subject);
            productInfo.setPrice(price);
            productInfo.setCount(count);
        }

        return productInfo;
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Subject updateProduct(@RequestBody ProductForm productForm){
        Subject curSubject = subjectService.findById(productForm.getId());
        curSubject.setName(productForm.getName());

        subjectService.save(curSubject);

        for(int i = 0; i < productForm.getProdAttrs().size(); i++){
            String attributeName = productForm.getProdAttrs().get(i);
            Attribute curAttr = attributeService.findByName(attributeName);

            Params params = paramsService.findByProductAndAttribute(curSubject, curAttr);
            params.setValue(productForm.getValues().get(i));

            paramsService.save(params);
        }

        return curSubject;
    }
}