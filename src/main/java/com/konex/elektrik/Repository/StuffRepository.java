/*
 * Copyright (c) 2018. month -- 5. day -- 3.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Stuff;
import com.konex.elektrik.Entity.StuffType;
import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface StuffRepository extends JpaRepository<Stuff, Long> {

    Set<Stuff> findAllBySubdivisionsOrderByDateOfReceivingDesc(Subdivision subdivision);
    Set<Stuff> findAllByStuffTypesOrderByDateOfManufactureAsc(StuffType stuffType);
    int countAllByStuffTypes(StuffType stuffType);
}
