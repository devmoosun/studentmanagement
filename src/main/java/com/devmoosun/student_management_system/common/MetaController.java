package com.devmoosun.student_management_system.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meta")
public class MetaController {

    @GetMapping("/home")
    public PageMeta homeMeta() {
        return new PageMeta("Home", "Welcome to the Student Management System.", "students, education, portal");
    }

    @GetMapping("/students")
    public PageMeta studentsMeta() {
        return new PageMeta("Students", "List of all enrolled students.", "students, courses, management");
    }
}
