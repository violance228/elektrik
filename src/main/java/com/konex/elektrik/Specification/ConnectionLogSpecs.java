/*
 * Copyright (c) 2018. month -- 5. day -- 3.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Specification;

import com.konex.elektrik.Entity.ConnectionLog;
import com.konex.elektrik.Entity.ConnectionLog_;
import com.konex.elektrik.Service.ConnectionLog.ConnectionLogService;
import com.konex.elektrik.Service.User.UserService;
import com.konex.elektrik.filter.ConnectionLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class ConnectionLogSpecs implements Specification<ConnectionLog> {

    public ConnectionLogSpecs() { }

    @Autowired
    private static ConnectionLogService connectionLogService;
    @Autowired
    private static UserService userService;

    public Predicate toPredicate(Root<ConnectionLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }

    /**
     * Returns Predicates and Criterias for ConnectionLog filtering
     *
     * @param connectionLogFilter object of ConnectionLogFilter class
     * @return Spec for ConnectionLog}
     */

    public static Specification<ConnectionLog> connectionLogSpecsByFilter(ConnectionLogFilter connectionLogFilter) {
        System.out.println("**************************** orderSpecByFilter **************************** " + connectionLogFilter);
        return (root, query, cb) -> {
            //Constructing list of parameter
            List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate predicate = null;
            SingularAttribute attribute = null;
            String param;
            String day, month, year;

            if (connectionLogFilter.getUserUsername() != null) {
                System.out.println("********************************** counterSpecGetSubdivisionId"+ connectionLogFilter.getUserUsername());
                attribute = ConnectionLog_.users;
                String search = null;
                search = connectionLogFilter.getUserUsername();
                predicate= SpecsMethods.getSpecForNestedTextField(attribute, "username", search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            System.out.println("------------------------------------------------------\npredicates_cnt: " + predicates.size());
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
