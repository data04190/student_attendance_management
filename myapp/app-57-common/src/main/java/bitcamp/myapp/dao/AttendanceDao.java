package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Attendance;

public interface AttendanceDao {
  void insert(Attendance member);

  List<Attendance> findAll();

  Attendance findBy(int no);

  int update(Attendance member);

  int delete(int no);

}
