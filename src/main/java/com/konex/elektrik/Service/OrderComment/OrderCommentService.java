package com.konex.elektrik.Service.OrderComment;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderComment;
import com.konex.elektrik.Entity.User;

import java.util.List;

public interface OrderCommentService {

    OrderComment addOrderComment(OrderComment orderComment, User user, Order order);
    void delete(Long id);
    OrderComment getById(Long id);
//    OrderComment findByUsername(String username);
    OrderComment editOrderComment(OrderComment orderComment);
    List<OrderComment> getAll();
    List<OrderComment> getOrderCommentByOrder(Order order);
}
