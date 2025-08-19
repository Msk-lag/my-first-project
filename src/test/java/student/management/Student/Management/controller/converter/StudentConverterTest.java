package student.management.Student.Management.controller.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.Student.Management.Repository.StudentCourseRepository;
import student.management.Student.Management.Repository.StudentRepository;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

    @Test
    void 組み立てた受講生詳細情報がStudentDetailに入ってることを確認(){
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();

        student.setId("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setGender("男性");

        List<Student> studentList = List.of(student);

        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseId("e7b1f8a2-4c3d-4f7a-9b2e-1d6f5a7c8b90");
        studentCourse.setCourseName("Javaコース");

        List<StudentCourse> studentCourseList = List.of(studentCourse);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> actual = converter.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);

    }

    @Test
    void 受講生IDが受講生コース情報の中にある受講生IDと一致しない際に受講生コース情報をわたしていないことを確認() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();

        student.setId("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setGender("男性");

        List<Student> studentList = List.of(student);

        studentCourse.setStudentId("123e4567-e89b-12d3-a456-426614174000");
        studentCourse.setCourseId("e7b1f8a2-4c3d-4f7a-9b2e-1d6f5a7c8b90");
        studentCourse.setCourseName("Javaコース");

        List<StudentCourse> studentCourseList = List.of(studentCourse);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> actual = converter.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEmpty();
    }

    @Test
    void 受講生詳細が0件の時に何も返さない確認() {
        StudentCourse studentCourse = new StudentCourse();
        List<Student> studentList = List.of();

        studentCourse.setStudentId("123e4567-e89b-12d3-a456-426614174000");
        studentCourse.setCourseId("e7b1f8a2-4c3d-4f7a-9b2e-1d6f5a7c8b90");
        studentCourse.setCourseName("Javaコース");

        List<StudentCourse> studentCourseList = List.of(studentCourse);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> actual = converter.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual).isEmpty();
    }
}