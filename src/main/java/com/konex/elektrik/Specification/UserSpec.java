package com.konex.elektrik.Specification;

import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Entity.User_;
import com.konex.elektrik.Service.User.UserService;
import com.konex.elektrik.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class UserSpec implements Specification<User> {

    public UserSpec() {
    }

    @Autowired
    private static UserService userService;

    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }

    /**
     * Returns Predicates and Criterias for Order filtering
     *
     * @param userFilter object of OrderFilter class
     * @return Spec for Order
     */

    public static Specification<User> userSpecByFilter(UserFilter userFilter) {
        System.out.println("**************************** orderSpecByFilter ****************************--- " + userFilter);
        return (root, query, cb) -> {
            //Constructing list of parameters
            List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate predicate = null;
            SingularAttribute attribute = null;
            String param;
            String day, month, year;

            if (userFilter.getSubdivisionId() != null) {
                System.out.println("**********************************orderSpecGetSubdivisionId-----------"+ userFilter.getSubdivisionId());
                attribute = User_.subdivisions;
                String search = null;
                search = userFilter.getSubdivisionId();
                predicate = SpecsMethods.getSpecForNumbers(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            } if (userFilter.getUserSurname() != null) {
                System.out.println("**********************************orderSpecGetSubdivisionId-----------"+ userFilter.getUserSurname());
                attribute = User_.surname;
                String search = null;
                search = userFilter.getUserSurname();
                predicate = SpecsMethods.getSpecForText(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            } if(userFilter.getUserName() != null) {
                System.out.println("**********************************orderSpecGetSubdivisionId----------"+ userFilter.getUserName());
                attribute = User_.username;
                String search = null;
                search = userFilter.getUserName();
                predicate = SpecsMethods.getSpecForText(attribute, search, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            System.out.println("------------------------------------------------------\npredicates_cnt: " + predicates.size());
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
