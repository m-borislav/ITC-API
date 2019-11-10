package com.itc.repos;

import com.itc.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseDao extends CrudRepository<Course, Long> {
    Course findByName(String name);
     Set<Course> findAll();
     Optional<Course> findById(Long id);
}
