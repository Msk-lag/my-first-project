package student.management.Student.Management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domain.StudentDetail;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void saveStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);
        repository.insert(student);

        LocalDate now = LocalDate.now();


        for (StudentCourses course : studentDetail.getStudentCourses()) {
            course.setCourseId(UUID.randomUUID().toString());
            course.setStudentId(studentId);
            course.setStartOfCourse(Date.valueOf(now));
            course.setEndOfCourse(Date.valueOf(now.plusYears(1)));

            courseRepository.insert(course);


        }

    }

    public StudentDetail getStudentDetailById(String id) {
        Student student = repository.findById(id);
        List<StudentCourses> courses = courseRepository.findByStudentId(id);
        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourses(courses);
        return detail;
    }


    @Transactional
    public void updateStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.update(student);
        for (StudentCourses course : studentDetail.getStudentCourses()) {
            courseRepository.update(course);
        }


    }
}