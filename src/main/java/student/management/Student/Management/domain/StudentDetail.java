package student.management.Student.Management.domain;

import lombok.Getter;
import lombok.Setter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;

import java.util.List;

@Getter
@Setter
public class StudentDetail {

    private Student student;
    private List<StudentCourses> studentCourses;

}
