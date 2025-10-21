package com.devmoosun.student_management_system.auth;

import com.devmoosun.student_management_system.student.Student;
import com.devmoosun.student_management_system.student.StudentRequestDto;
import com.devmoosun.student_management_system.student.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private StudentService studentService;

    //homepage
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        model.addAttribute("student", studentRequestDto);
        return "register";
    }

    //handle reg request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("student") StudentRequestDto studentRequestDto, BindingResult result, Model model) {
        Student existingStudent = studentService.findStudentByEmail(studentRequestDto.getEmail());
        if (existingStudent != null && existingStudent.getEmail() != null && existingStudent.getEmail().isEmpty()) {
            result.rejectValue("email", "null", "Email already existed");
        }
        if (result.hasErrors()) {
            model.addAttribute("student", studentRequestDto);
            return "/register";
        }
        studentService.createStudent(studentRequestDto);
        return "redirect:/register?success";


    }
}
