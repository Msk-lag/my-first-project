package student.management.Student.Management.Repository;


import org.apache.ibatis.annotations.*;
import student.management.Student.Management.data.Student;
import java.util.List;

/**
 * 受講生情報を扱うリポジトリ
 * 全件検索や単一条件での検索が行える
 */

@Mapper
public interface StudentRepository {
    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT COALESCE(MAX(id), 0) FROM students")
    int getMaxId();


    @Insert("""
            INSERT INTO students (id,fullname, furigana, nick_name, email, address, age, gender, remark)
            VALUES ( #{id},#{fullName}, #{furigana}, #{nickName}, #{email}, #{address}, #{age}, #{gender}, #{remark})
            """)
    void insert(Student student);
}
