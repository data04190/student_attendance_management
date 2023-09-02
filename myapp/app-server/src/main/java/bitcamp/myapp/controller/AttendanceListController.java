package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.dao.BoardDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/attendance/list")
public class AttendanceListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    try {
      AttendanceDao attendanceDao = (AttendanceDao) this.getServletContext().getAttribute("attendanceDao");
      request.setAttribute("list", attendanceDao.findAll());

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/WEB-INF/jsp/attendance/list.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw new ServletException(e);
    }

  }
}
