package com.konex.elektrik.Service.Order;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.Status;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.filter.OrderFilter;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Order addOrder(Order order, Subdivision subdivision, User user);
    void delete(Long id);
    Order getById(Long id);
    Order editOrderStatus(Order order, Status status, String surname);
    Order editOrder(Order order);
    List<Order> getAll(Sort sort);
    Set<Order> getAllByUser(User user);
    Set<Order> getAllBySubdivision(Subdivision subdivision);
    List<Order> getByCriteria(OrderFilter orderFilter, Sort sort);
}
