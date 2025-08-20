CREATE TABLE IF NOT EXISTS students
(
      id    VARCHAR(50) PRIMARY KEY,
      fullName   VARCHAR(100),
      furigana   VARCHAR(100),
      nick_name  VARCHAR(100),
      email      VARCHAR(100),
      address    VARCHAR(100) ,
      age        INT ,
      gender     VARCHAR(10),
      remark     VARCHAR(300),
      is_deleted boolean
      );


CREATE TABLE IF NOT EXISTS students_course
(
    course_id       VARCHAR(50)  PRIMARY KEY ,
    student_id      VARCHAR(50),
    course_name     VARCHAR(100),
    start_of_course DATE,
    end_of_course   DATE
);