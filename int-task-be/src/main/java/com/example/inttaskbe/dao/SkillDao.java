package com.example.inttaskbe.dao;

import com.example.inttaskbe.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillDao extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
}
