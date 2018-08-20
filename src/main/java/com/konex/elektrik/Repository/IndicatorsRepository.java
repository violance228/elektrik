package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IndicatorsRepository extends JpaRepository<Indicators, Long> {

    List<Indicators> findAllByCountersOrderByDateDesc(Counter counter);
    List<Indicators> findAllByCountersOrderByDateAsc(Counter counter);
    Indicators getFirstByCountersOrderByDateDesc(Counter counter);
    Indicators findFirstByCountersAndDateNot(Counter counter, Date date);
    Indicators findFirstByCountersAndDateAfterOrderByDateAsc(Counter counter, Date date);
    Indicators findFirstByCountersAndDateBeforeOrderByDateDesc(Counter counter, Date date);

    List<Indicators> getAllByCountersOrderByIndicatorAsc(Counter counter);
    List<Indicators> findByIdInOrderByCountersAscDateAsc(List<Long> idList);


//    @Query("select i FROM Indicators where i.counters = :counters and i.date >= :date order by i.date DESC nulls first ")
//    Indicators findIndicatorsDate(@Param("counter")Counter counter,
//                                  @Param("date") Date date);
}
