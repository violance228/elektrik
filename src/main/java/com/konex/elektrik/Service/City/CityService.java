package com.konex.elektrik.Service.City;

import com.konex.elektrik.Entity.City;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CityService {

    City addCity(City city);
    void delete(Long id);
    City getById(Long id);
    City editCity(City city);
    List<City> getAll(Sort sort);
}
