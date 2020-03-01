package org.example.repos;

import org.example.domain.entityes.Attribute;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttributeRepo extends CrudRepository<Attribute, Long> {
    Attribute findByName(String name);
    List<Attribute> findAll();
}
