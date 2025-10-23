package com.devmoosun.student_management_system.student;

import com.devmoosun.student_management_system.role.Role;
import com.devmoosun.student_management_system.role.RoleRepository;
import com.devmoosun.student_management_system.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudentMapper studentMapper;
    private RoleRepository roleRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<StudentResponseDto> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::mapToResponseDto).collect(Collectors.toList());
    }

    @Override
    public void createStudent(StudentRequestDto studentRequestDto) {
        Student student = studentMapper.mapToEntity(studentRequestDto);
        student.setPassword(bCryptPasswordEncoder.encode(studentRequestDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            roleService.checkRoleExists();
        }
        assert role != null;
        student.setRoles(new ArrayList<>(List.of(role)));
//       student.setRoles(Arrays.asList(role));
//       student.getRoles().add(role);
        studentRepository.save(student);
    }


    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).get();
        return studentMapper.mapToResponseDto(student);
    }

    @Override
    public void updateStudent(StudentRequestDto studentRequestDto) {
        Student student = studentRepository.findById(studentRequestDto.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        student.setEmail(studentRequestDto.getEmail());
        updatePasswordIfChanged(student, studentRequestDto.getPassword());
        studentRepository.save(student);


    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }


    private void updatePasswordIfChanged(Student student, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            return;
        }
        boolean isSame = bCryptPasswordEncoder.matches(newPassword, student.getPassword());
        if (!isSame) {
            student.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }

    }
}
