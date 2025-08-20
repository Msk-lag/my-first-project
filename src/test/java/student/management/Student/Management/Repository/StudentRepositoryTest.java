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
        Student actual =  sut.searchStudent("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        assertThat(actual.getFullName()).isEqualTo("青木 康介");
        assertThat(actual.getFurigana()).isEqualTo("せいき こすけ");
        assertThat(actual.getNickName()).isEqualTo("ケンクン");
        assertThat(actual.getEmail()).isEqualTo("aoki.kenta@example.com");
        assertThat(actual.getAddress()).isEqualTo("神奈川県横浜市青葉区7-7-7");
        assertThat(actual.getAge()).isEqualTo(19);
        assertThat(actual.getGender()).isEqualTo("男性");
    }

    @Test
    void 受講生の登録が行えること(){
        Student student = new Student();

        student.setId(UUID.randomUUID().toString());
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setAge(26);
        student.setGender("男性");
        student.setRemark("");
        student.setDeleted(false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(8);

    }

    @Test
    void 受講生更新が行えること(){
        Student student =  sut.searchStudent("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setAge(26);
        student.setGender("男性");
        student.setRemark("");

        sut.updateStudent(student);

        Student actual =  sut.searchStudent("0b0b0109-5f2a-4454-bc1b-5d1ccadcf80b");
        assertThat(actual.getFullName()).isEqualTo("菊池 正樹");
        assertThat(actual.getFurigana()).isEqualTo("キクチ マサキ");
        assertThat(actual.getNickName()).isEqualTo("マサキ");
        assertThat(actual.getEmail()).isEqualTo("test@example.com");
        assertThat(actual.getAddress()).isEqualTo("岩手県");
        assertThat(actual.getAge()).isEqualTo(26);
        assertThat(actual.getGender()).isEqualTo("男性");
        assertThat(actual.getRemark()).isEqualTo("");
    }

    @Test
    void 受講生の単一検索時無効なIDが渡されたとき何も返さないが行える() {
        Student actual = sut.searchStudent("dummy");
        assertThat(actual).isNull();
    }

}