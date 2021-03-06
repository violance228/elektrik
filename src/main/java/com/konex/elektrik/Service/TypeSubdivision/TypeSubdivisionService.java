package com.konex.elektrik.Service.TypeSubdivision;

import com.konex.elektrik.Entity.TypeSubdivision;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TypeSubdivisionService {

    TypeSubdivision addTypeSubdivision(TypeSubdivision typeSubdivision);
    void delete(Long id);
    TypeSubdivision getById(Long id);
    TypeSubdivision editTypeSubdivision(TypeSubdivision typeSubdivision);
    List<TypeSubdivision> getAll(Sort sort);
}
