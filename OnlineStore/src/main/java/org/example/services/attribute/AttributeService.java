package org.example.services.attribute;

import org.example.domain.entityes.Attribute;

import java.util.List;

public interface AttributeService {
    List<Attribute> findAll();
    Attribute save(Attribute attribute);
    Attribute findById(Long id);
    void update(Long id, String name, byte type);
    void deleteById(Long id);
    Attribute findByName(String name);
    void deleteAll();
}
