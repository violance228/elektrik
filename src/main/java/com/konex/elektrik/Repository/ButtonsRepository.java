package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.Buttons;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ButtonsRepository extends JpaRepository<Buttons, Long> {
    List<Buttons> getAllByParentIdIsNull(Sort sort);
    List<Buttons> getAllByParentIdEquals(Long id);
    List<Buttons> getAllByParentIdIsNotNull();
    Buttons getButtonsByEngName(String name);
}
