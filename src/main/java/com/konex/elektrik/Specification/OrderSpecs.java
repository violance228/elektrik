package com.konex.elektrik.Specification;

import com.konex.elektrik.Entity.Order;

import com.konex.elektrik.Entity.Order_;
import com.konex.elektrik.Service.User.UserService;
import com.konex.elektrik.filter.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecs implements Specification<Order> {

    public OrderSpecs() {
    }

    @Autowired
    private static UserService userService;

    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }

    /**
     * Returns Predicates and Criterias for Order filtering
     *
     * @param orderFilter object of OrderFilter class
     * @return Spec for Order
     */

    public static Specification<Order> orderSpecsByFilter(OrderFilter orderFilter) {
        System.out.println("**************************** orderSpecByFilter **************************** " + orderFilter);
        return (root, query, cb) -> {
            //Constructing list of parameters
            List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate predicate = null;
            SingularAttribute attribute = null;
            String param;
            String day, month, year;

            if (orderFilter.getUsername() != null) {
                attribute = Order_.users;
                String search = null;
                search = orderFilter.getUsername();
                predicate = SpecsMethods.getSpecForNumbers(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
            if (orderFilter.getSubdivisionId() != null) {
                attribute = Order_.subdivisions;
                String search = null;
                search = orderFilter.getSubdivisionId();
                predicate= SpecsMethods.getSpecForNumbers(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
            if (orderFilter.getStatusId() != null) {
                attribute = Order_.status;
                String search = null;
                search = orderFilter.getStatusId();
                predicate= SpecsMethods.getSpecForNumbers(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
            System.out.println("------------------------------------------------------\npredicates_cnt: " + predicates.size());
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
