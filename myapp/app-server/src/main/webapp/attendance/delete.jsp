<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>

<%@ page import="bitcamp.myapp.vo.Member"%>

<jsp:useBean id="attendanceDao" type="bitcamp.myapp.dao.AttendanceDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="bitcamp.myapp.vo.Member" scope="session"/>

<%
    if (loginUser.getNo() == 0) {
      response.sendRedirect("/auth/form.jsp");
      return;
    }

    request.setAttribute("refresh", "2;url=list.jsp");

    if (attendanceDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }
%>
