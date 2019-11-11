package com.itc.controller;

import com.itc.domain.Course;
import com.itc.domain.User;
import com.itc.exception.CourseNotFoundException;
import com.itc.exception.UserNotFoundException;
import com.itc.repos.CourseDao;
import com.itc.repos.UserDao;
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
    private final CourseDao courseDao;
    private final UserDao userDao;

    @Autowired
    public CourseController(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
    }

    @GetMapping("/api/admin/courses")
    public Set<Course> allCourses() {
        return courseDao.findAll();
    }

    @GetMapping("/api/admin/courses/{id}")
    public Course findCourse(@PathVariable Long id) {
        return courseDao.findById(id).get();
    }

    @PostMapping(value = "/api/admin/courses",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseDao.save(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PatchMapping(value = "/api/admin/courses/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                               @RequestBody Course course,
                                               HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findCourse/" + course.getId()).toUriString());
        Course courseFromDB = courseDao.findById(id).get();
        BeanUtils.copyProperties(course, courseFromDB);
        courseDao.save(courseFromDB);
        return new ResponseEntity<>(courseFromDB, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/admin/courses/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id, HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findCourse/" + id).toUriString());
        courseDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/api/admin/courses/{courseId}/enroll",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Course> enrollOnCourse(@PathVariable Long courseId,
                                                 @RequestBody User user,
                                                 HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findCourse/" + courseId).toUriString());
        try {
            Course courseFromDB = courseDao.findById(courseId).get();
            User userFromDb = userDao.findById(String.valueOf(user.getId())).get();
            if(courseFromDB == null) {
                throw new CourseNotFoundException();
            }
            if(userFromDb == null) {
                throw new UserNotFoundException();
            }
            courseFromDB.setStudents(courseFromDB.getStudents().add(userFromDb));
            courseDao.save(courseFromDB);
        } catch (CourseNotFoundException | UserNotFoundException) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
