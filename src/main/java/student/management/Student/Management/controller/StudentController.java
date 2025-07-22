package student.management.Student.Management.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domain.StudentDetail;
import student.management.Student.Management.service.StudentService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller

public class StudentController {

    private StudentService service;
    private StudentConverter converter;


    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentCourses> studentCourses = service.searchStudentCourseList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
        return "studentList";
    }


    @GetMapping("/studentCourseList")
    public List<StudentCourses> getStudentCourseList() {
        return service.searchStudentCourseList();
    }


    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentCourses(Arrays.asList(new StudentCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        //新規受講生情報登録
        service.saveStudentDetail(studentDetail);
        return "redirect:/studentList";
    }


    //受講生情報更新
    @GetMapping("/updateStudent/{id}")
    public String showUpdateStudent(@PathVariable UUID id, Model model) {
        StudentDetail studentDetail = service.getStudentDetailById(id.toString());
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";


    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }
        service.updateStudentDetail(studentDetail);
        return "redirect:/studentList";
    }


}
