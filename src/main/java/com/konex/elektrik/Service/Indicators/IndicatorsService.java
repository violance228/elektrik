package com.konex.elektrik.Service.Indicators;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IndicatorsService {

    Indicators addIndicators(Indicators indicators, Counter counter);
    void delete(Long id);
    Indicators getById(Long id);
    Indicators editIndicators(Indicators indicators);
    List<Indicators> getAll(Sort sort);
    List<Indicators> getAllByCounter(Counter counter, Sort sort);
    Indicators getFirstIndicatorByCounterSortByDate(Counter counter);
    Indicators getFirstByCounterAndDateIsNotLike(Counter counter, Date date);
    Indicators getFirstByCounterAndDateAfter(Counter counter, Date date);
    Indicators getFirstByCounterAndDateBefore(Counter counter, Date date);
    List<Indicators> getAllByCounterSortAsc(Counter counter);
    List<Indicators> getAllByCounterOrderByIndicators(Counter counter);
    List<Indicators> getAllBySubdivision(Subdivision subdivision, int year);
    List<Indicators> findByIdInOrderByCountersAscDateAsc(List<Long> indicatorIdList);
    List<Indicators> getListIndicatorByDate(int year);
    List<Indicators> getListIndicatorSortByCounterByDate(Counter counter, int year);
}
