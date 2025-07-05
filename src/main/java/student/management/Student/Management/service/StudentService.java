package student.management.Student.Management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentCourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository repository , StudentCourseRepository courseRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
    }

    public List<Student> searchStudentList() {

        List<Student> students = repository.search();
        return students.stream()
                .filter(v -> v.getAge() >= 30 && v.getAge() < 40)
                .collect(Collectors.toList());

        }

    public List<StudentCourse> searchStudentCourseList() {

        List<StudentCourse> studentCourses = courseRepository.courseSearch();
        return studentCourses.stream()
                .filter(studentCourse -> "Javaコース".equals(studentCourse.getCourseName()))
                .collect(Collectors.toList());


    }
}