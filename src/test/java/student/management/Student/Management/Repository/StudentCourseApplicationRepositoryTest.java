package student.management.Student.Management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.Student.Management.data.StudentCourseApplication;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static student.management.Student.Management.data.ApplicationStatus.OFFICIAL;
import static student.management.Student.Management.data.ApplicationStatus.PROVISIONAL;

@MybatisTest
class StudentCourseApplicationRepositoryTest {

    @Autowired
    private StudentCourseApplicationRepository sut;

    @Test
    void コース申し込み情報の全件検索が行える() {
        List<StudentCourseApplication> actual = sut.searchStudentCourseApplicationList();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 受講生IDに紐づく申し込み状況を取得できる() {
        List<StudentCourseApplication> actual = sut.searchStudentsCourseApplication
                (List.of("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b", "550e8400-e29b-41d4-a716-446655440000"));

        assertThat(actual.size()).isEqualTo(2);

        List<StudentCourseApplication> expected = List.of(
                new StudentCourseApplication(
                        "a1111111-1111-1111-1111-111111111111",
                        "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                        "83bb2827-c655-49f8-b095-04efbf055ca2",
                        PROVISIONAL
                ),
                new StudentCourseApplication(
                        "b2222222-2222-2222-2222-222222222222",
                        "550e8400-e29b-41d4-a716-446655440000",
                        "9f8c9c41-2a1b-4d62-9d25-111111111111",
                        OFFICIAL
                )
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 受講生IDに紐づくコース申し込み状況リストを一括で取得する際に無効なIDが渡されたとき空のリストを返す() {
        List<StudentCourseApplication> actual = sut.searchStudentsCourseApplication(List.of("dummy"));
        assertThat(actual).isEmpty();
    }

    @Test
    void コース申し込み情報の登録が行えること() {
        StudentCourseApplication studentCourseApplication = new StudentCourseApplication(
                UUID.randomUUID().toString(),
                "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                "83bb2827-c655-49f8-b095-04efbf055ca2",
                PROVISIONAL);

        sut.registerCourseApplication(studentCourseApplication);

        List<StudentCourseApplication> actual = sut.searchStudentCourseApplicationList();

        assertThat(actual.size()).isEqualTo(8);
    }

    @Test
    void コース申し込み情報のステータスが変更できること() {
        List<StudentCourseApplication> studentCourseApplication = sut.searchStudentsCourseApplication
                (List.of("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b"));

        StudentCourseApplication expected = studentCourseApplication.get(0);
        expected.setCourseStatus(OFFICIAL);

        sut.courseStatusUpdate(expected);

        List<StudentCourseApplication> updateStudentCourseApplication = sut.searchStudentsCourseApplication
                (List.of("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b"));
        StudentCourseApplication actual = updateStudentCourseApplication.get(0);

        assertThat(actual.getCourseStatus()).isEqualTo(OFFICIAL);
    }
}