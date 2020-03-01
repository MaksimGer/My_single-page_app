package org.example.services.category;

import org.example.domain.entityes.Category;
import org.example.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category findById(Long id) {
        Optional<Category> dbCategory = categoryRepo.findById(id);

        return dbCategory.orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public void update(Long id, String name) {
        Category dbCategory = categoryRepo.findById(id).orElse(null);

        if(dbCategory != null){
            dbCategory.setName(name);
            categoryRepo.save(dbCategory);
        }
    }
}
