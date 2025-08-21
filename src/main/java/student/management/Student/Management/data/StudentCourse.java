package student.management.Student.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@EqualsAndHashCode
public class StudentCourse {

    private String courseId;
    private String studentId;
    private String courseName;
    private Date startOfCourse;
    private Date endOfCourse;

    //デフォルトコンストラクタ
    public StudentCourse(){}

    //テスト用コンストラクタ
    public StudentCourse(
            String courseId, String studentId, String courseName, Date startOfCourse, Date endOfCourse) {

        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        this.startOfCourse = startOfCourse;
        this.endOfCourse = endOfCourse;
    }

}
