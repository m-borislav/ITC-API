package com.itc.dao;

import com.itc.domain.Course;
import com.itc.domain.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface LessonDao extends CrudRepository<Lesson, Long> {
    Set<Lesson> findAll();
    Optional<Lesson> findById(Long id);
    HashSet<Lesson> findByCousre(Course course);
}
