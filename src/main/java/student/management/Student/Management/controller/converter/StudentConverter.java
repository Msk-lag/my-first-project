package student.management.Student.Management.controller.converter;

import org.springframework.stereotype.Component;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.data.StudentCourseApplication;
import student.management.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の返還を行うコンバーターです。
 */

@Component
public class StudentConverter {

    /**
     * 受講生に紐づくコース情報をマッピングする。
     * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
     * 受講生コースに紐づく申し込み状況も一緒に組み立てる
     *
     * @param studentList                  受講生一覧
     * @param studentCourseList            受講生コース情報のリスト
     * @param studentCourseApplicationList コース申し込み状況
     * @return 受講生詳細情報のリスト
     */

    public List<StudentDetail> convertStudentDetails(List<Student> studentList,
                                                     List<StudentCourse> studentCourseList,
                                                     List<StudentCourseApplication> studentCourseApplicationList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        studentList.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
                    .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                    .collect(Collectors.toList());

            studentDetail.setStudentCourseList(convertStudentCourseList);
            studentDetails.add(studentDetail);

            List<StudentCourseApplication> convertApplicationList = studentCourseApplicationList.stream()
                    .filter(app -> student.getId().equals(app.getStudentId()))
                    .collect(Collectors.toList());
            studentDetail.setStudentCourseApplicationsList(convertApplicationList);
        });
        return studentDetails;
    }
}
