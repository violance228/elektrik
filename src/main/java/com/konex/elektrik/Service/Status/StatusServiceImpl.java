package com.konex.elektrik.Service.Status;

import com.konex.elektrik.Entity.Status;
import com.konex.elektrik.Repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Transactional(readOnly = true)
    public Status getById(Long id) {

        return statusRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public Status findByName(String name) {

        return statusRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Status> getAll() {

        return statusRepository.findAll();
    }
}
