package org.example.services.params;

import org.example.domain.entityes.Attribute;
import org.example.domain.entityes.Params;
import org.example.domain.entityes.Subject;
import org.example.repos.ParamsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamsServiceImpl implements ParamsService {
    @Autowired
    private ParamsRepo paramsRepo;

    @Override
    public void save(Params params) {
        paramsRepo.save(params);
    }

    @Override
    public void deleteAll(Iterable<Params> params) {
        paramsRepo.deleteAll(params);
    }

    @Override
    public List<Params> findAllByProduct(Subject subject) {
        return paramsRepo.findAllBySubject(subject);
    }

    @Override
    public List<Params> findAllByAttribute(Attribute attribute) {
        return paramsRepo.findAllByAttribute(attribute);
    }

    @Override
    public Params findByProductAndAttribute(Subject subject, Attribute attribute) {
        return paramsRepo.findBySubjectAndAttribute(subject, attribute);
    }

    @Override
    public void update(Long id, Subject subject, Attribute attribute, String value) {
        Params param = paramsRepo.findById(id).orElse(null);

        if(param != null){
            param.setSubject(subject);
            param.setAttribute(attribute);
            param.setValue(value);
            paramsRepo.save(param);
        }
    }

    @Override
    public void delete(Params params) {
        paramsRepo.delete(params);
    }
}