package com.example.inttaskbe.dao;

import com.example.inttaskbe.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateDao extends JpaRepository<Candidate, Long> {
}
