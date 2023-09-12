package bitcamp.myapp.controller;

import bitcamp.myapp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/attendance/list")
public class AttendanceListController implements PageController {

  @Autowired
  AttendanceService attendanceService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      request.setAttribute("list", attendanceService.list());
      return "/WEB-INF/jsp/attendance/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }
}
