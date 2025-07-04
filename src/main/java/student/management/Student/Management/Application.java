package student.management.Student.Management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

import java.util.List;

@SpringBootApplication

public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }





}