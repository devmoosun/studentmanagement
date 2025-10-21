package com.devmoosun.student_management_system.student;


import com.devmoosun.student_management_system.common.PageMeta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/students")
    public String listStudents(Model model) {
        List<StudentResponseDto> studentResponseDtos = studentService.getAll();
        model.addAttribute("students", studentResponseDtos);
        model.addAttribute("meta", new PageMeta(
                "All Students",
                "Welcome to the Student Management System.",
                "students, education, portal"
        ));
        return "students";
    }

    //handler method
    @GetMapping("/students/new")
    public String newStudent(Model model) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        model.addAttribute("meta", new PageMeta(
                "Create Student",
                "Welcome to the Student Management System.",
                "students, education, portal"
        ));
        model.addAttribute("student", studentResponseDto);
        return "create_student";
    }

    //submit request
    @PostMapping("/students")
    public String saveStudent(@Valid @ModelAttribute("student") StudentRequestDto studentRequestDto, BindingResult result, Model model) {
        model.addAttribute("meta", new PageMeta(
                "Create Student",
                "Welcome to the Student Management System.",
                "students, education, portal"
        ));
        if (result.hasErrors()) {
            model.addAttribute("student", studentRequestDto);
            return "create_student";
        }
        studentService.createStudent(studentRequestDto);
        return "redirect:/students";
    }
    //edit

    @GetMapping("/students/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
        StudentResponseDto studentResponseDto = studentService.getStudentById(id);
        model.addAttribute("student", studentResponseDto);
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentRequestDto studentRequestDto,
                                BindingResult result,
                                Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("student", studentRequestDto);
            return "edit_student";
        }
        studentRequestDto.setId(id);
        studentService.updateStudent(studentRequestDto);
        return "redirect:/students";

    }

    // handle delete request
    @GetMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return "redirect:/students";
    }
    // handle view request
    @GetMapping("/students/{id}/view")
    public String viewStudent(@PathVariable Long id, Model model) {
      StudentResponseDto studentResponseDto = studentService.getStudentById(id);
      model.addAttribute("student", studentResponseDto);

        return "view_student";
    }


}
