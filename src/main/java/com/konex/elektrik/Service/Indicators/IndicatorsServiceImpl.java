package com.konex.elektrik.Service.Indicators;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Repository.IndicatorsRepository;
import com.konex.elektrik.Service.Counter.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IndicatorsServiceImpl implements IndicatorsService {

    @Autowired
    private IndicatorsRepository indicatorsRepository;

    @Autowired
    private IndicatorsService indicatorsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CounterService counterService;

    @Transactional
    public Indicators addIndicators (Indicators indicators, Counter counter) {

        indicators.setCounters(counter);
        Indicators indicator = indicatorsService.getFirstIndicatorByCounterSortByDate(counter);
        if(indicator != null) {
            indicators.setConsumption(indicators.getIndicator() - indicator.getIndicator());
        }
        else {
            indicators.setConsumption(indicators.getIndicator());
        }
        return indicatorsRepository.save(indicators);
    }

    @Transactional
    public void delete(Long id) {

        indicatorsRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Indicators getById(Long id) {

        return indicatorsRepository.getOne(id);
    }

    @Transactional
    public Indicators editIndicators(Indicators  indicators) {


        Indicators indicator = indicatorsService.getFirstByCounterAndDateBefore(indicators.getCounters(), indicators.getDate());
        if(indicator != null) {
            indicators.setConsumption(indicators.getIndicator() - indicator.getIndicator());
        }
        else {
            indicators.setConsumption(indicators.getIndicator());
        }
        Indicators ind = indicatorsService.getFirstByCounterAndDateAfter(indicators.getCounters(), indicators.getDate());
        if(ind != null) {
            ind.setConsumption(ind.getIndicator() - indicators.getIndicator());
            indicatorsRepository.saveAndFlush(ind);
        }
        return indicatorsRepository.saveAndFlush(indicators);
    }

    @Transactional(readOnly = true)
    public List<Indicators > getAll(Sort sort) {

        return indicatorsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Indicators> getAllByCounter(Counter counter, Sort sort) {

        return indicatorsRepository.findAllByCountersOrderByDateDesc(counter);
    }

    @Transactional(readOnly = true)
    public List<Indicators> getAllByCounterSortAsc(Counter counter) {

        return indicatorsRepository.findAllByCountersOrderByDateAsc(counter);
    }

    @Transactional(readOnly = true)
    public Indicators getFirstIndicatorByCounterSortByDate(Counter counter) {

        return indicatorsRepository.getFirstByCountersOrderByDateDesc(counter);
    }

    @Transactional(readOnly = true)
    public Indicators getFirstByCounterAndDateIsNotLike(Counter counter, Date date) {

        return indicatorsRepository.findFirstByCountersAndDateNot(counter, date);
    }

    @Transactional(readOnly = true)
    public Indicators getFirstByCounterAndDateAfter(Counter counter, Date date) {

        return indicatorsRepository.findFirstByCountersAndDateAfterOrderByDateAsc(counter, date);
    }

    @Transactional(readOnly = true)
    public Indicators getFirstByCounterAndDateBefore(Counter counter, Date date) {

        return indicatorsRepository.findFirstByCountersAndDateBeforeOrderByDateDesc(counter, date);
    }

    @Transactional(readOnly = true)
    public List<Indicators> getAllByCounterOrderByIndicators(Counter counter) {

        return indicatorsRepository.getAllByCountersOrderByIndicatorAsc(counter);
    }

    @Transactional(readOnly = true)
    public List<Indicators> findByIdInOrderByCountersAscDateAsc(List<Long> indicatorIdList) {

        return indicatorsRepository.findByIdInOrderByCountersAscDateAsc(indicatorIdList);
    }

    @Transactional(readOnly = true)
    public List<Indicators> getAllBySubdivision(Subdivision subdivision, int year) {

        List<Counter> counterBySubdivision = new ArrayList<>();
        counterBySubdivision = counterService.getAllCountersBySubdivision(subdivision);
        List<Long> indicatorIdList = new ArrayList<>();

        for(Counter counter : counterBySubdivision) {
            List<Indicators> indicatorsList = indicatorsService.getAllByCounter(counter, new Sort(Sort.Direction.ASC, "name"));
            for(Indicators indicators : indicatorsList) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(indicators.getDate());
                int yearThisIndicator = calendar.get(Calendar.YEAR);
                if(yearThisIndicator == year) {
                    indicatorIdList.add(indicators.getId());
                }
            }
        }

        return indicatorsService.findByIdInOrderByCountersAscDateAsc(indicatorIdList);
    }

    @Transactional(readOnly = true)
    public List<Indicators> getListIndicatorByDate(int year) {

        String sql = "select i.indicators_id from indicators i where date_part('year', i.date) = " + year + "";
        List<Long> indicatorIdList = jdbcTemplate.queryForList(sql, Long.class);
        List<Indicators> indicatorList = indicatorsService.findByIdInOrderByCountersAscDateAsc(indicatorIdList);

        return indicatorList;
    }

    @Transactional(readOnly = true)
    public List<Indicators> getListIndicatorSortByCounterByDate(Counter counter, int year) {

       List<Indicators> indicatorsList = indicatorsService.getAllByCounter(counter, new Sort(Sort.Direction.ASC, "name"));
       List<Long> indicatorSortedByCounterAndDate = new ArrayList<>();

       for(Indicators indicators : indicatorsList) {
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(indicators.getDate());
           int yearThisIndicator = calendar.get(Calendar.YEAR);
           if(yearThisIndicator == year) {
               indicatorSortedByCounterAndDate.add(indicators.getId());
           }
       }

       return indicatorsService.findByIdInOrderByCountersAscDateAsc(indicatorSortedByCounterAndDate);
    }
}
