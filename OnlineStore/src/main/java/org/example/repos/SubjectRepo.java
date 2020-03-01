package org.example.repos;

import org.example.domain.entityes.Category;
import org.example.domain.entityes.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends CrudRepository<Subject, Long> {
    List<Subject> findByCategory(Category category);
    List<Subject> findAll();
    Optional<Subject> findById(Long subjectId);
    List<Subject> findAllByParent(Subject subject);
}
