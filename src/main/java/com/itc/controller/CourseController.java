package com.itc.controller;

import com.itc.domain.Course;
import com.itc.repos.CourseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Controller
public class CourseController {
    @Autowired
    private CourseDAO courseDAO;

    @GetMapping
    public String main(Map<String, Object> model) {
        Set<Course> courses = courseDAO.findAll();

        model.put("courses", courses.toArray());

        return "course";
    }

    @PostMapping
    public String add(@RequestParam(required = false) String name,
                      @RequestParam(required = false) String cost,
                      @RequestParam(required = false) String rating,
                      @RequestParam(required = false) String duration,
                      @RequestParam(required = false) String videoLink,
                      Map<String, Object> model) {
        Course course = new Course();
        course.setName(name);
        course.setCost(cost);
        course.setCreationDate(LocalDateTime.now());
        course.setRating(Integer.parseInt(rating));
        course.setDuration(duration);
        course.setVideoLink(videoLink);

        courseDAO.save(course);
        Set<Course> courses = courseDAO.findAll();

        model.put("courses", courses.toArray());

        return "course";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Set<Course> courses;

        if (filter != null && !filter.isEmpty()) {
            courses = Collections.singleton(courseDAO.findByName(filter));
        } else {
            courses = courseDAO.findAll();
        }

        model.put("courses", courses.toArray());

        return "course";
    }
}
