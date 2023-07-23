create table myapp_member(
  member_no INT NOT NULL,
  date DATE NOT NULL,
  student_name VARCHAR(50) NOT NULL,
  entry_time TIME NOT NULL,
  exit_time TIME NOT NULL,
  study_time TIME NOT NULL,
  late_status VARCHAR(20) NOT NULL
);

alter table myapp_member
  add constraint primary key (member_no),
  modify column member_no int not null auto_increment;