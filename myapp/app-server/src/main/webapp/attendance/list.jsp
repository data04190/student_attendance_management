<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>

<%@ page import="java.util.List"%>
<%@ page import="bitcamp.myapp.vo.Attendance"%>

<%
  request.setAttribute("refresh", "2;url=list");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>NAVER CLOUD 학생 관리 시스템</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>출결 관리</h1>
<div style='margin:5px;'>
<a href='/attendance/form.jsp'>새 글</a>
</div>
<table border='1'>
<thead>
  <tr><th>번호</th> <th>날짜</th> <th>이름</th> <th>입실 시간</th> <th>퇴실 시간</th> <th>스터디 시간</th> <th>지각 여부</th></tr>
</thead>

<jsp:useBean id="attendanceDao" type="bitcamp.myapp.dao.AttendanceDao" scope="application"/>

<%
  List<Attendance> list = attendanceDao.findAll();
%>
<tbody>
<%
  for (Attendance attendance : list) {
    pageContext.setAttribute("attendance", attendance);
%>
    <tr>
      <td>${attendance.no}</td>
      <td><a href='/attendance/detail.jsp?no=${attendance.no}'>
        ${attendance.date}
        </a>
      </td>
      <td>${attendance.studentNo.name}</td>
      <td>${attendance.entryTime}</td>
      <td>${attendance.exitTime}</td>
      <td>${attendance.studyTime}</td>
      <td>${attendance.lateStatus}</td>
    </tr>
<%
  }
%>
</tbody>
</table>
<a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>

</body>
</html>
