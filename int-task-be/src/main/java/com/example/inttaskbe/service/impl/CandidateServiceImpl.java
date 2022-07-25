package com.example.inttaskbe.service.impl;

import com.example.inttaskbe.dao.CandidateDao;
import com.example.inttaskbe.dto.CandidateDto;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.entity.Candidate;
import com.example.inttaskbe.entity.Skill;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import com.example.inttaskbe.mapper.CandidateMapper;
import com.example.inttaskbe.service.CandidateService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.inttaskbe.specification.CandidateSpecification.containsSkillWithName;
import static com.example.inttaskbe.specification.CandidateSpecification.nameContains;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateDao candidateDao;
    private final CandidateMapper candidateMapper = Mappers.getMapper(CandidateMapper.class);

    public CandidateServiceImpl(CandidateDao candidateDao) {
        this.candidateDao = candidateDao;
    }

    @Override
    public List<CandidateDto> findAll() {
        List<Candidate> entities = candidateDao.findAll();
        return entities.stream().map(entity ->{
            return candidateMapper.toDto(entity);
        }).collect(Collectors.toList());
    }

    @Override
    public Page<CandidateDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        Page<CandidateDto> entites = candidateDao.findAll(nameContains(nameFilter), pageable).map(candidateMapper::toDto);
        return entites;
    }

    @Override
    public Optional<CandidateDto> findById(Long id) {
        Optional<Candidate> entity = candidateDao.findById(id);
        if (entity.isPresent()) {
            return Optional.of(candidateMapper.toDto(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public CandidateDto update(CandidateDto candidateDto) throws RuntimeException {
        Optional<Candidate> entity = candidateDao.findById(candidateDto.getId());
        if (!entity.isPresent()) {
            throw new RuntimeException("Candidate does not exist:" + candidateDto.getFullName());
        }
        Candidate c = candidateDao.save(candidateMapper.toEntity(candidateDto));
        return candidateMapper.toDto(c);
    }

    @Override
    public CandidateDto save(CandidateDto candidateDto) throws EntityExistsException {
        if(candidateDto.getId() != null){
            Optional<Candidate> entity = candidateDao.findById(candidateDto.getId());
            if (entity.isPresent()) {
                throw new EntityExistsException(entity.get(),"Skill already exists!");
            }
        }
        Candidate candidate = candidateDao.save(candidateMapper.toEntity(candidateDto));
        return candidateMapper.toDto(candidate);
    }

    @Override
    public void deleteById(Long id) throws InvalidEntityException {
        Optional<Candidate> entity = candidateDao.findById(id);
        if (!entity.isPresent()) {
            throw new InvalidEntityException("Invalid skill entity!");
        }
        candidateDao.deleteById(id);
    }

    @Override
    public Page<CandidateDto> findAllBySkill(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String skillName) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        Page<CandidateDto> entites = candidateDao.findAll(containsSkillWithName(skillName), pageable).map(candidateMapper::toDto);
        return entites;
    }
}
