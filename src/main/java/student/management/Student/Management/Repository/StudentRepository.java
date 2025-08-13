package student.management.Student.Management.Repository;


import org.apache.ibatis.annotations.*;
import student.management.Student.Management.data.Student;
import java.util.List;

/**
 * 受講生情報を扱うRepository
 */

@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います
     * @return 受講生一覧（全件）
     */
    List<Student> search();

    /**
     * 受講生の検索を行います
     * @param id 受講生ID
     * @return 受講生
     */

    Student searchStudent(String id);

    /**
     * 受講生情報を新規登録します。　IDに関してはUUIDを自動で付与する。
     * @param student　受講生
     */


    void registerStudent(Student student);

    /**
     * 受講生を更新します。
     * @param student　受講生
     */

    void updateStudent(Student student);




}
