package com.konex.elektrik.Service.Role;

import com.konex.elektrik.Entity.Role;

import java.util.List;

public interface RoleService {

    Role getById(Long id);
    Role getByName(String name);
    List<Role> getAll();
}
