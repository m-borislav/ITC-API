package com.itc.dao;

import com.itc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByEmail(String email);
}
