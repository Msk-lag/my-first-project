package management.Repository;


import org.apache.ibatis.annotations.*;
import management.data.Student;

import java.util.List;

/**
 * 受講生情報を扱うRepository
 */

@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います
     *
     * @return 受講生一覧（全件）
     */
    List<Student> search();

    /**
     * 受講生の検索を行います
     *
     * @param id 受講生ID
     * @return 受講生
     */
    Student searchStudent(String id);

    /**
     * 受講生情報を新規登録します。　IDに関してはUUIDを自動で付与する。
     *
     * @param student 　受講生
     */
    void registerStudent(Student student);

    /**
     * 受講生を更新します。
     *
     * @param student 　受講生
     */
    void updateStudent(Student student);

    /**
     * 受講生の条件検索を行います
     *
     * @param fullName 名前
     * @param age      年齢
     * @param gender   性別
     * @return 受講生リスト
     */
    List<Student> searchStudents(String fullName, Integer age, String gender);

}
