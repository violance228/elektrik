package com.konex.elektrik.Service.TypeSubdivision;

import com.konex.elektrik.Entity.TypeSubdivision;
import com.konex.elektrik.Repository.TypeSubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeSubdivisionServiceImpl implements TypeSubdivisionService {

    @Autowired
    private TypeSubdivisionRepository typeSubdivisionRepository;

    @Transactional
    public TypeSubdivision addTypeSubdivision(TypeSubdivision typeSubdivision) {

        return typeSubdivisionRepository.save(typeSubdivision);
    }

    @Transactional
    public void delete(Long id) {

        typeSubdivisionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TypeSubdivision getById(Long id) {

        return typeSubdivisionRepository.getOne(id);
    }

    @Transactional
    public TypeSubdivision editTypeSubdivision(TypeSubdivision typeSubdivision) {

        return typeSubdivisionRepository.saveAndFlush(typeSubdivision);
    }

    @Transactional(readOnly = true)
    public List<TypeSubdivision> getAll(Sort sort) {

        return typeSubdivisionRepository.findAll(sort);
    }
}
