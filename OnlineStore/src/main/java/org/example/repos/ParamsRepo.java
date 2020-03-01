package org.example.repos;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Params;
import org.example.domain.entityes.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParamsRepo extends CrudRepository<Params, Long> {
    List<Params> findAllBySubject(Subject subject);
    List<Params> findAllByAttribute(Attribute attribute);
    Params findBySubjectAndAttribute(Subject subject, Attribute attribute);
}
