package com.sirest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentResponse {
    private Long id;
    private String semester;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseTitle;

    public EnrollmentResponse() {
    }

    public EnrollmentResponse(Long id, String semester, Long studentId, String studentName, Long courseId, String courseTitle) {
        this.id = id;
        this.semester = semester;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }

}