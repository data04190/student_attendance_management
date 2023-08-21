-- 회원
DROP TABLE IF EXISTS student_member RESTRICT;

-- 공지사항
DROP TABLE IF EXISTS student_board RESTRICT;

-- 출결관리
DROP TABLE IF EXISTS student_attendance RESTRICT;

-- 게시글첨부파일
DROP TABLE IF EXISTS student_board_file RESTRICT;

-- 회원
CREATE TABLE student_member (
  member_no INTEGER      NOT NULL COMMENT '번호', -- 번호
  name      VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
  email     VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
  password  VARCHAR(100) NOT NULL COMMENT '비밀번호', -- 비밀번호
  level     INTEGER      NOT NULL DEFAULT 1 COMMENT '권한', -- 권한
  photo     VARCHAR(255) NULL     COMMENT '사진' -- 사진
)
COMMENT '회원';

-- 회원
ALTER TABLE student_member
  ADD CONSTRAINT PK_student_member -- 회원 기본키
  PRIMARY KEY (
  member_no -- 번호
  );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_student_member
  ON student_member ( -- 회원
    email ASC -- 이메일
  );

ALTER TABLE student_member
  MODIFY COLUMN member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 공지사항
CREATE TABLE student_board (
  board_no     INTEGER      NOT NULL COMMENT '새 컬럼', -- 새 컬럼
  title        VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content      MEDIUMTEXT   NOT NULL COMMENT '내용', -- 내용
  view_count   INTEGER      NOT NULL DEFAULT 0
   COMMENT '조회수', -- 조회수
  created_date DATETIME     NOT NULL DEFAULT now() COMMENT '등록일', -- 등록일
  writer       INTEGER      NOT NULL COMMENT '작성자' -- 작성자
)
COMMENT '공지사항';

-- 공지사항
ALTER TABLE student_board
  ADD CONSTRAINT PK_student_board -- 공지사항 기본키
  PRIMARY KEY (
  board_no -- 새 컬럼
  );

ALTER TABLE student_board
  MODIFY COLUMN board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '새 컬럼';

-- 출결관리
CREATE TABLE student_attendance (
  attendance_no INTEGER     NOT NULL COMMENT '새 컬럼', -- 새 컬럼
  date          DATE        NOT NULL COMMENT '날짜', -- 날짜
  student_no    INTEGER     NOT NULL COMMENT '학생번호', -- 학생번호
  entry_time    TIME        NOT NULL COMMENT '입실시간', -- 입실시간
  exit_time     TIME        NOT NULL COMMENT '퇴실시간', -- 퇴실시간
  study_time    TIME        NOT NULL COMMENT '스터디시간', -- 스터디시간
  late_status   VARCHAR(20) NOT NULL COMMENT '지각상태' -- 지각상태
)
COMMENT '출결관리';

-- 출결관리
ALTER TABLE student_attendance
  ADD CONSTRAINT PK_student_attendance -- 출결관리 기본키
  PRIMARY KEY (
  attendance_no -- 새 컬럼
  );

ALTER TABLE student_attendance
  MODIFY COLUMN attendance_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '새 컬럼';

-- 게시글첨부파일
CREATE TABLE student_board_file (
  board_file_no INTEGER      NOT NULL COMMENT '번호', -- 번호
  filepath      VARCHAR(255) NOT NULL COMMENT '파일경로', -- 파일경로
  board_no      INTEGER      NOT NULL COMMENT '게시글번호' -- 게시글번호
)
COMMENT '게시글첨부파일';

-- 게시글첨부파일
ALTER TABLE student_board_file
  ADD CONSTRAINT PK_student_board_file -- 게시글첨부파일 기본키
  PRIMARY KEY (
  board_file_no -- 번호
  );

ALTER TABLE student_board_file
  MODIFY COLUMN board_file_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 공지사항
ALTER TABLE student_board
  ADD CONSTRAINT FK_student_member_TO_student_board -- 회원 -> 공지사항
  FOREIGN KEY (
  writer -- 작성자
  )
  REFERENCES student_member ( -- 회원
  member_no -- 번호
  );

-- 출결관리
ALTER TABLE student_attendance
  ADD CONSTRAINT FK_student_member_TO_student_attendance -- 회원 -> 출결관리
  FOREIGN KEY (
  student_no -- 학생번호
  )
  REFERENCES student_member ( -- 회원
  member_no -- 번호
  );

-- 게시글첨부파일
ALTER TABLE student_board_file
  ADD CONSTRAINT FK_student_board_TO_student_board_file -- 공지사항 -> 게시글첨부파일
  FOREIGN KEY (
  board_no -- 게시글번호
  )
  REFERENCES student_board ( -- 공지사항
  board_no -- 새 컬럼
  );