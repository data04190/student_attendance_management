package bitcamp.myapp.handler;

import java.io.PrintWriter;
import java.time.LocalTime;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/update")
public class AttendanceUpdateServlet implements Servlet {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceUpdateServlet(AttendanceDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.attendanceDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }


  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/attendance/list'>\n");
    out.println("<title>출결 체크</title>");
    out.println("</head>");
    out.println("<body>");

    if (loginUser.getLevel() == 2) {
      Attendance a = new Attendance();
      a.setNo(Integer.parseInt(request.getParameter("no")));
      a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      a.setLateStatus();

      out.println("<h1>게시글 변경</h1>");
      try {
        if (attendanceDao.update(a) == 0) {
          out.println("<p>게시글이 없거나 변경 권한이 없습니다.</p>");
        } else {
          out.println("<p>변경했습니다!</p>");
        }
        sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
        out.println("<p>게시글 변경 실패입니다!</p>");
        e.printStackTrace();
      }

    } else {
      out.println("<p>해당 레벨의 회원은 게시글을 작성할 수 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }



}
