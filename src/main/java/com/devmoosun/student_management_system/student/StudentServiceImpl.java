package com.devmoosun.student_management_system.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudentMapper studentMapper;

    @Override
    public List<StudentResponseDto> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::mapToResponseDto).collect(Collectors.toList());
    }

    @Override
    public void createStudent(StudentRequestDto studentRequestDto) {

        Student student = studentMapper.mapToEntity(studentRequestDto);
        studentRepository.save(student);
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
       Student student = studentRepository.findById(id).get();
        return studentMapper.mapToResponseDto(student);
    }

    @Override
    public void updateStudent(StudentRequestDto studentRequestDto) {

        studentRepository.save(studentMapper.mapToEntity(studentRequestDto));
    }
}
