package bitcamp.myapp.handler;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/add")
public class AttendanceAddServlet implements Servlet {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceAddServlet(AttendanceDao attendanceDao, SqlSessionFactory sqlSessionFactory) {
    this.attendanceDao = attendanceDao;
    this.sqlSessionFactory = sqlSessionFactory;

  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    Attendance a = new Attendance();
    a.setDate(LocalDate.now());
    a.setStudentNo(loginUser);
    a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
    a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
    a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
    a.setLateStatus();

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
    out.println("<h1>출결 체크</h1>");
    try {
      attendanceDao.insert(a);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다!</p>");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
