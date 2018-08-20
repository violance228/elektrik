package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
