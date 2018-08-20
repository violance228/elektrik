package com.konex.elektrik.Service.Buttons;

import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Repository.ButtonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ButtonsServiceImpl implements ButtonsService {

    @Autowired
    ButtonsRepository buttonsRepository;

    @Transactional(readOnly = true)
    public Buttons getById(Long id) {

        return buttonsRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Buttons > getAll() {

        return buttonsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Buttons> getAllWhereParentIdIsNull(Sort sort) {

        return buttonsRepository.getAllByParentIdIsNull(sort);
    }

    @Transactional(readOnly = true)
    public List<Buttons> getAllWhereParentIdIsNotNull() {

        return buttonsRepository.getAllByParentIdIsNotNull();
    }


    @Transactional(readOnly = true)
    public List<Buttons> getAllByParentId(Long id) {

        return buttonsRepository.getAllByParentIdEquals(id);
    }

    @Transactional(readOnly = true)
    public Buttons getByEngName(String name) {

        return buttonsRepository.getButtonsByEngName(name);
    }
}
