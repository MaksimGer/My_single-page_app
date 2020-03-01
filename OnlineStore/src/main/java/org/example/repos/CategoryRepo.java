package org.example.repos;

import org.example.domain.entityes.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    Category findByName(String name);
    Optional<Category> findById(Long id);
    List<Category> findAll();
}
