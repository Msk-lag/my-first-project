package student.management.Student.Management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.data.StudentCourseApplication;

import java.util.List;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {


    @Valid
    private Student student;

    @Valid
    private List<StudentCourse> studentCourseList;

    @Valid
    private List<StudentCourseApplication> studentCourseApplicationsList;

}
