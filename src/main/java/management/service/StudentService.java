package management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import management.Repository.StudentCourseApplicationRepository;
import management.Repository.StudentCourseRepository;
import management.Repository.StudentRepository;
import management.controller.converter.StudentConverter;
import management.data.ApplicationStatus;
import management.data.Student;
import management.data.StudentCourse;
import management.data.StudentCourseApplication;
import management.domain.StudentDetail;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 受講情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentCourseRepository courseRepository;
    private StudentCourseApplicationRepository applicationRepository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository,
                          StudentCourseRepository courseRepository,
                          StudentCourseApplicationRepository applicationRepository,
                          StudentConverter converter
    ) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.applicationRepository = applicationRepository;
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
        List<StudentCourseApplication> studentCourseApplicationList = applicationRepository.searchStudentCourseApplicationList();
        return converter.convertStudentDetails(studentList, studentCourseList, studentCourseApplicationList);

    }

    /**
     * 受講生コース情報検索
     *
     * @return 受講生コース情報
     */
    public List<StudentCourse> searchStudentCourseList() {
        return courseRepository.searchStudentCourseList();
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生,受講生コース情報,申し込み情報を個別に登録
     * 受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
     * 申し込み情報には受講生情報、コース情報を紐づける値を設定します
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);
        repository.registerStudent(student);

        LocalDate now = LocalDate.now();

        studentDetail.getStudentCourseList().forEach(course -> {
            initStudentsCourse(course, studentId, now);
            courseRepository.registerStudentCourse(course);

            StudentCourseApplication courseApplication = new StudentCourseApplication();
            initStudentsCourseApplication(courseApplication, course.getCourseId(), studentId);
            applicationRepository.registerCourseApplication(courseApplication);

            studentDetail.getStudentCourseApplicationsList().add(courseApplication);

        });
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報
     *
     * @param course    　受講生コース情報
     * @param studentId 　受講生ID
     * @param now       　日付
     */
    private static void initStudentsCourse(StudentCourse course, String studentId, LocalDate now) {

        course.setCourseId(UUID.randomUUID().toString());
        course.setStudentId(studentId);
        course.setStartOfCourse(Date.valueOf(now));
        course.setEndOfCourse(Date.valueOf(now.plusYears(1)));
    }

    /**
     * 受講生コース申し込み情報情報を登録する際の初期情報
     *
     * @param application 申し込み情報
     * @param studentId   　受講生ID
     * @param courseId    コースID
     *                    CourseStatus には初期情報として仮登録(Provisional)を設定する
     */
    private static void initStudentsCourseApplication(
            StudentCourseApplication application, String courseId, String studentId) {

        application.setApplicationId(UUID.randomUUID().toString());
        application.setStudentId(studentId);
        application.setCourseId(courseId);
        application.setCourseStatus(ApplicationStatus.PROVISIONAL);
    }


    /**
     * 受講生詳細検索です。
     * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報、申し込み情報を取得して設定します。
     *
     * @param id 　受講生ID
     * @return　受講生詳細
     */
    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourse> courses = courseRepository.searchStudentCourse(id);
        List<StudentCourseApplication> courseStatus = searchStudentsCourseApplicationNullCheck(List.of(id));
        return new StudentDetail(student, courses, courseStatus);
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生の情報、受講生コース情報、申し込み情報をそれぞれ更新します。
     *
     * @param studentDetail 　受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);
        for (StudentCourse course : studentDetail.getStudentCourseList()) {
            courseRepository.updateStudentCourse(course);
        }
        for (StudentCourseApplication applicationStatus : studentDetail.getStudentCourseApplicationsList()) {
            applicationRepository.courseStatusUpdate(applicationStatus);
        }
    }

    /**
     * 受講生条件検索
     * 名前、年齢、性別、受講しているコース名に合致する受講生の情報を取得して設定します。
     */
    public List<StudentDetail> searchStudents(String fullName, Integer age, String gender) {
        List<Student> students = repository.searchStudents(fullName, age, gender);
        List<String> studentIds = students.stream()
                .map(Student::getId)
                .toList();
        List<StudentCourse> courses = courseRepository.getCoursesByStudents(studentIds);
        List<StudentCourseApplication> courseStatus = searchStudentsCourseApplicationNullCheck(studentIds);
        return converter.convertStudentDetails(students, courses, courseStatus);
    }

    /**
     * studentIds が null または空リスト場合空のリストを返す
     * studentIds に値がある場合のみ、リポジトリに渡す
     */
    public List<StudentCourseApplication> searchStudentsCourseApplicationNullCheck(List<String> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyList();
        }
        return applicationRepository.searchStudentsCourseApplication(studentIds);
    }
}