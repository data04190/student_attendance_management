package bitcamp.myapp.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.vo.Attendance;

public class MySQLAttendanceDao implements AttendanceDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLAttendanceDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Attendance attendance) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bitcamp.myapp.dao.AttendanceDao.insert", attendance);
  }

  @Override
  public List<Attendance> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    return sqlSession.selectList("bitcamp.myapp.dao.AttendanceDao.findAll");
  }

  @Override
  public Attendance findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bitcamp.myapp.dao.AttendanceDao.findBy", no);
  }

  @Override
  public int update(Attendance attendance) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bitcamp.myapp.dao.AttendanceDao.update", attendance);
  }

  @Override
  public int delete(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bitcamp.myapp.dao.AttendanceDao.delete", no);
  }
}
