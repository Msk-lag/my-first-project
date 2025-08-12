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
     * 受講生のコース情報（全件）
     */
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します
     *
     * @param studentId 　受講生ID
     * 受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * 受講生コース情報を新規登録します。　IDに関してはUUIDを自動で付与する.
     *
     * @param studentCourse 　受講生コース情報
     */
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 　受講生コース情報
     */
    void updateStudentCourse(StudentCourse studentCourse);


}
