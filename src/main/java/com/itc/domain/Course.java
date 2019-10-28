package com.itc.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;
    @Column(name = "cost",  nullable = false,  length = 10)
    private String cost;
    @Column(name = "creationDate",  nullable = false,  length = 15)
    private LocalDateTime creationDate;
    @Column(name = "rating",  nullable = false,  length = 1)
    private int rating;
    @Column(name = "duration", nullable = false,  length = 4)
    private String duration;
    @Column(name = "courseInfoFilePath",  nullable = true, length = 300)
    private String courseInfoFilePath;
    @Column(name = "courseVideoLink",  nullable = true, length = 300)
    private String videoLink;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCourseInfoFilePath() {
        return courseInfoFilePath;
    }

    public void setCourseInfoFilePath(String courseInfoFilePath) {
        this.courseInfoFilePath = courseInfoFilePath;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id) &&
                name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
