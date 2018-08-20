package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubdivisionRepository extends JpaRepository<Subdivision, Long> {
    Subdivision findFirstByName(Long name);

}
