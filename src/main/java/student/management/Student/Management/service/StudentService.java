package student.management.Student.Management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domain.StudentDetail;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * 受講情報を取り扱うサービスです。
 * 受講生の検索や登・更新処理を行います。
 */

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentCourseRepository courseRepository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentCourseRepository courseRepository,StudentConverter converter) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.converter = converter;
    }

    /**
     * 受講生一覧検索
     * 全件検索を行うので、条件検索は行わないものになります。
     * @return 受講生一覧(全件)
     */

    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourses> studentCoursesList = courseRepository.courseSearch();
        return converter.convertStudentDetails(studentList, studentCoursesList);

    }

    public List<StudentCourses> searchStudentCourseList() {
        return courseRepository.courseSearch();
    }

    @Transactional
    public StudentDetail saveStudentDetail(StudentDetail studentDetail) {
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
        return studentDetail;

    }

    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
     * @param id　受講生ID
     * @return　受講生
     */

    public StudentDetail getStudentDetailById(String id) {
        Student student = repository.findById(id);
        List<StudentCourses> courses = courseRepository.findByStudentId(id);
        return new StudentDetail(student,courses);
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