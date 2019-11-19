package com.itc.controller;

import com.itc.domain.Course;
import com.itc.domain.Role;
import com.itc.domain.User;
import com.itc.repos.CourseDao;
import com.itc.repos.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "false")
public class CourseController {
    private final CourseDao courseDao;
    private final UserDao userDao;

    @Autowired
    public CourseController(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
    }

    @GetMapping(value = "/api/courses")
    public Set<Course> allCourses() {
        return courseDao.findAll();
    }

    @GetMapping("/api/courses/{id}")
    public Course findCourse(@PathVariable Long id) {
        return courseDao.findById(id).get();
    }

    @PostMapping(value = "/api/admin/courses",
            consumes = "application/json",
            produces = "application/json")
    public Course createCourse(@RequestBody Course course,
                               @RequestBody User user) {
        User userFromDb = userDao.findByEmail(user.getEmail());

        if (userFromDb != null && userFromDb.isAuthentificated() && user.getRoles().contains(Role.ADMIN)) {
            courseDao.save(course);
            return course;
            //return new ResponseEntity<>(course, HttpStatus.OK);
        } //else return new ResponseEntity<>(course, HttpStatus.FORBIDDEN);
        return course;
    }

    @PatchMapping(value = "/api/admin/courses/{id}",
            consumes = "application/json",
            produces = "application/json")
    public Course updateCourse(@PathVariable Long id,
                               @RequestBody Course course,
                               @RequestBody User user,
                               HttpServletResponse response) {
        User userFromDb = userDao.findByEmail(user.getEmail());

        if (userFromDb != null && userFromDb.isAuthentificated() && user.getRoles().contains(Role.ADMIN)) {
            response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/findCourse/" + course.getId()).toUriString());
            Course courseFromDB = courseDao.findById(id).get();
            BeanUtils.copyProperties(course, courseFromDB);
            courseDao.save(courseFromDB);
            return courseFromDB;
        }
        return null;
    }

    @DeleteMapping(value = "/api/user/{userId}/courses/{courseId}")
    public String deleteCourse(@PathVariable Long userId,
                               @PathVariable Long courseId) {
            User userFromDb = userDao.findById(userId).get();

        if (userFromDb != null && userFromDb.isAuthentificated() && userFromDb.getRoles().contains(Role.ADMIN)) {
            courseDao.deleteById(courseId);

            return "Deleted!";
        } else return "Fuck you!"; //new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

 /*   @PatchMapping(value = "/api/admin/courses/{courseId}/enroll",
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
         //   courseFromDB.setStudents(courseFromDB.getStudents().add(userFromDb));
            courseDao.save(courseFromDB);
        } catch (CourseNotFoundException | UserNotFoundException) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


}
