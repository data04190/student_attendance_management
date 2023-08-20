-- 학생 테이블 
CREATE TABLE myapp_member(
  member_no INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  level INT NOT NULL DEFAULT 1,
  PRIMARY KEY (member_no)
);

-- 학생 출결 관리 테이블 
CREATE TABLE myapp_student_attendance(
  attendance_no INT NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  student_no INT NOT NULL,
  entry_time TIME NOT NULL,
  exit_time TIME NOT NULL,
  study_time TIME NOT NULL,
  late_status VARCHAR(20) NOT NULL,
  PRIMARY KEY (attendance_no),
  FOREIGN KEY (student_no) REFERENCES myapp_member(member_no)
);

-- 공지사항 게시판 
CREATE TABLE myapp_board(
  board_no INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  content TEXT NULL,
  writer INT NOT NULL,
  password VARCHAR(100) NULL,
  view_count INT DEFAULT 0,
  created_date DATETIME DEFAULT NOW(),
  PRIMARY KEY (board_no),
  FOREIGN KEY (writer) REFERENCES myapp_member(member_no)
);