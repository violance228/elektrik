package com.konex.elektrik.Service.OrderComment;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderComment;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.OrderCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    @Transactional
    public OrderComment addOrderComment(OrderComment orderComment, User user, Order order) {

        orderComment.setUsers(user);
        orderComment.setOrders(order);
        return orderCommentRepository.save(orderComment);
    }

    @Transactional
    public void delete(Long id) {

        orderCommentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public OrderComment getById(Long id) {

        return orderCommentRepository.getOne(id);
    }

//    @Transactional(readOnly = true)
//    public OrderComment findByUsername(String username) {
//
//        return userRepository.findByUsername(username);
//    }

    @Transactional
    public OrderComment editOrderComment(OrderComment orderComment) {

        return orderCommentRepository.saveAndFlush(orderComment);
    }

    @Transactional(readOnly = true)
    public List<OrderComment> getAll() {

        return orderCommentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OrderComment> getOrderCommentByOrder(Order order) {

        return orderCommentRepository.findAllByOrdersOrderByDateAsc(order);
    }
}
