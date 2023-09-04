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
import java.time.LocalDate;
import java.time.LocalTime;

@Component("/attendance/add")
public class AttendanceAddController implements PageController {

  AttendanceDao attendanceDao;
  PlatformTransactionManager txManager;
  NcpObjectStorageService ncpObjectStorageService;

  public AttendanceAddController(
          AttendanceDao attendanceDao,
          PlatformTransactionManager txManager,
          NcpObjectStorageService ncpObjectStorageService) {
    this.attendanceDao = attendanceDao;
    this.txManager = txManager;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

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

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      Attendance attendance = new Attendance();
      attendance.setDate(LocalDate.now());
      attendance.setStudentNo(loginUser);
      attendance.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
      attendance.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
      attendance.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
      attendance.setLateStatus();

      attendanceDao.insert(attendance);

      txManager.commit(status);
      return "redirect:list";

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
