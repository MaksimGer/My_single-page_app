package org.example.domain.entityes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Objects")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Subject parent;

    @Column(name = "Name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id")
    private Category category;

    // ------------------------------------------------------------------------------------------------
    public Long getId() { return this.id; }

    public Subject getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Subject() {
    }

    public Subject(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setParent(Subject parent) {
        this.parent = parent;
    }

    // ----------------------- Equals & HashCode --------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Subject subject = (Subject) o;

        return Objects.equals(id, subject.id) &&
                Objects.equals(name, subject.name) &&
                category.equals(subject.category);
    }

    @Override
    public int hashCode() {
        int hashCode = 23;

        hashCode = 31 * hashCode + (int)(id^(id>>>32));
        hashCode = 31 * hashCode + name.hashCode();
        hashCode = 31 * hashCode + category.hashCode();

        return hashCode;
    }
}
