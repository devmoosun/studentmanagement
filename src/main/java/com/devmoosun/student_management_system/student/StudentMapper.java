package com.devmoosun.student_management_system.student;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentResponseDto mapToResponseDto(Student student);
    Student mapToEntity(StudentRequestDto studentRequestDto);

}
