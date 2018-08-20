package com.konex.elektrik.Service.TransportOrder;


import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.Cars;
import com.konex.elektrik.Entity.TransportOrder;
import com.konex.elektrik.Entity.User;

import java.util.List;

public interface TransportOrderService {

    TransportOrder addTransportOrder(TransportOrder transportOrder, Cars cars, Assignment assignment, User user);
    void delete(Long id);
    TransportOrder getById(Long id);
    TransportOrder editTransportOrder(TransportOrder transportOrder);
    List<TransportOrder> getAll();
    List<TransportOrder> getAllWhereAssignmentIsNotNull();
}
