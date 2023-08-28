<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>NAVER CLOUD 학생 관리 시스템</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>출결 체크</h1>
<form action='/attendance/add.jsp' method='post'>
  입실 시간: <input type='time' name='entryTime'><br>
  퇴실 시간: <input type='time' name='exitTime'><br>
  스터디 시간: <input type='time' name='studyTime'>

<button>등록</button>
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>
