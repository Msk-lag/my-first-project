package student.management.Student.Management.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.Student.Management.Repository.StudentCourseApplicationRepository;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.ApplicationStatus;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.data.StudentCourseApplication;
import student.management.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentCourseRepository courseRepository;

    @Mock
    private StudentCourseApplicationRepository applicationRepository;

    @Mock
    private StudentConverter converter;


    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, courseRepository, applicationRepository, converter);
    }

    @Test
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処理を適切に呼び出せていること() {
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        List<StudentCourseApplication> studentCourseApplicationList = new ArrayList<>();

        when(repository.search()).thenReturn(studentList);
        when(courseRepository.searchStudentCourseList()).thenReturn(studentCourseList);
        when(applicationRepository.searchStudentCourseApplicationList()).thenReturn(studentCourseApplicationList);

        sut.searchStudentList();

        verify(repository, times(1)).search();
        verify(courseRepository, times(1)).searchStudentCourseList();
        verify(applicationRepository, times(1)).searchStudentCourseApplicationList();
        verify(converter, times(1)).convertStudentDetails(
                studentList, studentCourseList, studentCourseApplicationList);
    }

    @Test
    void 受講生詳細検索_リポジトリが適切に呼び出されているのか() {
        Student student = new Student();
        List<StudentCourse> courses = new ArrayList<>();
        List<StudentCourseApplication> applications = new ArrayList<>();
        String id = "dummy";

        when(repository.searchStudent(id)).thenReturn(student);
        when(courseRepository.searchStudentCourse(id)).thenReturn(courses);
        when(applicationRepository.searchStudentsCourseApplication(List.of(id))).thenReturn(applications);

        sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(id);
        verify(courseRepository, times(1)).searchStudentCourse(id);
        verify(applicationRepository, times(1)).searchStudentsCourseApplication(List.of(id));
    }

    @Test
    void 受講生条件検索時_リポジトリとコンバーターの処理を適切に呼び出せていること() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId("dummy");
        students.add(student);

        List<StudentCourse> courses = new ArrayList<>();
        List<StudentCourseApplication> applications = new ArrayList<>();

        when(repository.searchStudents(any(), any(), any()))
                .thenReturn(students);
        when(courseRepository.getCoursesByStudents(List.of("dummy")))
                .thenReturn(courses);
        when(applicationRepository.searchStudentsCourseApplication(List.of("dummy")))
                .thenReturn(applications);

        sut.searchStudents("", null, "");

        verify(repository, times(1)).searchStudents(any(), any(), any());
        verify(courseRepository, times(1)).getCoursesByStudents(List.of("dummy"));
        verify(applicationRepository, times(1)).searchStudentsCourseApplication(List.of("dummy"));
        verify(converter, times(1)).convertStudentDetails(students, courses, applications);
    }

    @Test
    void 受講生条件検索時一括でコース情報を取得しているか() {
        List<String> studentId = List.of("id1", "id2");

        courseRepository.getCoursesByStudents(List.of("id1", "id2"));
        applicationRepository.searchStudentsCourseApplication(List.of("id1", "id2"));

        verify(courseRepository, times(1)).getCoursesByStudents(studentId);
        verify(applicationRepository, times(1)).searchStudentsCourseApplication(studentId);
    }

    @Test
    void 受講生登録_リポジトリ適切に呼び出されているのか() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        StudentCourseApplication courseApplication = new StudentCourseApplication();
        StudentDetail actual = new StudentDetail();

        actual.setStudent(student);
        actual.setStudentCourseList(List.of(studentCourse));
        actual.setStudentCourseApplicationsList(new ArrayList<>(List.of(courseApplication)));

        sut.registerStudent(actual);

        verify(repository, times(1)).registerStudent(any(Student.class));
        verify(courseRepository, times(1)).registerStudentCourse(any(StudentCourse.class));
        verify(applicationRepository, times(1)).registerCourseApplication(any(StudentCourseApplication.class));
    }

    @Test
    void 受講生登録時nullを入力してもリポジトリ適切に呼び出されているのか() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        StudentDetail actual = new StudentDetail();

        actual.setStudent(student);
        actual.setStudentCourseList(List.of(studentCourse));
        actual.setStudentCourseApplicationsList(null);

        sut.registerStudent(actual);

        verify(repository, times(1)).registerStudent(any(Student.class));
        verify(courseRepository, times(1)).registerStudentCourse(any(StudentCourse.class));
        verify(applicationRepository, times(1)).registerCourseApplication(any(StudentCourseApplication.class));
    }

    @Test
    void 受講生詳細の更新_リポジトリが適切に呼び出されているのか() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        StudentCourseApplication courseApplication = new StudentCourseApplication();
        StudentDetail studentDetail = new StudentDetail();

        studentDetail.setStudent(student);
        studentDetail.setStudentCourseList(List.of(studentCourse));
        studentDetail.setStudentCourseApplicationsList(List.of(courseApplication));

        sut.updateStudent(studentDetail);

        verify(repository, times(1)).updateStudent(student);
        verify(courseRepository, times(1)).updateStudentCourse(studentCourse);
        verify(applicationRepository, times(1)).courseStatusUpdate(courseApplication);
    }

}




