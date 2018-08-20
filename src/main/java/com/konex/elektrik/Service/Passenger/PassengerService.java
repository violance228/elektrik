package com.konex.elektrik.Service.Passenger;

import com.konex.elektrik.Entity.Passenger;

import java.util.List;

public interface PassengerService {

    Passenger addPassenger(Passenger passenger);
    void delete(Long id);
    Passenger getById(Long id);
    Passenger editPassenger(Passenger passenger);
    List<Passenger> getAll();
}
