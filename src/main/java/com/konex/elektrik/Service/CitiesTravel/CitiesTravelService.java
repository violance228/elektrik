package com.konex.elektrik.Service.CitiesTravel;

import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.CitiesTravel;
import com.konex.elektrik.Entity.City;

import java.util.List;

public interface CitiesTravelService {

    CitiesTravel addCitiesTravel(CitiesTravel citiesTravel, Assignment assignment, City city);
    void delete(Long id);
    CitiesTravel getById(Long id);
    CitiesTravel editCitiesTravel(CitiesTravel citiesTravel);
    List<CitiesTravel> getAll();
}
