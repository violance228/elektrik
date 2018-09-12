package com.konex.elektrik.Service.OrderForPerson;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderForPerson;
import com.konex.elektrik.Entity.User;

import java.util.List;

public interface OrderForPersonService {

    OrderForPerson addOrderForPerson(OrderForPerson orderForPerson);
    void delete(Long id);
    OrderForPerson getById(Long id);
    OrderForPerson editOrderForPerson(OrderForPerson orderForPerson);
    List<OrderForPerson> getAll();
    List<OrderForPerson> getAllByUsers (User user);
}
