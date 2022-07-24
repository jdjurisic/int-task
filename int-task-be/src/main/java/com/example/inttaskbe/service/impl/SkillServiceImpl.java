package com.example.inttaskbe.service.impl;

import com.example.inttaskbe.dao.SkillDao;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.entity.Skill;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import com.example.inttaskbe.mapper.SkillMapper;
import com.example.inttaskbe.service.SkillService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillDao skillDao;
    private final SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    public SkillServiceImpl(SkillDao skillDao) {
        this.skillDao = skillDao;
    }


    @Override
    public List<SkillDto> findAll() {
        List<Skill> entities = skillDao.findAll();
        return entities.stream().map(entity ->{
            return skillMapper.toDto(entity);
        }).collect(Collectors.toList());
    }

    @Override
    public Page<SkillDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        Page<SkillDto> entites = skillDao.findAll(pageable).map(skillMapper::toDto);
        return entites;
    }

    @Override
    public Optional<SkillDto> findById(Long id) {
        Optional<Skill> entity = skillDao.findById(id);
        if (entity.isPresent()) {
            return Optional.of(skillMapper.toDto(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public SkillDto update(SkillDto skillDto) throws RuntimeException {
        Optional<Skill> entity = skillDao.findById(skillDto.getId());
        if (!entity.isPresent()) {
            throw new RuntimeException("Skill does not exist:" + skillDto.getName());
        }
        Skill c = skillDao.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(c);
    }

    @Override
    public SkillDto save(SkillDto skillDto) throws EntityExistsException {
            Optional<Skill> entity = skillDao.findByName(skillDto.getName());
            if (entity.isPresent()) {
                throw new EntityExistsException(entity.get(),"Skill already exists!");
            }
        Skill skill = skillDao.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(skill);
    }

    @Override
    public void deleteById(Long id) throws InvalidEntityException {
        Optional<Skill> entity = skillDao.findById(id);
        if (!entity.isPresent()) {
            throw new InvalidEntityException("Invalid skill entity!");
        }
        skillDao.deleteById(id);
    }
}
