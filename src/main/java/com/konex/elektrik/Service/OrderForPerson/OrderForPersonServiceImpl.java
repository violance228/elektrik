package com.konex.elektrik.Service.OrderForPerson;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderForPerson;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.OrderForPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderForPersonServiceImpl implements OrderForPersonService {

    @Autowired
    private OrderForPersonRepository orderForPersonRepository;

    @Transactional
    public OrderForPerson addOrderForPerson(OrderForPerson orderForPerson) {

        return orderForPersonRepository.save(orderForPerson);
    }

    @Transactional
    public void delete(Long id) {

        orderForPersonRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public OrderForPerson getById(Long id) {

        return orderForPersonRepository.getOne(id);
    }

    @Transactional
    public OrderForPerson editOrderForPerson(OrderForPerson orderForPerson) {

        return orderForPersonRepository.saveAndFlush(orderForPerson);
    }

    @Transactional(readOnly = true)
    public List<OrderForPerson> getAll() {

        return orderForPersonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OrderForPerson> getAllByUsers (User user) {

        return orderForPersonRepository.getAllByUsers(user);
    }
}
