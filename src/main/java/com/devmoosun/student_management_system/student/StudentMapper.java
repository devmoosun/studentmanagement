package com.devmoosun.student_management_system.student;

import com.devmoosun.student_management_system.role.RoleMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface StudentMapper {

    StudentResponseDto mapToResponseDto(Student student);


    Student mapToEntity(StudentRequestDto studentRequestDto);

}
