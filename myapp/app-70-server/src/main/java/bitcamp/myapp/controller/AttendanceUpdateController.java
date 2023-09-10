package bitcamp.myapp.controller;

import bitcamp.myapp.service.AttendanceService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Controller("/attendance/update")
public class AttendanceUpdateController implements PageController {

  @Autowired
  AttendanceService attendanceService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    try {
      Attendance a = attendanceService.get(Integer.parseInt(request.getParameter("no")));
      if (a == null || loginUser.getLevel() != 2) {
        throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
      }
      a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      a.setLateStatus();

      attendanceService.update(a);
      return "redirect:list";


    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=detail?no=" + request.getParameter("no"));
      throw e;
    }
  }
}
