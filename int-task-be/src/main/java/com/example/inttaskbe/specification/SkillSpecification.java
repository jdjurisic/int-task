package com.example.inttaskbe.specification;

import com.example.inttaskbe.entity.Skill;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SkillSpecification {
    public static Specification<Skill> nameContains(String name) {
        return (org, cq, cb) -> cb.like(org.get("name"), "%" + name + "%");
    }
}
