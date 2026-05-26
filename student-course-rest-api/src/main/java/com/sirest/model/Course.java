package com.sirest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private Integer credits;

    public Course() {
    }

    public Course(Long id, String title, String instructor, Integer credits) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.credits = credits;
    }
}