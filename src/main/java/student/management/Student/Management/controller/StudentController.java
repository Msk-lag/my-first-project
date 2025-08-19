package student.management.Student.Management.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domain.StudentDetail;
import student.management.Student.Management.exceptionHandler.TestException;
import student.management.Student.Management.service.StudentService;


import java.util.List;
import java.util.UUID;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@Validated
@RestController

public class StudentController {

    private StudentService service;



    @Autowired
    public StudentController(StudentService service) {
        this.service = service;

    }

    /**
     * 受講生詳細の一覧検索
     * 全件検索を行うので、条件検索は行わないものになります。
     *
     * @return 受講生一覧(全件)
     */
    @Operation(summary = "一覧検索" ,description = "受講生一覧を検索します。")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList(){
        return service.searchStudentList();
    }

    /**
     * 受講生コースを検索します
     *
     * @return 受講生コース一覧
     */
    @Operation(summary = "受講生コース検索" ,description = "受講生コースを検索します。")
    @GetMapping("/studentCourseList")
    public List<StudentCourse> getStudentCourseList() {
        return service.searchStudentCourseList();
    }


    /**
     *受講生詳細の登録を行います。
     *
     * @param studentDetail　受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生登録", description = "受講生を登録します。")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(
            @RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }


    /**
     * 受講生詳細検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生情報
     */
    @Operation(summary = "受講生詳細検索" ,description = "IDに紐づく任意の受講生の情報を取得します。")
    @GetMapping("/student/{id}")
    public StudentDetail showUpdateStudent(@PathVariable  UUID id) {
        return service.searchStudent(id.toString());


    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います（論理削除）
     * @param studentDetail　受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生更新", description = "受講生詳細の更新を行います。")
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }

    /**
     * 例外処理を確認するためのメソッド
     */
    @Operation(summary = "例外処理", description = "例外処理を確認するためのメソッド")
    @GetMapping("/testStudentList")
    public List<StudentDetail> testStudentList() throws TestException {
        throw new TestException(
                "現在このAPIは使用できません。URLは「testStudentList」ではなく「studentList」を利用してください");
    }
}
