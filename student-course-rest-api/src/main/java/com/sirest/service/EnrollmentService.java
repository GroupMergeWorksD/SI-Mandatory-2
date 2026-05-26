package com.sirest.service;

import com.sirest.dto.EnrollmentResponse;
import com.sirest.exception.ResourceNotFoundException;
import com.sirest.model.Enrollment;
import com.sirest.repository.EnrollmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Page<EnrollmentResponse> getAllEnrollments(String search, int page, int size) {
        return enrollmentRepository
                .findBySemesterContainingIgnoreCase(search, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    public EnrollmentResponse getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        return mapToResponse(enrollment);
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getSemester(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getName(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle()
        );
    }
}