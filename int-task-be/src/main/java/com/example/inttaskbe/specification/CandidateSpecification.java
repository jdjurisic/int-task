package com.example.inttaskbe.specification;

import com.example.inttaskbe.entity.Candidate;
import com.example.inttaskbe.entity.Skill;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

@Component
public class CandidateSpecification {
    public static Specification<Candidate> nameContains(String name) {
        return (org, cq, cb) -> cb.like(org.get("fullName"), "%" + name + "%");
    }

    public static Specification<Candidate> containsSkillWithName(String name) {
        return (root, query, cb) -> {
            Join<Object, Object> bListJoin = root.join("skills", JoinType.INNER);
            return cb.like(bListJoin.get("name"), "%" + name + "%");
        };
    }
}
