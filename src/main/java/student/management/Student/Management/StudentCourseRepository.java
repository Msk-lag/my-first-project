package student.management.Student.Management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;
@Mapper
public interface StudentCourseRepository {
    @Select("SELECT * FROM students_course")
    List<StudentCourse> courseSearch();
}

