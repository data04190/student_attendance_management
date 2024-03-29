package bitcamp.myapp.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.vo.Member;
import org.springframework.stereotype.Component;

@Component
public class MySQLMemberDao implements MemberDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLMemberDao(SqlSessionFactory sqlSessionFactory) {
    System.out.println("MySQLMemberDao() 호출됨!");
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bitcamp.myapp.dao.MemberDao.insert", member);
  }

  @Override
  public List<Member> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("bitcamp.myapp.dao.MemberDao.findAll");
  }

  @Override
  public Member findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bitcamp.myapp.dao.MemberDao.findBy", no);
  }


  @Override
  public Member findByEmailAndPassword(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("bitcamp.myapp.dao.MemberDao.findByEmailAndPassword", member);
  }

  @Override
  public int update(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bitcamp.myapp.dao.MemberDao.update", member);
  }

  @Override
  public int delete(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bitcamp.myapp.dao.MemberDao.delete", no);
  }

}
