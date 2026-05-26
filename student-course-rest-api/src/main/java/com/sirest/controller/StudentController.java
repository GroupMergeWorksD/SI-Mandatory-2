package com.sirest.controller;

import com.sirest.config.ApiPaths;
import com.sirest.dto.DeleteStudentResponse;
import com.sirest.dto.StudentRequest;
import com.sirest.dto.StudentResponse;
import com.sirest.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(ApiPaths.API_V1 + "/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public PagedModel<EntityModel<StudentResponse>> getAllStudents(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<StudentResponse> studentPage = studentService.getAllStudents(search, page, size);

        List<EntityModel<StudentResponse>> students = studentPage.getContent().stream()
                .map(student -> EntityModel.of(student,
                        linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                        linkTo(methodOn(StudentController.class).getAllStudents(search, page, size)).withRel("students"),
                        linkTo(methodOn(StudentController.class).updateStudent(student.getId(), null)).withRel("update"),
                        linkTo(methodOn(StudentController.class).deleteStudent(student.getId())).withRel("delete")
                ))
                .toList();

        return PagedModel.of(
                students,
                new PagedModel.PageMetadata(studentPage.getSize(), studentPage.getNumber(), studentPage.getTotalElements())
        );
    }

    @GetMapping("/{id}")
    public EntityModel<StudentResponse> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.getStudentById(id);

        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents("", 0, 5)).withRel("students"),
                linkTo(methodOn(StudentController.class).updateStudent(id, null)).withRel("update"),
                linkTo(methodOn(StudentController.class).deleteStudent(id)).withRel("delete")
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.createStudent(request);

        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents("", 0, 5)).withRel("students")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.updateStudent(id, request);

        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents("", 0, 5)).withRel("students")
        );
    }

    @DeleteMapping("/{id}")
    public EntityModel<DeleteStudentResponse> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        DeleteStudentResponse deleteStudentResponse = new DeleteStudentResponse("Student deleted successfully");

        return EntityModel.of(deleteStudentResponse,
                linkTo(methodOn(StudentController.class).getAllStudents("", 0, 5)).withRel("students"));
    }
}