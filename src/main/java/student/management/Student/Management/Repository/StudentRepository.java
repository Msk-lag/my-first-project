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

    @Select("SELECT * FROM students")
    List<Student> searchStudent();

    /**
     * 受講生の検索を行います
     * @param id 受講生ID
     * @return 受講生
     */

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student getStudentId(String id);

    /**
     * 受講生情報を新規登録します。　IDに関してはUUIDを自動で付与する。
     * @param student　受講生
     */


    @Insert("""
            INSERT INTO students (id,fullName, furigana, nick_name, email, address, age, gender, remark,is_deleted)
            VALUES ( #{id},#{fullName}, #{furigana}, #{nickName}, #{email}, #{address}, #{age}, #{gender}, #{remark},false)
            """)
    void registerStudent(Student student);

    /**
     * 受講生を更新します。
     * @param student　受講生
     */
    @Update("""
            UPDATE  students
            SET fullName = #{fullName},
                furigana = #{furigana},
                nick_name = #{nickName},
                email = #{email},
                address = #{address},
                age = #{age},
                gender = #{gender},
                remark = #{remark},
                is_deleted = #{isDeleted}
            WHERE id = #{id}
            """)
    void updateStudent(Student student);




}
