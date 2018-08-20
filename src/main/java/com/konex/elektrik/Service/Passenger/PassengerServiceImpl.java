package com.konex.elektrik.Service.Passenger;

import com.konex.elektrik.Entity.Passenger;
import com.konex.elektrik.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository citiesTravelRepository;

    @Transactional
    public Passenger addPassenger(Passenger passenger) {

        return citiesTravelRepository.save(passenger);
    }

    @Transactional
    public void delete(Long id) {

        citiesTravelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Passenger getById(Long id) {

        return citiesTravelRepository.getOne(id);
    }

    @Transactional
    public Passenger editPassenger(Passenger passenger) {

        return citiesTravelRepository.saveAndFlush(passenger);
    }

    @Transactional(readOnly = true)
    public List<Passenger> getAll() {

        return citiesTravelRepository.findAll();
    }
}
