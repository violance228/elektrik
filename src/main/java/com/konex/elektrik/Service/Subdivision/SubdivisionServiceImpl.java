package com.konex.elektrik.Service.Subdivision;

import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Repository.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    @Autowired
    private SubdivisionRepository subdivisionRepository;

    @Transactional
    public Subdivision addSubdivision(Subdivision subdivision) {

        return subdivisionRepository.save(subdivision);
    }

    @Transactional
    public void delete(Long id) {

        subdivisionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Subdivision getById(Long id) {

        return subdivisionRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public Subdivision findByName(Long name) {

        return subdivisionRepository.findFirstByName(name);
    }

    @Transactional
    public Subdivision editSubdivision(Subdivision subdivision) {

        return subdivisionRepository.saveAndFlush(subdivision);
    }

    @Transactional(readOnly = true)
    public List<Subdivision> getAll(Sort sort) {

        return subdivisionRepository.findAll(sort);
    }

}
