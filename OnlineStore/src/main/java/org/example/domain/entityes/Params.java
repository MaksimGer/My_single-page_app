package org.example.domain.entityes;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Params")
public class Params {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Subject subject;

    @ManyToOne()
    @JoinColumn(name = "attribute_id")

    private Attribute attribute;

    private String value;

    // ------------------------------------------------------------------------------------------------
    public Params() {}

    public Params(Subject subject, Attribute attribute, String value) {
        this.subject = subject;
        this.attribute = attribute;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // ----------------------- Equals & HashCode --------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Params params = (Params) o;
        return  Objects.equals(subject, params.subject) &&
                Objects.equals(attribute, params.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, attribute);
    }
}