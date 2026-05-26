package com.sirest.service;

import com.sirest.exception.ResourceNotFoundException;
import com.sirest.model.Course;
import com.sirest.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourses(String search, int page, int size) {
        return courseRepository.findByTitleContainingIgnoreCase(search, PageRequest.of(page, size));
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
}