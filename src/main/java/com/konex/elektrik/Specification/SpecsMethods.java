package com.konex.elektrik.Specification;

import org.hibernate.usertype.UserType;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecsMethods {
    public static final String EQUAL = "=";
    public static final String NOT_EQUAL = "<>";
    public static final String GREATHER = ">";
    public static final String LESS = "<";
    public static final String GREATHER_OR_EQUAL = ">=";
    public static final String LESS_OR_EQUAL = "<=";
//    enum NumberComparation { EQUAL, NOT_EQUAL , GREATHER, LESS, GREATHER_OR_EQUAL, LESS_OR_EQUAL }

    public static List<String> numberOperationList = Arrays.asList(EQUAL, NOT_EQUAL, GREATHER, LESS, GREATHER_OR_EQUAL, LESS_OR_EQUAL);

    public static Predicate getPredicateForCheckbox(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        if (searchTerm != null) {
            String param = searchTerm.trim();
            System.out.println(attribute.getName() + ": " + param);
            if (!param.isEmpty() && !param.equalsIgnoreCase("null")) {
                Boolean value = Boolean.parseBoolean(param);
                return criteriaBuilder.equal(root.get(attribute), value);
            }
        }
        return null;
    }

    public static Predicate getSpecForPartOfDate(SingularAttribute attribute, String searchTerm, String partOfDate, Root root, CriteriaBuilder criteriaBuilder) {
//        System.out.println(attribute.getName() + "_" + partOfDate + ": " + searchTerm);
        Integer value = null;
        if (searchTerm != null && !searchTerm.isEmpty() && !searchTerm.equals("0")) {
            value = Integer.parseInt(searchTerm);
            return criteriaBuilder.equal(criteriaBuilder.function(partOfDate, Integer.class, root.get(attribute)), value);
        }
        return null;
    }

    public static Predicate getSpecForNumbers(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println("***********************************getSpecForNumbers************"+ searchTerm );
        Predicate predicate = null;
        HashMap<String, String> operatorAndNumber = new HashMap<>();
        String expr = null;
        String digit = null;
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm == null || searchTerm.isEmpty()) {
            return null;
        } else {
            operatorAndNumber = findOperatorAndNumber(searchTerm);
            expr = operatorAndNumber.get("operator");
            digit = operatorAndNumber.get("number");
        }
        System.out.println("Find expr: " + expr + "\nFind digit: " + digit);
        Float numb = 0.0f;
        try {
            numb = Float.parseFloat(digit);
        } catch (NumberFormatException e) {
            System.err.println("Error: Can't parse the number");
        }
        if (expr.equals(EQUAL)) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
        if (expr.equals(GREATHER)) predicate = criteriaBuilder.greaterThan(root.<Float>get(attribute), numb);
        if (expr.equals(LESS)) predicate = criteriaBuilder.lessThan(root.<Float>get(attribute), numb);
        if (expr.equals(GREATHER_OR_EQUAL))
            predicate = criteriaBuilder.greaterThanOrEqualTo(root.<Float>get(attribute), numb);
        if (expr.equals(LESS_OR_EQUAL)) predicate = criteriaBuilder.lessThanOrEqualTo(root.<Float>get(attribute), numb);
        if (expr.equals(NOT_EQUAL)) predicate = criteriaBuilder.notEqual(root.<Float>get(attribute), numb);
        if (expr.isEmpty()) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
        if (digit.isEmpty()) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
        return predicate;
    }

    public static Predicate getSpecForText(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm == null || searchTerm.isEmpty()) {
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), "%"),
                    criteriaBuilder.isNull(root.get(attribute))
            );
        } else {
            searchTerm = searchTerm.trim();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), "%" + searchTerm.toLowerCase() + "%");
        }
    }

    public static Predicate getSpecForObjectById(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm != null && !searchTerm.isEmpty() && !searchTerm.equalsIgnoreCase("null")) {
            return criteriaBuilder.equal(root.<UserType>get(attribute).get("id"), searchTerm);
        }
        return null;
    }

    public static Predicate getSpecForIdNumber(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm != null && !searchTerm.isEmpty() && !searchTerm.equalsIgnoreCase("null")) {
            int id;
            try {
                id = Integer.parseInt(searchTerm);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return null;
            }
            return criteriaBuilder.equal(root.get(attribute), id);
        }
        return null;
    }

    public static Predicate getSpecForNestedTextField(SingularAttribute attribute, String fieldName, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm != null && !searchTerm.isEmpty() && !searchTerm.equalsIgnoreCase("null")) {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute).get(fieldName)), "%" + searchTerm.toLowerCase() + "%");
        }
        return null;
    }

    public static Predicate getSpecByList(SingularAttribute attribute, List searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm != null && searchTerm.size() > 0) {
            return criteriaBuilder.isTrue(root.<UserType>get(attribute).in(searchTerm));
        }
        return null;
    }

    public static Predicate getSpecForDataMore(SingularAttribute attribute, Date searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        //return criteriaBuilder.lessThanOrEqualTo(root.<UserType>get(attribute).get("id"), searchTerm);
        return criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), searchTerm);
    }

    public static Predicate getSpecForDataLess(SingularAttribute attribute, Date searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        //return criteriaBuilder.lessThanOrEqualTo(root.<UserType>get(attribute).get("id"), searchTerm);
        return criteriaBuilder.lessThanOrEqualTo(root.get(attribute), searchTerm);
    }

    public static Predicate getSpecForNumbers(SingularAttribute attribute, String searchTerm, Join root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        HashMap<String, String> operatorAndNumber = new HashMap<>();
        String expr = null;
        String digit = null;
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm == null || searchTerm.isEmpty()) {
            return null;
        } else {
            operatorAndNumber = findOperatorAndNumber(searchTerm);
            expr = operatorAndNumber.get("operator");
            digit = operatorAndNumber.get("number");
        }
        System.out.println("Find expr: " + expr + "\nFind digit: " + digit);
        Float numb = 0.0f;
        try {
            numb = Float.parseFloat(digit);
        } catch (NumberFormatException e) {
            System.err.println("Error: Can't parse the number");
        }
