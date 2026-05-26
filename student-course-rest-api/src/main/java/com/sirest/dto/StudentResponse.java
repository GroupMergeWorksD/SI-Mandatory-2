package com.sirest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private Integer age;

    public StudentResponse() {
    }

    public StudentResponse(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

}