package student.management.Student.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース申し込み情報")
@Getter
@Setter
@EqualsAndHashCode
public class StudentCourseApplication {

    @NotBlank
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "UUIDを使用してください")
    private String applicationId;


    @NotBlank
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "UUIDを使用してください")
    private String studentId;


    @NotBlank
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "UUIDを使用してください")
    private String courseId;
    

    @NotBlank
    private ApplicationStatus courseStatus;


    //デフォルトコンストラクタ
    public StudentCourseApplication() {
    }

    //テスト用コンストラクタ
    public StudentCourseApplication(
            String applicationId,
            String studentId,
            String courseId,
            ApplicationStatus courseStatus) {

        this.applicationId = applicationId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseStatus = courseStatus;
    }
}
