package student.management.Student.Management.data;


import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

    private String id;


    private String fullName;


    private String furigana;


    private String nickName;

    @Email
    private String email;


    private String address;


    private int age;


    private String gender;


    private String remark;


    private boolean isDeleted;

}
