package com.example.inttaskbe.mapper;

import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toEntity(SkillDto skillDto);
    SkillDto toDto(Skill skill);
}
