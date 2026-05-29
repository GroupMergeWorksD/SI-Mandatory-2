package com.sirest.service;

import com.sirest.dto.StudentRequest;
import com.sirest.dto.StudentResponse;
import com.sirest.exception.ResourceNotFoundException;
import com.sirest.model.Enrollment;
import com.sirest.model.Student;
import com.sirest.repository.EnrollmentRepository;
import com.sirest.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Page<StudentResponse> getAllStudents(String search, int page, int size) {
        return studentRepository
                .findByNameContainingIgnoreCase(search, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToResponse(student);
    }

    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());

        return mapToResponse(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());

        return mapToResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        Enrollment enrollment = enrollmentRepository.findByStudentId(id);
        if (enrollment != null) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Cannot delete student with active enrollments"
            );
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge()
        );
    }
}