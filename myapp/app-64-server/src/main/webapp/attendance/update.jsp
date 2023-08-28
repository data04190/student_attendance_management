<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>

<%@ page import="bitcamp.myapp.vo.Attendance"%>
<%@ page import="bitcamp.myapp.vo.Member"%>
<%@ page import="java.time.LocalTime"%>

<jsp:useBean id="attendanceDao" type="bitcamp.myapp.dao.AttendanceDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="bitcamp.util.NcpObjectStorageService" scope="application"/>
<jsp:useBean id="loginUser" class="bitcamp.myapp.vo.Member" scope="session"/>

<%
    if (loginUser.getNo() == 0) {
      response.sendRedirect("/auth/form.jsp");
      return;
    }

    request.setAttribute("refresh", "2;url=list.jsp");

    try {
        Attendance a = new Attendance();
        a.setNo(Integer.parseInt(request.getParameter("no")));
        a.setEntryTime(LocalTime.parse(request.getParameter("entryTime")));
        a.setExitTime(LocalTime.parse(request.getParameter("exitTime")));
        a.setStudyTime(LocalTime.parse(request.getParameter("studyTime")));
        a.setLateStatus();

        if (attendanceDao.update(a) == 0) {
            throw new Exception("게시글이 없거나 변경 권한이 없습니다.");
        }


        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    } catch (Exception e) {

        e.printStackTrace(); // 혹은 다른 오류 처리 방법을 사용
    }
%>