package com.devmoosun.student_management_system.student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface StudentService {


    List<StudentResponseDto> getAll();

    void createStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto getStudentById(Long id);

    void updateStudent(@Valid StudentRequestDto studentRequestDto);

    void deleteStudent(Long id);

    Student findStudentByEmail(@Email(message = "Must be a valid email address") String email);
//    StudentResponseDto cr
}
