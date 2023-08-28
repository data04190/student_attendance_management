<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style='height:50px;background-color:orange;'>
    <img src='https://www.ncloud.com/public/img/logo-m.png' style='height:40px'>
    <a href='/member/list.jsp'>학생 관리</a>
    <a href='/attendance/list.jsp'>출결 체크</a>
    <a href='/board/list.jsp'>공지 사항</a>

<c:choose>
    <c:when test="${empty sessionScope.loginUser}">
        <a href='/auth/form.jsp'>로그인</a>
    </c:when>
    <c:otherwise>
        <c:if test="${empty sessionScope.loginUser.photo}">
            <img style='height:40px' src='/images/avatar.png'>
        </c:if>
        <c:if test="${not empty sessionScope.loginUser.photo}">
            <img src='http://kgddbipzoniy19010732.cdn.ntruss.com/student_member/${loginUser.photo}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
        </c:if>
        ${loginUser.name} <a href='/auth/logout.jsp'>로그아웃</a>
    </c:otherwise>
  </c:choose>
</div>