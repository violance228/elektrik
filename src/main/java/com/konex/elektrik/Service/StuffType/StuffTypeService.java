/*
 * Copyright (c) 2018. month -- 5. day -- 4.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.StuffType;

import com.konex.elektrik.Entity.StuffType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StuffTypeService {

    StuffType addStuffType(StuffType stuffType);
    void delete(Long id);
    StuffType getById(Long id);
    StuffType editStuffType(StuffType stuffType);
    List<StuffType> getAll(Sort sort);
}
