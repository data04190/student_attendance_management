package bitcamp.myapp.handler;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/delete")
public class AttendanceDeleteServlet implements Servlet {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceDeleteServlet(AttendanceDao memberDao, SqlSessionFactory sqlSessionFactory) {
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
    out.printf("<meta http-equiv='refresh' content='1;url=/board/list'>\n");
    out.println("<title>출결 체크</title>");
    out.println("</head>");
    out.println("<body>");

    if (loginUser.getLevel() == 2) {

      try {
        if (attendanceDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
          throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
        } else {
          response.sendRedirect("/attendance/list");
        }
        sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
    } else {
      out.println("<p>해당 레벨의 회원은 게시글을 작성할 수 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }



}
