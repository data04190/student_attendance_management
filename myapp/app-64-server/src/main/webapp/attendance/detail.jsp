<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>

<%@ page import="bitcamp.myapp.vo.Attendance"%>

<jsp:useBean id="attendanceDao" type="bitcamp.myapp.dao.AttendanceDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>

<%
    request.setAttribute("refresh", "2;url=list.jsp");
    Attendance attendance = attendanceDao.findBy(Integer.parseInt(request.getParameter("no")));
    pageContext.setAttribute("attendance", attendance);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>NAVER CLOUD 학생 관리 시스템</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>출결 체크</h1>

<% if (attendance == null) { %>
    <p>해당 번호의 게시글이 없습니다!</p>
<% } else { %>
    <style>
        input[type='time'] {
            width: 150px; /* 여기서 150px 부분을 원하는 넓이로 조절하시면 됩니다. */
        }
    </style>
    <form action='/attendance/update.jsp' method='post'>
        <table border='1'>
            <tr>
                <th style='width:120px;'>번호</th>
                <td style='width:150px;'><input type='text' name='no' value='<%= attendance.getNo() %>' readonly></td>
            </tr>
            <tr><th>이름</th> <td><%= attendance.getStudentNo().getName() %></td></tr>
            <tr>
                <th>입실 시간</th>
                <td><input type='time' name='entryTime' value='<%= attendance.getEntryTime() %>'></td>
            </tr>
            <tr>
                <th>퇴실 시간</th>
                <td><input type='time' name='exitTime' value='<%= attendance.getExitTime() %>'></td>
            </tr>
            <tr>
                <th>스터디 시간</th>
                <td><input type='time' name='studyTime' value='<%= attendance.getStudyTime() %>'></td>
            </tr>
            <tr><th>지각 여부</th> <td><%= attendance.getLateStatus() %></td></tr>
        </table>

        <div>
            <button>변경</button>
            <button type='reset'>초기화</button>
            <a href='/attendance/delete.jsp?no=<%= attendance.getNo() %>'>삭제</a>
            <a href='/attendance/list.jsp'>목록</a>
        </div>
    </form>
<% } %>

<jsp:include page="../footer.jsp"/>

</body>
</html>

