package com.devmoosun.student_management_system.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);
//    Optional<Student> findByEmail(String email);
}
