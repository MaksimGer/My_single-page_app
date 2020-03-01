package org.example.domain.entityes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.example.domain.entityes.resolver.EntityIdResolver;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Type")
    private byte type;

    @ManyToMany(mappedBy = "attributes")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class
            , resolver= EntityIdResolver.class
            , scope=Attribute.class
            , property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Category> categories = new HashSet<>();

    // ------------------------------------------------------------------------------------------------
    public Attribute() { }

    public Attribute(String name, byte type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte getType() { return  type; }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(byte type) { this.type = type; }

    //------------------------ Categories functions ------------------------------------
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public boolean addCategory(Category category){
        boolean add = categories.add(category);

        if(!category.getAttributes().contains(this)){
            category.addAttribute(this);
        }

        return add;
    }

    public void removeCategory(Category category){
        categories.remove(category);
        if(category.getAttributes().contains(this)){
            category.removeAttribute(this);
        }
    }

    public void removeAllCategories(){
        List<Category> categories = new ArrayList<>(getCategories());
        for(Category category: categories){
            category.removeAttribute(this);
        }
        this.categories.clear();
    }

    // ----------------------- Equals & HashCode --------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Attribute attribute = (Attribute) o;
        return Objects.equals(id, attribute.id) &&
                Objects.equals(name, attribute.name) &&
                Objects.equals(type, attribute.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}