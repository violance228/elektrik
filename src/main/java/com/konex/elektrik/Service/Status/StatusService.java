package com.konex.elektrik.Service.Status;

import com.konex.elektrik.Entity.Status;

import java.util.List;

public interface StatusService {

    Status getById(Long id);
    Status findByName(String name);
    List<Status> getAll();
}
