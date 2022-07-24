package com.example.inttaskbe.service;

import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    List<SkillDto> findAll();

    Page<SkillDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter);

    Optional<SkillDto> findById(Long id);

    SkillDto update(SkillDto skillDto) throws RuntimeException;

    SkillDto save(SkillDto skillDto) throws EntityExistsException;

    void deleteById(Long id) throws InvalidEntityException;
}
