package student.management.Student.Management.Repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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


    @Insert("""
                INSERT INTO students_course (course_id, student_id, course_name, start_of_course, end_of_course)
                VALUES (#{courseId}, #{studentId}, #{courseName}, #{startOfCourse}, #{endOfCourse})
            """)
    void insert(StudentCourses course);


    @Update("""
            UPDATE  students_course
            SET course_name = #{courseName}
            
            WHERE student_id = #{studentId} AND course_id = #{courseId}
            """)
    void update(StudentCourses course);


    @Select("SELECT * FROM students_course WHERE student_id = #{studentId}")
    List<StudentCourses> findByStudentId(String studentId);




}
