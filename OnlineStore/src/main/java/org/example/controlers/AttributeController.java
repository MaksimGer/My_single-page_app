package org.example.controlers;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Category;
import org.example.domain.entityes.Params;
import org.example.domain.resources.AttributeForm;
import org.example.services.attribute.AttributeService;
import org.example.services.category.CategoryService;
import org.example.services.params.ParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private ParamsService paramsService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Attribute> getAllAttributes() {
        return attributeService.findAll();
    }

    @RequestMapping(value = "/{attrID}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Attribute update(@PathVariable("attrID") Attribute attribute) {
        return attribute;
    }

    @RequestMapping(value = "/getAllByCategory/{categoryID}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Set<Attribute> getAttributesByCategory(@PathVariable("categoryID") Category category) {
        return category.getAttributes();
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Attribute addAttribute(@RequestBody AttributeForm attributeForm) {
        Attribute attrByName = attributeService.findByName(attributeForm.getName());

        if(attrByName == null){
            Attribute attribute = new Attribute(attributeForm.getName(), attributeForm.getType().getId());
            return attributeService.save(attribute);
        }
        else return null;
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Attribute updateAttribute(@RequestBody AttributeForm attributeForm) {
        Attribute attributeByName = attributeService.findByName(attributeForm.getName());

        if(attributeByName != null && !attributeByName.getId().equals(attributeForm.getId())){
            return null;
        }

        attributeService.update(attributeForm.getId(), attributeForm.getName(), attributeForm.getType().getId());

        return attributeService.findById(attributeForm.getId());
    }

    @RequestMapping(value = "/{attributeID}",
            method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public void deleteAttribute(@PathVariable("attributeID") Attribute attribute) {

        List<Params> paramsByAttr = paramsService.findAllByAttribute(attribute);

        paramsService.deleteAll(paramsByAttr);

        attribute.removeAllCategories();
        attributeService.save(attribute);
        attributeService.deleteById(attribute.getId());
    }
}