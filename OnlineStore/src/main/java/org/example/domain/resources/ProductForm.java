package org.example.domain.resources;

import java.util.List;

public class ProductForm {
    private Long id;
    private String name;

    private  Long categoryId;
    private List<String> prodAttrs;

    private List<String> values;

    public ProductForm() {
    }

    public ProductForm(Long id, String name, Long categoryId, List<String> attributes, List<String> values) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.prodAttrs = attributes;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getProdAttrs() {
        return prodAttrs;
    }

    public void setProdAttrs(List<String> prodAttrs) {
        this.prodAttrs = prodAttrs;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
