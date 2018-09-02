package com.konex.elektrik.Service.City;

import com.konex.elektrik.Entity.City;
import com.konex.elektrik.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public City addCity(City citiesTravel) {

        return cityRepository.save(citiesTravel);
    }

    @Transactional
    public void delete(Long id) {

        cityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public City getById(Long id) {

        return cityRepository.getOne(id);
    }

    @Transactional
    public City editCity(City citiesTravel) {

        return cityRepository.saveAndFlush(citiesTravel);
    }

    @Transactional(readOnly = true)
    public List<City> getAll(Sort sort) {

        return cityRepository.findAll(sort);
    }
}
