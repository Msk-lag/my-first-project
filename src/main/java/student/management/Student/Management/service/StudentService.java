package student.management.Student.Management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domain.StudentDetail;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentCourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository repository, StudentCourseRepository courseRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
    }

    public List<Student> searchStudentList() {
        return repository.search();

    }

    public List<StudentCourses> searchStudentCourseList() {
        return courseRepository.courseSearch();
    }


    public void saveStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        student.setId(UUID.randomUUID().toString());

        repository.insert(student);
    }

}