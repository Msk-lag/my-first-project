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

}
