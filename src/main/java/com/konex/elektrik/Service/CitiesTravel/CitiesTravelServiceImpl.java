package com.konex.elektrik.Service.CitiesTravel;

import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.CitiesTravel;
import com.konex.elektrik.Entity.City;
import com.konex.elektrik.Repository.CitiesTravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitiesTravelServiceImpl implements CitiesTravelService {

    @Autowired
    private CitiesTravelRepository citiesTravelRepository;

    @Transactional
    public CitiesTravel addCitiesTravel(CitiesTravel citiesTravel, Assignment assignment, City city) {

        citiesTravel.setAssignments(assignment);
        citiesTravel.setCities(city);
        return citiesTravelRepository.save(citiesTravel);
    }

    @Transactional
    public void delete(Long id) {

        citiesTravelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CitiesTravel getById(Long id) {

        return citiesTravelRepository.getOne(id);
    }

    @Transactional
    public CitiesTravel editCitiesTravel(CitiesTravel citiesTravel) {

        return citiesTravelRepository.saveAndFlush(citiesTravel);
    }

    @Transactional(readOnly = true)
    public List<CitiesTravel> getAll() {

        return citiesTravelRepository.findAll();
    }
}
