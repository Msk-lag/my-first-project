package student.management.Student.Management.domain;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {


    @Valid
    private Student student;

    @Valid
    private List<StudentCourse> studentCourseList;

}
