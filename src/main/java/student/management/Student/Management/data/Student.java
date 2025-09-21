package student.management.Student.Management.data;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Schema(description = "受講生")
@Getter
@Setter
@EqualsAndHashCode
public class Student {

    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "UUIDを使用してください")
    private String id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String furigana;

    @NotBlank
    private String nickName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;


    private int age;

    @NotBlank
    private String gender;


    private String remark;


    private boolean isDeleted;

    //デフォルトコンストラクタ
    public Student() {
    }

    //テスト用コンストラクタ
    public Student(
            String id,
            String fullName,
            String furigana,
            String nickName,
            String email,
            String address,
            int age,
            String gender,
            String remark,
            boolean isDeleted
    ) {

        this.id = id;
        this.fullName = fullName;
        this.furigana = furigana;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.remark = remark;
        this.isDeleted = isDeleted;
    }

}
