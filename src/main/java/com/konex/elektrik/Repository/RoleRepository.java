package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);
}
