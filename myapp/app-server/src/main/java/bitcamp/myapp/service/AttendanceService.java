package bitcamp.myapp.service;

import bitcamp.myapp.vo.Attendance;

import java.util.List;

public interface AttendanceService {
  int add(Attendance attendance) throws Exception;
  List<Attendance> list() throws Exception;
  Attendance get(int attendanceNo) throws Exception;
  int update(Attendance attendance) throws Exception;
  int delete(int attendanceNo) throws Exception;
}
