package student.management.Student.Management.controller.converter;

import org.springframework.stereotype.Component;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverter {
    public List<StudentDetail> convertStudentDetails(List<Student> students,
                                                      List<StudentCourses> studentCourses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        students.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourses> convertStudentCourses = studentCourses.stream()
                    .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                    .collect(Collectors.toList());

            studentDetail.setStudentCourses(convertStudentCourses);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }
}
