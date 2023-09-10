package bitcamp.myapp.service;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.TransactionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.List;

@Service
public class DefaultAttendanceService implements AttendanceService {

  AttendanceDao attendanceDao;
  TransactionTemplate txTemplate;

  public DefaultAttendanceService(AttendanceDao attendanceDao, PlatformTransactionManager txManager) {
    this.attendanceDao = attendanceDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(Attendance attendance) throws Exception {
    return txTemplate.execute(status -> {
      int count = attendanceDao.insert(attendance);
      return count;
    });
  }

  @Override
  public List<Attendance> list() throws Exception {
    return attendanceDao.findAll();
  }

  @Override
  public Attendance get(int attendanceNo) throws Exception {
    return attendanceDao.findBy(attendanceNo);
  }

  @Override
  public int update(Attendance attendance) throws Exception {
    return txTemplate.execute(status -> {
      int count = attendanceDao.update(attendance);
      return attendanceDao.update(attendance);
    });
  }

  @Override
  public int delete(int attendanceNo) throws Exception {
    return txTemplate.execute(status -> {
      return attendanceDao.delete(attendanceNo);
    });
  }
}
