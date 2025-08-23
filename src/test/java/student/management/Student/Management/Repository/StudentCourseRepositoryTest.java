package student.management.Student.Management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

import java.sql.Date;
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
    void 受講生コース情報の全件検索が行える() {
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 受講生IDに紐づく受講生コース情報検索が行える() {
        List<StudentCourse> studentCourse = sut.searchStudentCourse("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");

        assertThat(studentCourse.size()).isEqualTo(1);
        StudentCourse actual = studentCourse.get(0);

        StudentCourse expected = new StudentCourse(
                "83bb2827-c655-49f8-b095-04efbf055ca2",
                "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                "WordPress副業コース",
                java.sql.Date.valueOf("2025-08-12"),
                java.sql.Date.valueOf("2026-08-12")
        );
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 受講生コース情報の登録が行えること() {
        StudentCourse studentCourse = new StudentCourse(
                UUID.randomUUID().toString(),
                "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                "デザインコース",
                java.sql.Date.valueOf(LocalDate.now()),
                java.sql.Date.valueOf(LocalDate.now().plusYears(1)));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(8);
    }

    @Test
    void 受講生コース情報のコース名が変更できること() {
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

    @Test
    void 受講生IDに紐づく受講生コース情報リストで取得する() {
        List<StudentCourse> actual = sut.getCoursesByStudents
                (List.of("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b", "550e8400-e29b-41d4-a716-446655440000"));
        assertThat(actual.size()).isEqualTo(2);

        List<StudentCourse> expected = List.of(
                new StudentCourse(
                        "83bb2827-c655-49f8-b095-04efbf055ca2",
                        "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                        "WordPress副業コース",
                        java.sql.Date.valueOf("2025-08-12"),
                        java.sql.Date.valueOf("2026-08-12")
                ),
                new StudentCourse(
                        "9f8c9c41-2a1b-4d62-9d25-111111111111",
                        "550e8400-e29b-41d4-a716-446655440000",
                        "Javaコース",
                        java.sql.Date.valueOf("2025-07-01"),
                        java.sql.Date.valueOf("2025-09-30")
                )
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void 受講生IDに紐づく受講生コース情報リストで一括で取得する際に無効なIDが渡されたとき空のリストを返す() {
        List<StudentCourse> actual = sut.getCoursesByStudents(List.of("dummy"));
        assertThat(actual).isEmpty();
    }
}
