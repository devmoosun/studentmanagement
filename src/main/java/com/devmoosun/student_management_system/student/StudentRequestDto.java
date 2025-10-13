package com.devmoosun.student_management_system.student;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentRequestDto {

    private Long id;

    @Size(min = 2, message = "Firstname cannot be less than 2 characters")
    private String firstName;

    @Size(min = 2, message = "Lastname cannot be less than 2 characters")
    private String lastName;

    @Email(message = "Must be a valid email address")
    private String email;



}
