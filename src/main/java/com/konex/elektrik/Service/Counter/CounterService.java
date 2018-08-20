package com.konex.elektrik.Service.Counter;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.filter.CounterFilter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

//import com.konex.elektrik.Entity.Manufacturer;

public interface CounterService {

    Counter addCounter(Counter counter, Subdivision subdivision
//            , Manufacturer manufacturer
    );
    void delete(Long id);
    Counter getById(Long id);
    List<Counter> findByIdList(List<Long> counterIdList);
    Counter editCounter(Counter counter, Subdivision subdivision
//            , Manufacturer manufacturer
    );
    List<Counter> getAll(Sort sort);
    List<Counter> findCounterByCriteria(CounterFilter counterFilter);
    List<Counter> getAllCountersBySubdivision(Subdivision subdivision);

    List<Counter> getAll(Specification<Counter> counterSpecs, Sort sort);
//    List<Counter> findAllCountersIdBySubdivisionAndOrderByDate(Subdivision subdivision);
    List<Counter> getStatisticsBySpec(CounterFilter counterFilter);
}
