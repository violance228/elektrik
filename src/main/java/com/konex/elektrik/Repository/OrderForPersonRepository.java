package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.OrderForPerson;
import com.konex.elektrik.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderForPersonRepository extends JpaRepository<OrderForPerson, Long> {

    List<OrderForPerson> getAllByUsers (User user);
}
