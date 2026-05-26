package com.sirest.repository;

import com.sirest.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Page<Enrollment> findBySemesterContainingIgnoreCase(String semester, Pageable pageable);
}