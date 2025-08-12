package student.management.Student.Management.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domain.StudentDetail;
import student.management.Student.Management.service.StudentService;


import java.util.List;
import java.util.UUID;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */

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

    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }


    @GetMapping("/studentCourseList")
    public List<StudentCourse> getStudentCourseList() {
        return service.searchStudentCourseList();
    }


    /**
     *受講生詳細の登録を行います。
     *
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.saveStudentDetail(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }


    /**
     * 受講生詳細検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生情報
     */
    @GetMapping("/student/{id}")
    public StudentDetail showUpdateStudent(@PathVariable UUID id) {
        return service.getStudentDetailById(id.toString());


    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います（論理削除）
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */

    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudentDetail(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }


}
