package com.equalpath.repository;

import com.equalpath.domain.Application;
import com.equalpath.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByUser(User user, Pageable pageable);
}
