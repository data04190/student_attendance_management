package bitcamp.myapp.controller;

import bitcamp.myapp.service.AttendanceService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller("/attendance/add")
public class AttendanceAddController implements PageController {

  @Autowired
  AttendanceService attendanceService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/attendance/form.jsp";
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    try {
      Attendance attendance = new Attendance();
      attendance.setDate(LocalDate.now());
      attendance.setStudentNo(loginUser);
      attendance.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      attendance.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      attendance.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      attendance.setLateStatus();

      attendanceService.add(attendance);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
