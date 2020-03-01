package org.example.services.subject;

import org.example.domain.entityes.Category;
import org.example.domain.entityes.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findByCategory(Category category);
    void update(Long id, String name, Category category);
    Subject findById(Long id);
    void save(Subject subject);
    void delete(Subject subject);
    List<Subject> findAll();
    List<Subject> findAllByParent(Subject subject);
    void deleteAll(List<Subject> subjects);
}
