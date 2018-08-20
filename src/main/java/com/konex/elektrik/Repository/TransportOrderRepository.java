package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportOrderRepository extends JpaRepository<TransportOrder, Long> {
    List<TransportOrder> getAllByAssignmentsIsNotNull();
}
