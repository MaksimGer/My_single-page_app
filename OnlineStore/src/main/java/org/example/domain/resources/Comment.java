package org.example.domain.resources;

import org.example.domain.entityes.Subject;
import org.example.domain.entityes.User;

public class Comment {
    private Long id;

    private User author;

    private Subject product;

    private String text;

    public Comment() {
    }

    public Comment(User author, Subject product, String text) {
        this.author = author;
        this.product = product;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Subject getProduct() {
        return product;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Subject product) {
        this.product = product;
    }

    public void setText(String text) {
        this.text = text;
    }
}
