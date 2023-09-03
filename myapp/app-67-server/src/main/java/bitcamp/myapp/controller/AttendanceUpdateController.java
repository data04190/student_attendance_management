package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

public class AttendanceUpdateController implements PageController {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public AttendanceUpdateController(
          AttendanceDao attendanceDao,
          SqlSessionFactory sqlSessionFactory,
          NcpObjectStorageService ncpObjectStorageService) {
    this.attendanceDao = attendanceDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    try {
      Attendance a = new Attendance();
      a.setNo(Integer.parseInt(request.getParameter("no")));
      a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      a.setLateStatus();

      if (attendanceDao.update(a) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=detail?no=" + request.getParameter("no"));
      throw e;
    }
  }
}
