package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Component("/attendance/update")
public class AttendanceUpdateController implements PageController {

  AttendanceDao attendanceDao;
  PlatformTransactionManager txManager;
  NcpObjectStorageService ncpObjectStorageService;

  public AttendanceUpdateController(
          AttendanceDao attendanceDao,
          PlatformTransactionManager txManager,
          NcpObjectStorageService ncpObjectStorageService) {
    this.attendanceDao = attendanceDao;
    this.txManager = txManager;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

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
        txManager.commit(status);
        return "redirect:list";
      }

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("refresh", "2;url=detail?no=" + request.getParameter("no"));
      throw e;
    }
  }
}
