package com.konex.elektrik.Service.Assignment;

import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.AssignmentRepository;
import com.konex.elektrik.Service.Status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StatusService statusService;

    @Transactional
    public Assignment addAssignment(Assignment assignment, Subdivision subdivision, User user) {

        assignment.setUsers(user);
        assignment.setSubdivisions(subdivision);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void delete(Long id) {

        assignmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Assignment getById(Long id) {

        return assignmentRepository.getOne(id);
    }

    @Transactional
    public Assignment editAssignment(Assignment assignment) {

//        assignment.setStatus(status);
//        System.out.println("---------------"+assignment.getStatus().getId());
        return assignmentRepository.saveAndFlush(assignment);
    }

    @Transactional(readOnly = true)
    public List<Assignment > getAll(Sort sort) {

        return assignmentRepository.findAll();
    }
}
