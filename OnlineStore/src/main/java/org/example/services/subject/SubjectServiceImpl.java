package org.example.services.subject;

import org.example.domain.entityes.Category;
import org.example.domain.entityes.Subject;
import org.example.repos.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepo subjectRepo;

    @Override
    public List<Subject> findByCategory(Category category) {
        return subjectRepo.findByCategory(category);
    }

    @Override
    public void update(Long id, String name, Category category) {
        Subject subject = subjectRepo.findById(id).orElse(null);

        if(subject != null){
            subject.setName(name);
            subject.setCategory(category);
            subjectRepo.save(subject);
        }
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Subject subject) {
        subjectRepo.save(subject);
    }

    @Override
    public void delete(Subject subject) {
        subjectRepo.delete(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepo.findAll();
    }

    @Override
    public List<Subject> findAllByParent(Subject subject) {
        return subjectRepo.findAllByParent(subject);
    }

    @Override
    public void deleteAll(List<Subject> subjects) {
        subjectRepo.deleteAll(subjects);
    }
}