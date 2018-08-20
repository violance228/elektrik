package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Cars, Long> {

}
