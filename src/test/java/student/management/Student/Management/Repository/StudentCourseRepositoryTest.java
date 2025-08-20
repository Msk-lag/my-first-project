package student.management.Student.Management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class StudentCourseRepositoryTest {

    @Autowired
    private StudentCourseRepository sut;

    @Test
    void 受講生コース情報の全件検索が行える(){
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 受講生IDに紐づく受講生コース情報検索が行える() {
        List<StudentCourse> studentCourse = sut.searchStudentCourse("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");

        assertThat(studentCourse.size()).isEqualTo(1);
        StudentCourse actual = studentCourse.get(0);

        assertThat(actual.getCourseId()).isEqualTo("83bb2827-c655-49f8-b095-04efbf055ca2");
        assertThat(actual.getCourseName()).isEqualTo("WordPress副業コース");
        assertThat(actual.getStartOfCourse()).isEqualTo("2025-08-12");
        assertThat(actual.getEndOfCourse()).isEqualTo("2026-08-12");
    }

    @Test
    void 受講生コース情報の登録が行えること(){
        StudentCourse studentCourse = new StudentCourse();

        studentCourse.setCourseId(UUID.randomUUID().toString());
        studentCourse.setStudentId("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        studentCourse.setCourseName("デザインコース");
        studentCourse.setStartOfCourse(java.sql.Date.valueOf(LocalDate.now()));
        studentCourse.setEndOfCourse(java.sql.Date.valueOf(LocalDate.now().plusYears(1)));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(8);

    }

    @Test
    void 受講生コース情報のコース名が変更できること(){
        List<StudentCourse> studentCourses = sut.searchStudentCourse("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        StudentCourse studentCourse = studentCourses.get(0);
        studentCourse.setCourseName("デザインコース");

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> updateStudentCourse = sut.searchStudentCourse("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        StudentCourse actual = updateStudentCourse.get(0);

        assertThat(actual.getCourseName()).isEqualTo("デザインコース");
    }

    @Test
    void 受講生IDに紐づく受講生コース情報検索の際に無効なIDが渡されたとき空のリストを返す() {
        List<StudentCourse> actual = sut.searchStudentCourse("dummy");
        assertThat(actual).isEmpty();
    }

}