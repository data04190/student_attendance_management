<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="refresh" value="2;url=list" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>NAVER CLOUD 학생 관리 시스템</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>출결 체크</h1>

<c:if test="${attendance == null}">
    <p>해당 번호의 게시글이 없습니다!</p>
</c:if>

<c:if test="${attendance != null}">
    <style>
        input[type='time'] {
            width: 150px;
        }
    </style>

    <form action='/attendance/update' method='post'>
        <table border='1'>
            <tr>
                <th style='width:120px;'>번호</th>
                <td style='width:150px;'>
                    <input type='text' name='no' value='${attendance.no}' readonly>
                </td>
            </tr>
            <tr>
                <th>이름</th>
                <td>${attendance.studentNo.name}</td>
            </tr>
            <tr>
                <th>입실 시간</th>
                <td><input type='time' name='entryTime' value='${attendance.entryTime}'></td>
            </tr>
            <tr>
                <th>퇴실 시간</th>
                <td><input type='time' name='exitTime' value='${attendance.exitTime}'></td>
            </tr>
            <tr>
                <th>스터디 시간</th>
                <td><input type='time' name='studyTime' value='${attendance.studyTime}'></td>
            </tr>
            <tr>
                <th>지각 여부</th>
                <td>${attendance.lateStatus}</td>
            </tr>
        </table>

    <div>
        <button>변경</button>
        <button type='reset'>초기화</button>
        <a href='/attendance/delete?no=${param.no}'>삭제</a>
        <a href='/attendance/list'>목록</a>
    </div>
    </form>
</c:if>
<jsp:include page="../footer.jsp"/>

</body>
</html>