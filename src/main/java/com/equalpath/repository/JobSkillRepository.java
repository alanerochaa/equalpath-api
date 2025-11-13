package com.equalpath.repository;

import com.equalpath.domain.JobSkill;
import com.equalpath.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    List<JobSkill> findByJob(Job job);
}
