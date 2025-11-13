package com.equalpath.repository;

import com.equalpath.domain.UserSkill;
import com.equalpath.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUser(User user);
}
