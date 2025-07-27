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
    @Select("SELECT * FROM students WHERE is_deleted = false")
    List<Student> search();

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(String id);


    @Insert("""
            INSERT INTO students (id,fullname, furigana, nick_name, email, address, age, gender, remark,is_deleted)
            VALUES ( #{id},#{fullName}, #{furigana}, #{nickName}, #{email}, #{address}, #{age}, #{gender}, #{remark},false)
            """)
    void insert(Student student);

    @Update("""
            UPDATE  students
            SET fullname = #{fullName},
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
    void update(Student student);




}
