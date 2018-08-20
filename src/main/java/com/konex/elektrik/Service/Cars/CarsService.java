package com.konex.elektrik.Service.Cars;

import com.konex.elektrik.Entity.Cars;

import java.util.List;

public interface CarsService {

    Cars addCars(Cars cars);
    void delete(Long id);
    Cars getById(Long id);
    Cars editCars(Cars cars);
    List<Cars> getAll();
}
