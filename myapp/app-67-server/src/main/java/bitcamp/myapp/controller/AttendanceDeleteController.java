package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;


public class AttendanceDeleteController implements PageController {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceDeleteController(AttendanceDao attendanceDao, SqlSessionFactory sqlSessionFactory) {
    this.attendanceDao = attendanceDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
      if (attendanceDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}

