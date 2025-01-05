package com.akshay.testcontainers.contollers;

import com.akshay.testcontainers.entity.Course;
import com.akshay.testcontainers.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        log.info("CourseController :: add course method executed");
        return courseService.addNewCourse(course);
    }

    @GetMapping
    public List<Course> viewAllCourses(){
        log.info("CourseController:: view all courses");
        return courseService.getAllAvailableCourses();
    }
}
