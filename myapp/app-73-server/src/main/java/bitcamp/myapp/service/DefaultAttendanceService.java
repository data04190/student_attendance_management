package bitcamp.myapp.service;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
public class DefaultAttendanceService implements AttendanceService {

  AttendanceDao attendanceDao;

  public DefaultAttendanceService(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  @Transactional
  @Override
  public int add(Attendance attendance) throws Exception {
      return attendanceDao.insert(attendance);
  }

  @Override
  public List<Attendance> list() throws Exception {
    return attendanceDao.findAll();
  }

  @Override
  public Attendance get(int attendanceNo) throws Exception {
    return attendanceDao.findBy(attendanceNo);
  }

  @Transactional
  @Override
  public int update(Attendance attendance) throws Exception {
      return attendanceDao.update(attendance);
  }

  @Transactional
  @Override
  public int delete(int attendanceNo) throws Exception {
      return attendanceDao.delete(attendanceNo);
  }
}
