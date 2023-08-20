-- student_member 테이블 예제 데이터 
  INSERT INTO student_member(member_no, name, email, password, level) VALUES
(1, '관리자', 'admin@testcom', sha1('1111'), 2),
(2, '강XX', 'kang@test.com', sha1('1111'), 1),
(3, '김XX', 'kim@test.com', sha1('1111'), 1),
(4, '이XX', 'lee@test.com', sha1('1111'), 1),
(5, '박XX', 'park@test.com', sha1('1111'), 1),
(6, '양XX', 'yang@test.com', sha1('1111'), 1);

-- student_attendance 테이블 예제 데이터
INSERT INTO student_attendance (attendance_no, date, student_no, entry_time, exit_time, study_time, late_status) VALUES
  (1, '2023-07-04', 2, '09:22:00', '18:12:00', '19:25:00', 'X'),
  (2, '2023-07-04', 3, '09:30:00', '18:01:00', '21:00:00', 'X'),
  (6, '2023-07-06', 2, '09:33:00', '18:00:00', '21:45:00', 'O'),
  (7, '2023-07-09', 3, '09:55:00', '18:02:00', '18:55:00', 'O'),
  (8, '2023-07-10', 5, '09:12:00', '18:30:00', '19:27:00', 'X'),
  (9, '2023-07-11', 2, '09:11:00', '18:33:00', '19:50:00', 'X');

-- myapp_board 테이블 예제 데이터
  INSERT INTO student_board(board_no, title, content, writer) VALUES
(1, '7월 공지사항', '공지 내용', 1),
(2, '수업 공지', '공지 내용', 1),
(3, '긴급공지', '공지 내용', 1),
(5, '공지사항', '공지 내용', 1);