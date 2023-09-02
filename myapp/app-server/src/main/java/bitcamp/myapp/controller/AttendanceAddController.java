package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/attendance/add")
public class AttendanceAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/attendance/form.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/login");
      return;
    }

    AttendanceDao attendanceDao = (AttendanceDao) this.getServletContext().getAttribute("attendanceDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

    try {
      Attendance attendance = new Attendance();
      attendance.setDate(LocalDate.now());
      attendance.setStudentNo(loginUser);
      attendance.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      attendance.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      attendance.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      attendance.setLateStatus();

      attendanceDao.insert(attendance);

      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);
    }
  }
}
