package com.konex.elektrik.Service.Counter;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Repository.CounterRepository;
import com.konex.elektrik.Service.Indicators.IndicatorsService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Specification.CounterSpecs;
import com.konex.elektrik.filter.CounterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;
    @Autowired
    private CounterService counterService;
    @Autowired
    private IndicatorsService indicatorsService;
    @Autowired
    private SubdivisionService subdivisionService;

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

    @Transactional(readOnly = true)
    public List<Indicators> setNullIndicators(Counter counter, int year) {

        List<Indicators> indicatorsList = indicatorsService.getListIndicatorSortByCounterByDate(counter, year);
        List<Indicators> returnListIndicators = new ArrayList<>();
        int month = 1;

        for (Indicators indicators : indicatorsList) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(indicators.getDate());
            int indicatorMonth = calendar.get(Calendar.MONTH);
            indicatorMonth++;
            if (indicatorMonth != month) {
                for (int i = month; i < indicatorMonth; i++) {
                    Indicators indicatorsSetNull = new Indicators();
                    indicatorsSetNull = indicators;
                    indicatorsSetNull.setDate(new Date(year-i-01));
                    indicatorsSetNull.setIndicator(0);
                    indicatorsSetNull.setConsumption(0);
                    returnListIndicators.add(indicatorsSetNull);
                    month++;
                }
                returnListIndicators.add(indicators);
                month++;
            } else {
                returnListIndicators.add(indicators);
                month++;
            }
        }
        return returnListIndicators;
    }

    @Transactional(readOnly = true)
    public Map<Counter, List<Indicators>> counterListMap(int year) {

        List<Counter> countersList = counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions.name"));

        Map<Counter, List<Indicators>> countersListMap = new LinkedHashMap<>();

        for (Counter counter : countersList) {

            countersListMap.put(counter,
                    setNullIndicators(counter, year));
        }

        return countersListMap;
    }

    @Transactional(readOnly = true)
    public Map<Counter, List<Indicators>> counterBySubdivisionListMap(int year, long idSubdv) {


        List<Counter> counters = counterService.getAllCountersBySubdivision(subdivisionService.getById(idSubdv));

        Map<Counter, List<Indicators>> countersListMap = new LinkedHashMap<>();
        for (Counter counter : counters)
            countersListMap.put(counter,
                    indicatorsService.getListIndicatorSortByCounterByDate(counter, year));

        return countersListMap;
    }

    @Transactional(readOnly = true)
    public List<Indicators> getIndicatorsBySubdivision(int year, long counterId) {

        Counter counter = counterService.getById(counterId);

        int month = 0;

        List<Indicators> IndicatorsListChart = indicatorsService.getListIndicatorSortByCounterByDate(counter, year);
        List<Indicators> IndicatorsListArr = new ArrayList<>();

        for (Indicators firstIndicators : IndicatorsListChart) {
            Calendar calendarIndicator = Calendar.getInstance();
            calendarIndicator.setTime(firstIndicators.getDate());
            int indicatorMonth = calendarIndicator.get(Calendar.MONTH);
            if (indicatorMonth == month) {
                IndicatorsListArr.add(firstIndicators);
                month++;
            } else {
                for (int i = 0; i < indicatorMonth; i++) {
                    Indicators indicators = new Indicators();
                    indicators.setConsumption(0);
                    indicators.setIndicator(0);
                    IndicatorsListArr.add(indicators);
                    month++;
                }
                IndicatorsListArr.add(firstIndicators);
                month++;
            }
        }
        Indicators indicators = new Indicators();
        if (IndicatorsListArr.size() != 12) {
            for (int i = IndicatorsListArr.size(); i <= 11; i++) {
                indicators.setConsumption(0);
                indicators.setIndicator(0);
                IndicatorsListArr.add(indicators);
            }
        }

        return IndicatorsListArr;
    }

}
