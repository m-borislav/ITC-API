package com.itc.controller;

import com.itc.domain.Course;
import com.itc.repos.CourseDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
public class CourseController {
    private final CourseDAO courseDAO;

    @Autowired
    public CourseController(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @GetMapping("/courses")
    public Set<Course> allCourses() {
        return courseDAO.findAll();
    }

    @GetMapping("/courses/{id}")
    public Course findCourse(@PathVariable Long id) {
        return courseDAO.findById(id).get();
    }

    @PostMapping(value = "/courses",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseDAO.save(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PatchMapping(value = "/courses/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                               @RequestBody Course course,
                                               HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findCourse/" + course.getId()).toUriString());
        Course courseFromDB = courseDAO.findById(id).get();
        BeanUtils.copyProperties(course, courseFromDB);
        courseDAO.save(courseFromDB);
        return new ResponseEntity<>(courseFromDB, HttpStatus.OK);
    }

    @DeleteMapping(value = "/courses/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id, HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findCourse/" + id).toUriString());
        courseDAO.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
