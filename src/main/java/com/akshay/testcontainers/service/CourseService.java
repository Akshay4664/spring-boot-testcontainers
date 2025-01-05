package com.akshay.testcontainers.service;

import com.akshay.testcontainers.entity.Course;
import com.akshay.testcontainers.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    @Autowired
    CourseRepository courseRepository;

    public Course addNewCourse(Course course) {
        log.info("Course Service:: Add new method executed");
        return courseRepository.save(course);
    }

    public List<Course> getAllAvailableCourses() {
        log.info("Course Service:: get all available courses method executed");
        return courseRepository.findAll();
    }
}
