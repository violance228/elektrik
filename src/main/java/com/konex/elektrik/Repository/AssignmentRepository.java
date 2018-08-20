package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Assignment findBySubdivisions(String name);
}