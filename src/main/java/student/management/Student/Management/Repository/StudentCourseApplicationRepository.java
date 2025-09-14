package student.management.Student.Management.Repository;


import org.apache.ibatis.annotations.Mapper;
import student.management.Student.Management.data.StudentCourseApplication;

import java.util.List;

/**
 * コース申し込み状況を管理するリポジトリ
 */

@Mapper
public interface StudentCourseApplicationRepository {

    /**
     * 申し込み状況を全件取得
     * テスト用
     */
    List<StudentCourseApplication> searchStudentCourseApplicationList();

    /**
     * 受講生IDに紐づく申し込み状況を一括取得
     *
     * @param studentId 受講生ID
     */
    List<StudentCourseApplication> searchStudentsCourseApplication(List<String> studentId);

    /**
     * コース申し込み情報のステータスを新規登録します。IDに関してはUUIDを自動で付与する.
     *
     * @param studentCourseApplication 　コース申し込み情報
     */
    void registerCourseApplication(StudentCourseApplication studentCourseApplication);

    /**
     * コース申し込み情報のステータスを更新します。
     *
     * @param studentCourseApplication 　コース申し込み情報
     */
    void courseStatusUpdate(StudentCourseApplication studentCourseApplication);
}
