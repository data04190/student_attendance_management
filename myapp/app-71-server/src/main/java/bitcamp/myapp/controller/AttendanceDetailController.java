package bitcamp.myapp.controller;

import bitcamp.myapp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/attendance/detail")
public class AttendanceDetailController implements PageController {

  @Autowired
  AttendanceService attendanceService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("attendance", attendanceService.get(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/attendance/detail.jsp";

  }
}
