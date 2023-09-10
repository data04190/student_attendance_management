package bitcamp.myapp.service;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class DefaultAttendanceService implements AttendanceService {

  AttendanceDao attendanceDao;
  PlatformTransactionManager txManager;

  public DefaultAttendanceService(AttendanceDao attendanceDao, PlatformTransactionManager txManager) {
    this.attendanceDao = attendanceDao;
    this.txManager = txManager;
  }

  @Override
  public int add(Attendance attendance) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = attendanceDao.insert(attendance);
      txManager.commit(status);
      return count;

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }

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
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = attendanceDao.update(attendance);
      txManager.commit(status);
      return count;

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int delete(int attendanceNo) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = attendanceDao.delete(attendanceNo);
      txManager.commit(status);
      return count;

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}
