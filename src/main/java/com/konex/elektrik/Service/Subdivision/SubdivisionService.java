package com.konex.elektrik.Service.Subdivision;

import com.konex.elektrik.Entity.Subdivision;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SubdivisionService {

    Subdivision addSubdivision(Subdivision subdivision);
    void delete(Long id);
    Subdivision getById(Long id);
    Subdivision findByName(Long name);
    Subdivision editSubdivision(Subdivision subdivision);
    List<Subdivision> getAll(Sort sort);
//
}
