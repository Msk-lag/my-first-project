package student.management.Student.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

    private String courseId;
    private String studentId;
    private String courseName;
    private Date startOfCourse;
    private Date endOfCourse;

}
