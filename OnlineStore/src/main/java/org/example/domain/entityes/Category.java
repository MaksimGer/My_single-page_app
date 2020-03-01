package org.example.domain.entityes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.example.domain.entityes.resolver.EntityIdResolver;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Categories")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category")
    private Category parentCategory;

    @ManyToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class
            , resolver= EntityIdResolver.class
            , scope=Category.class
            , property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Attribute> attributes = new HashSet<>();

    // ------------------------------------------------------------------------------------------------
    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Category() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    //---------------------- Attributes functions ----------------------------------
    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public boolean addAttribute(Attribute attribute){
        boolean add = attributes.add(attribute);
        if(!attribute.getCategories().contains(this)) {
            attribute.addCategory(this);
        }
        return add;
    }

    public void removeAttribute(Attribute attribute){
        attributes.remove(attribute);
        if(attribute.getCategories().contains(this)){
            attribute.removeCategory(this);
        }
    }

    public void removeAllAttributes(){
        attributes.clear();
    }

    // ----------------------- Equals & HashCode --------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        int hashCode = 23;

        hashCode = 31 * hashCode + (int)(id^(id>>>32));
        hashCode = 31 * hashCode + name.hashCode();

        return hashCode;
    }
}