//        if(numberOperationList.contains(expr) || expr == null || expr.isEmpty()) {
        if (expr.equals(EQUAL)) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
        if (expr.equals(GREATHER)) predicate = criteriaBuilder.greaterThan(root.<Float>get(attribute), numb);
        if (expr.equals(LESS)) predicate = criteriaBuilder.lessThan(root.<Float>get(attribute), numb);
        if (expr.equals(GREATHER_OR_EQUAL))
            predicate = criteriaBuilder.greaterThanOrEqualTo(root.<Float>get(attribute), numb);
        if (expr.equals(LESS_OR_EQUAL)) predicate = criteriaBuilder.lessThanOrEqualTo(root.<Float>get(attribute), numb);
        if (expr.equals(NOT_EQUAL)) predicate = criteriaBuilder.notEqual(root.<Float>get(attribute), numb);
        if (expr.isEmpty()) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
        if (digit.isEmpty()) predicate = criteriaBuilder.equal(root.<Float>get(attribute), numb);
//        } else {
//            throw new WrongComparisionOperationException();
//        }
        return predicate;
    }

    public static HashMap<String, String> findOperatorAndNumber(String serachString) {
        HashMap<String, String> operatorAndNumber = new HashMap<>();
        String digit = null;
        String expr = null;

        Pattern p = Pattern.compile("-?\\d+\\.?\\d*"); //"-?\\d+" for INT
        Matcher m = p.matcher(serachString);
        while (m.find()) {
            digit = m.group();
            expr = serachString.substring(0, m.start());
        }
        if (digit == null || digit.isEmpty()) {
            digit = "0.0";
            expr = serachString;
        }
        operatorAndNumber.put("operator", expr);
        operatorAndNumber.put("number", digit);

        return operatorAndNumber;
    }

    //************************************MYSELF METHODS*****************************************
//*******************************************************************************************
    public static Predicate getSpecForStatus(SingularAttribute attribute, String searchTerm, Root root, CriteriaBuilder criteriaBuilder) {
        System.out.println(attribute.getName() + ": " + searchTerm);
        if (searchTerm != null) {
            return criteriaBuilder.equal((Expression<?>) attribute, searchTerm);
        }
        return null;
    }
}
