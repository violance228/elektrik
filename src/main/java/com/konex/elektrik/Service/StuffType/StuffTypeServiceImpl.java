/*
 * Copyright (c) 2018. month -- 5. day -- 4.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.StuffType;

import com.konex.elektrik.Entity.StuffType;
import com.konex.elektrik.Repository.StuffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StuffTypeServiceImpl implements StuffTypeService {

    @Autowired
    private StuffTypeRepository stuffTypeRepository;

    @Transactional
    public StuffType addStuffType(StuffType stuffType) {

        return stuffTypeRepository.save(stuffType);
    }

    @Transactional
    public void delete(Long id) {

        stuffTypeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StuffType getById(Long id) {

        return stuffTypeRepository.getOne(id);
    }

    @Transactional
    public StuffType editStuffType(StuffType stuffType) {

        return stuffTypeRepository.saveAndFlush(stuffType);
    }

    @Transactional(readOnly = true)
    public List<StuffType> getAll(Sort sort) {

        return stuffTypeRepository.findAll();
    }
}
