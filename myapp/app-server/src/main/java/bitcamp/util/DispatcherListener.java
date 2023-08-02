package bitcamp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.dao.MySQLAttendanceDao;
import bitcamp.dao.MySQLBoardDao;
import bitcamp.dao.MySQLMemberDao;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.handler.AttendanceAddListener;
import bitcamp.myapp.handler.AttendanceDeleteListener;
import bitcamp.myapp.handler.AttendanceDetailListener;
import bitcamp.myapp.handler.AttendanceListListener;
import bitcamp.myapp.handler.AttendanceUpdateListener;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.LoginListener;
import bitcamp.myapp.handler.MemberAddListener;
import bitcamp.myapp.handler.MemberDeleteListener;
import bitcamp.myapp.handler.MemberDetailListener;
import bitcamp.myapp.handler.MemberListListener;
import bitcamp.myapp.handler.MemberUpdateListener;

public class DispatcherListener implements ActionListener {

  // 객체 보관소
  Map<String, Object> beanContainer = new HashMap<>();

  public DispatcherListener() throws Exception {

    // Mybatis 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml")));
    beanContainer.put("sqlSessionFactory", sqlSessionFactory);

    // DAO 준비
    MemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
    AttendanceDao attendanceDao = new MySQLAttendanceDao(sqlSessionFactory);
    BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
    beanContainer.put("memberDao", memberDao);
    beanContainer.put("attendanceDao", attendanceDao);
    beanContainer.put("boardDao", boardDao);

    // Listener 준비
    beanContainer.put("login", new LoginListener(memberDao));

    beanContainer.put("member/add", new MemberAddListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/list", new MemberListListener(memberDao));
    beanContainer.put("member/detail", new MemberDetailListener(memberDao));
    beanContainer.put("member/update", new MemberUpdateListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/delete", new MemberDeleteListener(memberDao, sqlSessionFactory));

    beanContainer.put("attendance/add",
        new AttendanceAddListener(attendanceDao, sqlSessionFactory));
    beanContainer.put("attendance/list", new AttendanceListListener(attendanceDao));
    beanContainer.put("attendance/detail", new AttendanceDetailListener(attendanceDao));
    beanContainer.put("attendance/update",
        new AttendanceUpdateListener(attendanceDao, sqlSessionFactory));
    beanContainer.put("attendance/delete",
        new AttendanceDeleteListener(attendanceDao, sqlSessionFactory));

    beanContainer.put("board/add", new BoardAddListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/list", new BoardListListener(boardDao));
    beanContainer.put("board/detail", new BoardDetailListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/update", new BoardUpdateListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/delete", new BoardDeleteListener(boardDao, sqlSessionFactory));
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    ActionListener listener = (ActionListener) beanContainer.get(prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }
}
