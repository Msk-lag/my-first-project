package student.management.Student.Management.Repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import student.management.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * コース情報を扱うRepository
 */


@Mapper
public interface StudentCourseRepository {

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return　受講生のコース情報（全件）
     */

    @Select("SELECT * FROM students_course")
    List<StudentCourse> searchStudentCourseList();


    /**
     * 受講生コース情報を新規登録します。　IDに関してはUUIDを自動で付与する.
     *
     * @param studentCourse 　受講生コース情報
     */
    @Insert("""
                INSERT INTO students_course (course_id, student_id, course_name, start_of_course, end_of_course)
                VALUES (#{courseId}, #{studentId}, #{courseName}, #{startOfCourse}, #{endOfCourse})
            """)
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 　受講生コース情報
     */
    @Update("""
            UPDATE  students_course
            SET course_name = #{courseName}
            
            WHERE student_id = #{studentId} AND course_id = #{courseId}
            """)
    void updateStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生IDに紐づく受講生コース情報を検索します
     *
     * @param studentId 　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */

    @Select("SELECT * FROM students_course WHERE student_id = #{studentId}")
    List<StudentCourse> searchStudentCourse(String studentId);


}
