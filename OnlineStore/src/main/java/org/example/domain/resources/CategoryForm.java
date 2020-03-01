package org.example.domain.resources;

import java.util.List;

public class CategoryForm {
    private Long id;
    private Long parentCategoryId;
    private String name;

    private List<String> categAttrs;
    private List<Boolean> checked;

    public CategoryForm() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategAttrs() {
        return categAttrs;
    }

    public List<Boolean> getChecked() {
        return checked;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategAttrs(List<String> categAttrs) {
        this.categAttrs = categAttrs;
    }

    public void setChecked(List<Boolean> checked) {
        this.checked = checked;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}