/*
 * Copyright (c) 2018. month -- 5. day -- 3.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.Stuff;

import com.konex.elektrik.Entity.Stuff;
import com.konex.elektrik.Entity.StuffType;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Repository.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class StuffServiceImpl implements StuffService {

    @Autowired
    private StuffRepository stuffRepository;


    @Transactional
    public Stuff addStuff(Stuff stuff, StuffType stuffType, Subdivision subdivision) {

        stuff.setSubdivisions(subdivision);
        stuff.setStuffTypes(stuffType);
        return stuffRepository.save(stuff);
    }

    @Transactional
    public void delete(Long id) {

        stuffRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Stuff getById(Long id) {

        return stuffRepository.getOne(id);
    }

    @Transactional
    public Stuff editStuff(Stuff stuff) {

        return stuffRepository.saveAndFlush(stuff);
    }

    @Transactional(readOnly = true)
    public List<Stuff> getAll(Sort sort) {

        return stuffRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Stuff> findAllStuffBySubdivision(Subdivision subdivision) {

        return stuffRepository.findAllBySubdivisionsOrderByDateOfReceivingDesc(subdivision);
    }

    @Transactional(readOnly = true)
    public Set<Stuff> findAllStuffByStuffType(StuffType stuffType) {

        return stuffRepository.findAllByStuffTypesOrderByDateOfManufactureAsc(stuffType);
    }

    @Transactional(readOnly = true)
    public int countAllStuffByStuffType(StuffType stuffType) {

        return stuffRepository.countAllByStuffTypes(stuffType);
    }
}
