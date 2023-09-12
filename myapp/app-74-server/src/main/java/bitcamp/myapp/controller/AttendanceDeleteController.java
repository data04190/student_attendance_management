package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.myapp.service.AttendanceService;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/attendance/delete")
public class AttendanceDeleteController implements PageController {

  @Autowired
  AttendanceService attendanceService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
      Attendance a = attendanceService.get(Integer.parseInt(request.getParameter("no")));
      if (a == null || loginUser.getLevel() != 2) {
        throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
      } else {
        attendanceService.delete(a.getNo());
        return "redirect:list";
      }

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}

