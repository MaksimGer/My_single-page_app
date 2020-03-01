package org.example.domain.resources;

public class AttributeForm {
    private Long id;
    private String name;
    private AttributeType type;

    public AttributeForm() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AttributeType getType() { return  type; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(AttributeType type) {this.type = type; }
}
