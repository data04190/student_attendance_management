<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>

<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalTime"%>
<%@ page import="bitcamp.myapp.vo.Attendance"%>
<%@ page import="bitcamp.myapp.vo.Member"%>

<jsp:useBean id="attendanceDao" type="bitcamp.myapp.dao.AttendanceDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="bitcamp.myapp.vo.Member" scope="session"/>

<%
    if (loginUser.getNo() == 0) {
      response.sendRedirect("/auth/form.jsp");
      return;
    }

    // 오류가 발생했을 때 refresh 할 URL을 미리 지정한다.
    request.setAttribute("refresh", "2;url=list.jsp");

    Attendance a = new Attendance();
    a.setDate(LocalDate.now());
    a.setStudentNo(loginUser);
    a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
    a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
    a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
    a.setLateStatus();

    attendanceDao.insert(a);


    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");
%>








