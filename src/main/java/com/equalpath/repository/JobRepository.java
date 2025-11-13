package com.equalpath.repository;

import com.equalpath.domain.Job;
import com.equalpath.domain.enums.JobMode;
import com.equalpath.domain.enums.SeniorityLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByModalidadeAndSenioridade(
            JobMode modalidade,
            SeniorityLevel senioridade,
            Pageable pageable
    );
}
