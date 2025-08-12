package student.management.Student.Management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
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
    public StudentService(StudentRepository repository, StudentCourseRepository courseRepository, StudentConverter converter) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.converter = converter;
    }

    /**
     * 受講生詳細の一覧検索
     * 全件検索を行うので、条件検索は行わないものになります。
     *
     * @return 受講生詳細一覧(全件)
     */

    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourse> studentCourseList = courseRepository.searchStudentCourseList();
        return converter.convertStudentDetails(studentList, studentCourseList);

    }

    public List<StudentCourse> searchStudentCourseList() {
        return courseRepository.searchStudentCourseList();
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */

    @Transactional
    public StudentDetail saveStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);
        repository.registerStudent(student);

        LocalDate now = LocalDate.now();

        studentDetail.getStudentCourseList().forEach(course -> {
            initStudentsCourse(course, studentId, now);
            courseRepository.registerStudentCourse(course);
        });

        return studentDetail;

    }

    /**
     * 受講生コース情報を登録する際の初期情報
     * @param course　受講生コース情報
     * @param studentId　受講生ID
     * @param now　日付
     */

    private static void initStudentsCourse(StudentCourse course, String studentId, LocalDate now) {
        course.setCourseId(UUID.randomUUID().toString());
        course.setStudentId(studentId);
        course.setStartOfCourse(Date.valueOf(now));
        course.setEndOfCourse(Date.valueOf(now.plusYears(1)));
    }

    /**
     * 受講生詳細検索です。
     * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 　受講生ID
     * @return　受講生詳細
     */

    public StudentDetail getStudentDetailById(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourse> courses = courseRepository.searchStudentCourse(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生の情報を受講生コース情報をそれぞれ更新します。
     * @param studentDetail　受講生詳細
     */
    @Transactional
    public void updateStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);
        for (StudentCourse course : studentDetail.getStudentCourseList()) {
            courseRepository.updateStudentCourse(course);
        }


    }
}