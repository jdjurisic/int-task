package com.example.inttaskbe.service;

import com.example.inttaskbe.dto.CandidateDto;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<CandidateDto> findAll();

    Page<CandidateDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    Optional<CandidateDto> findById(Long id);

    CandidateDto update(CandidateDto candidateDto) throws RuntimeException;

    CandidateDto save(CandidateDto candidateDto) throws EntityExistsException;

    void deleteById(Long id) throws InvalidEntityException;
}
