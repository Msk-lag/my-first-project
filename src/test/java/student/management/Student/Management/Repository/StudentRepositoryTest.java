package student.management.Student.Management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.Student.Management.data.Student;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行える(){
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 受講生の単一検索が行える(){
        Student expected = new Student(
                "0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b",
                "青木 康介",
                "せいき こすけ",
                "ケンクン",
                "aoki.kenta@example.com",
                "神奈川県横浜市青葉区7-7-7",
                19,
                "男性",
                null,
                false
        );
        Student actual = sut.searchStudent("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 受講生の登録が行えること(){
        Student student = new Student(
                UUID.randomUUID().toString(),
                "菊池 正樹",
                "キクチ マサキ",
                "マサキ",
                "test@example.com",
                "岩手県",
                26,
                "男性",
                "",
              false
        );
        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(8);
    }

    @Test
    void 受講生更新が行えること(){
        Student student =  sut.searchStudent("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        Student updatedStudent = new Student(
                student.getId(),
                "菊池 正樹",
                "キクチ マサキ",
                "マサキ",
                "test@example.com",
                "岩手県",
                26,
                "男性",
                "",
                false
        );
        sut.updateStudent(updatedStudent);

        Student actual = sut.searchStudent(student.getId());

        assertThat(actual).isEqualTo(updatedStudent);
    }

    @Test
    void 受講生の単一検索時無効なIDが渡されたとき何も返さない() {
        Student actual = sut.searchStudent("dummy");
        assertThat(actual).isNull();
    }

}