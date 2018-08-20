package com.konex.elektrik.Service.TransportOrder;

import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.Cars;
import com.konex.elektrik.Entity.TransportOrder;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.TransportOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransportOrderServiceImpl implements TransportOrderService {

    @Autowired
    private TransportOrderRepository transportOrderRepository;

    @Transactional
    public TransportOrder addTransportOrder(TransportOrder transportOrder, Cars cars, Assignment assignment, User user) {

        transportOrder.setCars(cars);
        transportOrder.setAssignments(assignment);
        transportOrder.setUsers(user);
        return transportOrderRepository.save(transportOrder);
    }

    @Transactional
    public void delete(Long id) {

        transportOrderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TransportOrder getById(Long id) {

        return transportOrderRepository.getOne(id);
    }

    @Transactional
    public TransportOrder editTransportOrder(TransportOrder transportOrder) {

        return transportOrderRepository.saveAndFlush(transportOrder);
    }

    @Transactional(readOnly = true)
    public List<TransportOrder> getAll() {

        return transportOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TransportOrder> getAllWhereAssignmentIsNotNull() {

        return transportOrderRepository.getAllByAssignmentsIsNotNull();
    }
}
