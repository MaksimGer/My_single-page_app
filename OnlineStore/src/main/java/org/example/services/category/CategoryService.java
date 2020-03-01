package org.example.services.category;

import org.example.domain.entityes.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAll();
    void save(Category category);
    Category findByName(String name);
    void deleteById(Long id);
    void update(Long id, String name);
}