package com.konex.elektrik.Service.Counter;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Repository.CounterRepository;
import com.konex.elektrik.Specification.CounterSpecs;
import com.konex.elektrik.filter.CounterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import com.konex.elektrik.Entity.Manufacturer;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;
    @Autowired
    private CounterService counterService;

    @Transactional
    public Counter addCounter(Counter counter, Subdivision subdivision
//            , Manufacturer manufacturer
    ) {

        counter.setSubdivisions(subdivision);
//        counter.setManufacturers(manufacturer);
        return counterRepository.save(counter);
    }

    @Transactional
    public void delete(Long id) {

        counterRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Counter getById(Long id) {

        return counterRepository.getOne(id);
    }

    @Override
    public List<Counter> findByIdList(List<Long> counterIdList) {
        return counterRepository.findByIdIn(counterIdList);
    }

    @Transactional
    public Counter editCounter(Counter counter, Subdivision subdivision
//            , Manufacturer manufacturer
    ) {

        counter.setSubdivisions(subdivision);
//        counter.setManufacturers(manufacturer);
        return counterRepository.saveAndFlush(counter);
    }

    @Transactional(readOnly = true)
    public List<Counter> getAll(Sort sort) {

        return counterRepository.findAll(sort);
    }
    @Transactional(readOnly = true)
    public List<Counter> findCounterByCriteria(CounterFilter counterFilter) {

        Specification<Counter> counterSpecs = CounterSpecs.counterSpecByFilter(counterFilter);
        return counterService.getAll(counterSpecs, new Sort(Sort.Direction.ASC, "subdivisions.name"));
    }

    @Transactional(readOnly = true)
    public List<Counter> getAllCountersBySubdivision(Subdivision subdivision) {

        return counterRepository.getAllBySubdivisions(subdivision);
    }

    @Transactional(readOnly = true)
    public List<Counter> getAll(Specification<Counter> counterSpecs, Sort sort) {

        return counterRepository.findAll(counterSpecs, sort);
    }

    @Transactional(readOnly = true)
    public List<Counter> getStatisticsBySpec(CounterFilter counterFilter) {

        Specification<Counter> counterSpecification = CounterSpecs.counterSpecByFilter(counterFilter);
        return counterService.getAll(counterSpecification, new Sort(Sort.Direction.ASC, "subdivision.name", "indicators.date"));
    }
}
