package org.example.services.params;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Params;
import org.example.domain.entityes.Subject;

import java.util.List;

public interface ParamsService {
    void save(Params params);
    void deleteAll(Iterable<Params> params);
    List<Params> findAllByProduct(Subject subject);
    List<Params> findAllByAttribute(Attribute attribute);
    Params findByProductAndAttribute(Subject subject, Attribute attribute);
    void update(Long id, Subject subject, Attribute attribute, String value);
    void delete(Params params);
}