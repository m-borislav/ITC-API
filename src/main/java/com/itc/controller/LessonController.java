package com.itc.controller;

import com.itc.dao.CourseDao;
import com.itc.dao.LessonDao;
import com.itc.dao.UserDao;
import com.itc.domain.Course;
import com.itc.domain.Lesson;
import com.itc.domain.User;
import com.itc.exception.CourseNotFoundException;
import com.itc.exception.LessonNotFoundException;
import com.itc.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class LessonController {

    private LessonDao lessonDao;
    private CourseDao courseDao;
    private UserDao userDao;

    public LessonController(LessonDao lessonDao, CourseDao courseDao) {
        this.lessonDao = lessonDao;
        this.courseDao = courseDao;
        this.userDao = userDao;
    }

    @Autowired


    @GetMapping("/lesson/{id}")
    public Lesson getLesson(@PathVariable Long id){
        return lessonDao.findById(id).get();
    }

    @GetMapping("/lesson")
    public Set<Lesson> getLesson(){
        return lessonDao.findAll();
    }

    @PostMapping(value = "/lesson", consumes = "application/json", produces = "application/json")
    public Lesson addLesson(@RequestBody Lesson lesson){
        lessonDao.save(lesson);
        return lesson;
    }

    @PatchMapping(value = "/lesson/{id}", consumes = "application/json", produces = "application/json")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody Lesson newLesson){
        Lesson lessonFromDb = lessonDao.findById(id).get();
        if (lessonFromDb != null){
            lessonFromDb = lessonDao.findById(id).get();
            BeanUtils.copyProperties(newLesson, lessonFromDb);
            lessonDao.save(lessonFromDb);
            return lessonFromDb;
        }
        return null;
    }

    @DeleteMapping(value = "/lesson/{id}", consumes = "application/json", produces = "application/json")
    public void deleteLesson(@PathVariable Long id){
        lessonDao.deleteById(id);
    }

    @PatchMapping(value = "user/{userId}/course/{courseId}/lesson/{lessonId}/courseLesson",
            consumes = "application/json", produces = "application/json")
    public Set<Lesson> getCourseLesson(@PathVariable Long lessonId,
                                       @PathVariable Long courseId,
                                       @PathVariable Long userId, @RequestBody Course course){
        Course courseFromDB = courseDao.findById(courseId).get();
        User userFromDb = userDao.findById(userId).get();
        Lesson lessonFromDb = lessonDao.findById(lessonId).get();
        if (courseFromDB == null) {
            throw new CourseNotFoundException();
        }
        if (userFromDb == null) {
            throw new UserNotFoundException();
        }
        if (lessonFromDb == null) {
            throw new LessonNotFoundException();
        }
        if (userFromDb.isAuthentificated() && userFromDb.isEnrolledOnCourse()) {
            return (Set<Lesson>) lessonDao.findByCousre(course);
        }
        return null;
    }


}
