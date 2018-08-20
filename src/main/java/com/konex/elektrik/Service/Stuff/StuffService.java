/*
 * Copyright (c) 2018. month -- 5. day -- 3.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.Stuff;

import com.konex.elektrik.Entity.Stuff;
import com.konex.elektrik.Entity.StuffType;
import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface StuffService {

    Stuff addStuff(Stuff stuff, StuffType stuffType, Subdivision subdivision);
    void delete(Long id);
    Stuff getById(Long id);
    Stuff editStuff(Stuff stuff);
    List<Stuff> getAll(Sort sort);
    Set<Stuff> findAllStuffBySubdivision(Subdivision subdivision);
    Set<Stuff> findAllStuffByStuffType(StuffType stuffType);
    int countAllStuffByStuffType(StuffType stuffType);
}
