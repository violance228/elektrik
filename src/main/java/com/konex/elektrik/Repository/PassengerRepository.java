package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
