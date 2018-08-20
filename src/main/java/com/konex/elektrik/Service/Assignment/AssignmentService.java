package com.konex.elektrik.Service.Assignment;

import com.konex.elektrik.Entity.Assignment;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AssignmentService {

    Assignment addAssignment(Assignment assignment, Subdivision subdivision, User user);
    void delete(Long id);
    Assignment getById(Long id);
    Assignment editAssignment(Assignment assignment);
    List<Assignment> getAll(Sort sort);
}
