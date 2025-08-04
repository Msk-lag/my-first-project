package student.management.Student.Management.Repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import student.management.Student.Management.data.StudentCourses;
import java.util.List;

/**
 * コース情報を扱うRepository
 */


@Mapper
public interface StudentCourseRepository {

    /**
     * 受講生のコース情報の全件検索を行います。
     * @return　受講生のコース情報（全件）
     */

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

    /**
     * 受講生IDに紐づく受講生コース情報を検索します
     * @param studentId　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */

    @Select("SELECT * FROM students_course WHERE student_id = #{studentId}")
    List<StudentCourses> findByStudentId(String studentId);




}
