package student.management.Student.Management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domain.StudentDetail;
import student.management.Student.Management.exceptionHandler.TestException;
import student.management.Student.Management.service.StudentService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class StudentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生情報の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(get("/studentList"))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 受講生コース情報の検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(get("/studentCourseList"))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudentCourseList();
    }

    @Test
    void 受講生詳細検索時にサービス呼び出しが行われること() throws Exception {
        UUID id = UUID.randomUUID();
        StudentDetail studentDetail = new StudentDetail();
        when(service.searchStudent(id.toString())).thenReturn(studentDetail);

        mockMvc.perform(get("/student/{id}", id.toString()))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudent(id.toString());
    }

    @Test
    void 受講生詳細検索時にUUID以外の入力されたときにサービスの呼び出しが行われないこと() throws  Exception{
        mockMvc.perform(get("/student/{id}","dummy"))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).searchStudent(anyString());
    }

    @Test
    void 受講生登録時にサービス呼び出しが行われること() throws Exception {
        StudentDetail studentDetail = new StudentDetail();

        mockMvc.perform(post("/registerStudent")
                        .content(objectMapper.writeValueAsString(studentDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).registerStudent(any(StudentDetail.class));
    }

    @Test
    void 受講生更新時にサービス呼び出しが発生すること() throws Exception {
        StudentDetail studentDetail = new StudentDetail();

        mockMvc.perform(put("/updateStudent")
                        .content(objectMapper.writeValueAsString(studentDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).updateStudent(any(StudentDetail.class));
    }

    @Test
    void 例外処理が呼び出されメッセージが想定どおりであること() throws Exception {
        mockMvc.perform(get("/testStudentList"))
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertThat(result.getResolvedException()).isInstanceOf(TestException.class)
                )
                .andExpect(result ->
                        assertThat(result.getResolvedException().getMessage())
                                .contains("現在このAPIは使用できません。URLは「testStudentList」ではなく「studentList」を利用してください")
                );
    }

    @Test
    void 受講生詳細の受講生でIDがUUIDでない入力になったときに入力チェックがかかること() {
        Student student = new Student();
        student.setId("dummy");
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setGender("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("UUIDを使用してください");
    }

    @Test
    void 受講生詳細の受講生で適切な値を入力したときに異常が発生しないこと() {
        Student student = new Student();
        student.setId("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        student.setFullName("菊池 正樹");
        student.setFurigana("キクチ マサキ");
        student.setNickName("マサキ");
        student.setEmail("test@example.com");
        student.setAddress("岩手県");
        student.setGender("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(0);
    }

}