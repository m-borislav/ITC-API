package com.itc.repos;

import com.itc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsDao extends JpaRepository<User, String> {

}
