package student.management.Student.Management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
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
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before(){
        sut  = new StudentService(repository,courseRepository,converter);
    }

    @Test
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処理を適切に呼び出せていること() {
        List<Student>studentList = new ArrayList<>();
        List<StudentCourse>studentCourseList = new ArrayList<>();

       when(repository.search()).thenReturn(studentList);
       when(courseRepository.searchStudentCourseList()).thenReturn(studentCourseList);

        sut.searchStudentList();

        verify(repository, times(1)).search();
        verify(courseRepository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList,studentCourseList);
    }

    @Test
    void 受講生詳細検索_リポジトリが適切に呼び出されているのか(){
        Student student = new Student();
        List<StudentCourse> courses = new ArrayList<>();
        String id = "dummy";

        when(repository.searchStudent(id)).thenReturn(student);
        when(courseRepository.searchStudentCourse(id)).thenReturn(courses);

        sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(id);
        verify(courseRepository, times(1)).searchStudentCourse(id);
    }

    @Test
    void 受講生登録_リポジトリ適切に呼び出されているのか生成されたUUIDの形式が正しいものか(){
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        StudentDetail studentDetail = new StudentDetail();

        studentDetail.setStudent(student);
        studentDetail.setStudentCourseList(List.of(studentCourse));

        sut.registerStudent(studentDetail);

        verify(repository, times(1)).registerStudent(student);
        verify(courseRepository, times(1)).registerStudentCourse(studentCourse);

        String id = studentDetail.getStudent().getId();
        assertDoesNotThrow(() -> UUID.fromString(id));

    }

    @Test
    void  受講生詳細の更新_リポジトリが適切に呼び出されているのか(){
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        StudentDetail studentDetail = new StudentDetail();

        studentDetail.setStudent(student);
        studentDetail.setStudentCourseList(List.of(studentCourse));

        sut.updateStudent(studentDetail);

        verify(repository, times(1)).updateStudent(student);
        verify(courseRepository, times(1)).updateStudentCourse(studentCourse);
    }

}