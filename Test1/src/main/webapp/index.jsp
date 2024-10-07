<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO, user.User"%>
<%
request.setCharacterEncoding("utf-8");
%>
<%
String studentId = request.getParameter("studentId");
String password = request.getParameter("password");
String message = "";

if (studentId != null && password != null) {
	UserDAO userDAO = UserDAO.getInstance();
	User user = userDAO.login(studentId, password);
	if (user != null) {
		session.setAttribute("userId", user.getStudentId());
		response.sendRedirect("courseList.jsp");
	} else {
		message = "로그인에 실패했습니다.";
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
</head>
<body
	style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-image: url('https://kbu.ac.kr/resources/homepage/kor/_Img/Contents/cgreet_topP.jpg'); background-size: cover; background-repeat: no-repeat; background-position: center;">
	<div
		style="width: 300px; margin: 100px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: rgba(255, 255, 255, 0.8);">
		<h2 style="text-align: center;">로그인</h2>
		<form action="index.jsp" method="post">
			학번: <input type="text" name="studentId"
				style="width: 90%; padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 3px;"><br>
			비밀번호: <input type="password" name="password"
				style="width: 90%; padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 3px;"><br>
			<input type="submit" value="로그인"
				style="width: 100%; padding: 10px; border: none; border-radius: 3px; background-color: #0000FF; color: #fff; cursor: pointer;">
		</form>
		<p style="color: red; text-align: center;"><%=message%></p>
		<div style="text-align: center; margin-top: 10px;">
			<a href="register.jsp">회원가입</a>
		</div>
	</div>
</body>
</html>