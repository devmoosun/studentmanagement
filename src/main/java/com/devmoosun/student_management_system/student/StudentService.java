package com.devmoosun.student_management_system.student;

import jakarta.validation.Valid;

import java.util.List;

public interface StudentService {


    List<StudentResponseDto> getAll();

    void createStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto getStudentById(Long id);

    void updateStudent(@Valid StudentRequestDto studentRequestDto);
//    StudentResponseDto cr
}
