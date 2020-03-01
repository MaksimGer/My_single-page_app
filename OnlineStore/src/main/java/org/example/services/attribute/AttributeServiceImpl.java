package org.example.services.attribute;

import org.example.domain.entityes.Attribute;
import org.example.repos.AttributeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeServiceImpl implements AttributeService {
    @Autowired
    private AttributeRepo attributeRepo;

    @Override
    public List<Attribute> findAll() {
        return attributeRepo.findAll();
    }

    @Override
    public Attribute save(Attribute attribute) {
        return attributeRepo.save(attribute);
    }

    @Override
    public Attribute findById(Long id) {
        Optional<Attribute> dbAttribute = attributeRepo.findById(id);

        return dbAttribute.orElse(null);
    }

    @Override
    public void update(Long id, String name, byte type) {
        Attribute attribute = attributeRepo.findById(id).orElse(null);


        if(attribute != null){
            attribute.setName(name);
            attribute.setType(type);
            attributeRepo.save(attribute);
        }
    }

    @Override
    public void deleteById(Long id) {
        attributeRepo.deleteById(id);
    }

    @Override
    public Attribute findByName(String name) {
        return attributeRepo.findByName(name);
    }

    @Override
    public void deleteAll() {
        attributeRepo.deleteAll();
    }
}