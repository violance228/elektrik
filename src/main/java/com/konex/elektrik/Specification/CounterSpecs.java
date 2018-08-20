package com.konex.elektrik.Specification;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Counter_;
import com.konex.elektrik.Entity.Indicators_;
import com.konex.elektrik.filter.CounterFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class CounterSpecs implements Specification<Counter> {

    public CounterSpecs() { }

    public Predicate toPredicate(Root<Counter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }

    /**
     * Returns Predicates and Criterias for Counter filtering
     *
     * @param counterFilter object of OrderFilter class
     * @return Spec for Counter
     */

    public static Specification<Counter> counterSpecByFilter(CounterFilter counterFilter) {
        System.out.println("------------------------------- orderSpecByFilter --------------------------- " + counterFilter);
        return (root, query, cb) -> {
            //Constructing list of parameters
            List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate predicate = null;
            SingularAttribute attribute = null;
            String param;
            String day, month, year;

            if (counterFilter.getSubdivisionId() != null) {
                System.out.println("********************************** counterSpecGetSubdivisionId"+ counterFilter.getSubdivisionId());
                attribute = Counter_.subdivisions;
                String search = null;
                search = counterFilter.getSubdivisionId();
                predicate= SpecsMethods.getSpecForNumbers(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
            if(counterFilter.getCounterType() != null) {
                System.out.println("********************************** counterSpecGetTypeId"+ counterFilter.getCounterType());
                attribute = Counter_.type;
                String search = null;
                search = counterFilter.getCounterType();
                predicate = SpecsMethods.getSpecForText(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            year = counterFilter.getDateOfWithdrawalOfIndicators();
            if (year != null && year != "0" && !year.isEmpty()) {
                attribute = Indicators_.counters;
                predicate = SpecsMethods.getSpecForPartOfDate(attribute, year, "year", root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            System.out.println("------------------------------------------------------\npredicates_cnt: " + predicates.size());
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
