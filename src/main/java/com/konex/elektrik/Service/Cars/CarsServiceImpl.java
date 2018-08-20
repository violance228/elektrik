package com.konex.elektrik.Service.Cars;

import com.konex.elektrik.Entity.Cars;
import com.konex.elektrik.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarsServiceImpl implements CarsService {

    @Autowired
    private CarRepository carRepository;

    @Transactional
    public Cars addCars(Cars cars) {

        return carRepository.save(cars);
    }

    @Transactional
    public void delete(Long id) {

        carRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Cars getById(Long id) {

        return carRepository.getOne(id);
    }

    @Transactional
    public Cars editCars(Cars cars) {

        return carRepository.saveAndFlush(cars);
    }

    @Transactional(readOnly = true)
    public List<Cars> getAll() {

        return carRepository.findAll();
    }
}
