package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AttendanceDetailController implements PageController {

  AttendanceDao attendanceDao;

  public AttendanceDetailController(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("attendance", attendanceDao.findBy(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/attendance/detail.jsp";

  }
}

//
//  @Override
//  protected void doGet(HttpServletRequest request, HttpServletResponse response)
//          throws ServletException, IOException {
//
//    AttendanceDao attendanceDao = (AttendanceDao) this.getServletContext().getAttribute("attendanceDao");
//    request.setAttribute("attendance", attendanceDao.findBy(Integer.parseInt(request.getParameter("no"))));
//    request.setAttribute("viewUrl", "/WEB-INF/jsp/attendance/detail.jsp");
//  }
//}
