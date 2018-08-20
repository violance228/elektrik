package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Order getOrdersBySubdivisionsId(Long id);
    Set<Order> findAllByUsersOrderByDateOfApplicationDesc(User user);
    Set<Order> findAllBySubdivisionsOrderByDateOfApplicationDesc(Subdivision subdivision);
}
