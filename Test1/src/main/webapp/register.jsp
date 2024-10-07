<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO, user.User"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%
String name = request.getParameter("name");
String studentId = request.getParameter("studentId");
String password = request.getParameter("password");
String message = "";

if (name != null && studentId != null && password != null) {
	UserDAO userDAO = UserDAO.getInstance();
	if (userDAO.isStudentIdAvailable(studentId)) {
		User user = new User(studentId, password, name);
		userDAO.insert(user);
		message = "회원가입이 완료되었습니다.";
	} else {
		message = "이미 사용 중인 학번입니다.";
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입</title>
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
}

.container {
	width: 400px;
	margin: 100px auto;
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 5px;
	background-color: #f9f9f9;
}

h2 {
	text-align: center;
}

input[type="text"], input[type="password"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 3px;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #0000FF;
	color: #fff;
	cursor: pointer;
	padding: 10px 20px;
	border: none;
	border-radius: 3px;
}

.message {
	margin-top: 10px;
	color: #ff0000;
}

.message a {
	color: #0000FF;
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="container">
		<h2>회원가입</h2>
		<form action="register.jsp" method="post">
			이름: <input type="text" name="name"><br> 학번: <input
				type="text" name="studentId"><br> 비밀번호: <input
				type="password" name="password"><br> <input
				type="submit" value="가입하기">
		</form>
		<p class="message"><%=message%></p>
		<a href="index.jsp">로그인 화면으로</a>
	</div>
</body>
</html>