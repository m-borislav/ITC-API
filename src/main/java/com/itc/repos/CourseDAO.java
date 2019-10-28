package com.itc.repos;

import com.itc.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseDAO extends CrudRepository<Course, Long> {
    Course findByName(String name);
     Set<Course> findAll();
  //  @Query("FROM course WHERE studentId = studId")
  //  Set<Course> findStudentCourses(Long studId);

}
