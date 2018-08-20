package com.konex.elektrik.Service.Role;

import com.konex.elektrik.Entity.Role;
import com.konex.elektrik.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role getById(Long id) {

        return roleRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Role> getAll() {

        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}