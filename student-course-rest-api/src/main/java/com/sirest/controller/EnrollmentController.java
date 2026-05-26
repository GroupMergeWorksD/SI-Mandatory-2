package com.sirest.controller;

import com.sirest.dto.EnrollmentResponse;
import com.sirest.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public PagedModel<EntityModel<EnrollmentResponse>> getAllEnrollments(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<EnrollmentResponse> enrollmentPage = enrollmentService.getAllEnrollments(search, page, size);

        List<EntityModel<EnrollmentResponse>> enrollments = enrollmentPage.getContent().stream()
                .map(enrollment -> EntityModel.of(enrollment,
                        linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel(),
                        linkTo(methodOn(EnrollmentController.class).getAllEnrollments(search, page, size)).withRel("enrollments")
                ))
                .toList();

        return PagedModel.of(
                enrollments,
                new PagedModel.PageMetadata(
                        enrollmentPage.getSize(),
                        enrollmentPage.getNumber(),
                        enrollmentPage.getTotalElements()
                )
        );
    }

    @GetMapping("/{id}")
    public EntityModel<EnrollmentResponse> getEnrollmentById(@PathVariable Long id) {
        EnrollmentResponse enrollment = enrollmentService.getEnrollmentById(id);

        return EntityModel.of(enrollment,
                linkTo(methodOn(EnrollmentController.class).getEnrollmentById(id)).withSelfRel(),
                linkTo(methodOn(EnrollmentController.class).getAllEnrollments("", 0, 5)).withRel("enrollments")
        );
    }
}