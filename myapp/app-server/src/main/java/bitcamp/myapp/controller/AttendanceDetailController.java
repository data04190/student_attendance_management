package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/attendance/detail")
public class AttendanceDetailController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    AttendanceDao attendanceDao = (AttendanceDao) this.getServletContext().getAttribute("attendanceDao");
    request.setAttribute("attendance", attendanceDao.findBy(Integer.parseInt(request.getParameter("no"))));
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/attendance/detail.jsp").include(request, response);
  }
}
