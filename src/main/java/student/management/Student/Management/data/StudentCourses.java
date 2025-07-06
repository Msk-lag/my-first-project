package student.management.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class StudentCourses {

    private String courseId;
    private String studentId;
    private String courseName;
    private Date startOfCourse;
    private Date endOfCourse;

}
