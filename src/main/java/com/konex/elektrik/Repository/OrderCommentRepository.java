package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderCommentRepository extends JpaRepository<OrderComment, Long> {
    List<OrderComment> findAllByOrdersOrderByDateAsc(Order order);
}