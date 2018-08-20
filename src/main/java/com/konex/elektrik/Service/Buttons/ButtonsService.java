package com.konex.elektrik.Service.Buttons;

import com.konex.elektrik.Entity.Buttons;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ButtonsService {
    Buttons getById(Long id);
    List<Buttons> getAll();
    List<Buttons> getAllWhereParentIdIsNull(Sort sort);
    List<Buttons> getAllWhereParentIdIsNotNull();
    List<Buttons> getAllByParentId(Long id);
    Buttons getByEngName(String name);
}
