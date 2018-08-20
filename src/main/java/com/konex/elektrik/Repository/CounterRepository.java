package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long>, JpaSpecificationExecutor<Counter> {
    Counter findByName(String name);

    List<Counter> findByIdIn(List<Long> idList);
    List<Counter> getAllBySubdivisions(Subdivision subdivision);


}