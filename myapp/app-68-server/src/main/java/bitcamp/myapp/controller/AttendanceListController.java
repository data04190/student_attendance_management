package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/attendance/list")
public class AttendanceListController implements PageController {

  AttendanceDao attendanceDao;

  public AttendanceListController(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      request.setAttribute("list", attendanceDao.findAll());
      return "/WEB-INF/jsp/attendance/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }
}
