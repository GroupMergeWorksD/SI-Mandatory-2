package com.sirest.controller;

import com.sirest.model.Course;
import com.sirest.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public PagedModel<EntityModel<Course>> getAllCourses(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Course> coursePage = courseService.getAllCourses(search, page, size);

        List<EntityModel<Course>> courses = coursePage.getContent().stream()
                .map(course -> EntityModel.of(course,
                        linkTo(methodOn(CourseController.class).getCourseById(course.getId())).withSelfRel(),
                        linkTo(methodOn(CourseController.class).getAllCourses(search, page, size)).withRel("courses")
                ))
                .toList();

        return PagedModel.of(
                courses,
                new PagedModel.PageMetadata(coursePage.getSize(), coursePage.getNumber(), coursePage.getTotalElements())
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);

        return EntityModel.of(course,
                linkTo(methodOn(CourseController.class).getCourseById(id)).withSelfRel(),
                linkTo(methodOn(CourseController.class).getAllCourses("", 0, 5)).withRel("courses")
        );
    }
}