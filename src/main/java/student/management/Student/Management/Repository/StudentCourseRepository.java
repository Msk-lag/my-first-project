package student.management.Student.Management.Repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import student.management.Student.Management.data.StudentCourses;
import java.util.List;

/**
 * コース情報を扱うリポジトリ
 * 全件検索や単一条件での検索が行える
 */
@Mapper
public interface StudentCourseRepository {
    @Select("SELECT * FROM students_course")
    List<StudentCourses> courseSearch();
}

