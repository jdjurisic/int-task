package com.example.inttaskbe.mapper;

import com.example.inttaskbe.dto.CandidateDto;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.entity.Candidate;
import com.example.inttaskbe.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    Candidate toEntity(CandidateDto candidateDto);
    CandidateDto toDto(Candidate candidate);
}
