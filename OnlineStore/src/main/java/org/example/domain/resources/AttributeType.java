package org.example.domain.resources;

import java.util.Objects;

public class AttributeType {
    private String name;
    private byte id;

    public AttributeType() {
    }

    public AttributeType(String name, byte id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        AttributeType attributeType = (AttributeType) o;
        return Objects.equals(id, attributeType.id);
    }

    @Override
    public int hashCode() {
        int hashCode = 23;

        hashCode = 31 * hashCode + id;

        return hashCode;
    }
}
