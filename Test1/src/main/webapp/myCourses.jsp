<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, course.CourseDAO, course.Course"%>
<%
request.setCharacterEncoding("utf-8");
%>
<%
String studentId = (String) session.getAttribute("userId");
CourseDAO courseDAO = CourseDAO.getInstance();
List<Course> courses = courseDAO.getEnrolledCourses(studentId);

String courseId = request.getParameter("courseId");
if (courseId != null && studentId != null) {
	courseDAO.unenrollCourse(studentId, courseId);
	response.sendRedirect("myCourses.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>내 강좌 목록</title>
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
}

.container {
	width: 850px;
	margin: 50px auto;
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 5px;
	background-color: #f9f9f9;
	text-align: center;
}

h2 {
	text-align: center;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	border: 1px solid #ccc;
}

th {
	background-color: #0000FF;
	color: #fff;
}

input[type="submit"] {
	background-color: #0000FF;
	color: #fff;
	cursor: pointer;
	padding: 5px 10px;
	border: none;
	border-radius: 3px;
}

a {
	color: #0000FF;
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="container">
		<h2>내 강좌 목록</h2>
		<%
		if (courses.isEmpty()) {
		%>
		<p>
			수강 신청한 강좌가 없습니다. <a href="courseList.jsp">강좌 목록으로 이동</a>
		</p>
		<%
		} else {
		%>
		<table>
			<tr>
				<th>강좌명</th>
				<th>교수명</th>
				<th>학점</th>
				<th>강의시간</th>
				<th>학과</th>
				<th>학년</th>
				<th>취소</th>
			</tr>
			<%
			for (Course course : courses) {
			%>
			<tr>
				<td><%=course.getName()%></td>
				<td><%=course.getProfessor()%></td>
				<td><%=course.getCredit()%></td>
				<td><%=course.getSchedule()%></td>
				<td><%=course.getDepartment()%></td>
				<td><%=course.getYear()%></td>
				<td>
					<form action="myCourses.jsp" method="post">
						<input type="hidden" name="courseId" value="<%=course.getId()%>">
						<input type="submit" value="취소">
					</form>
				</td>
			</tr>
			<% } %>
		</table>
		<% } %>
		<p>
			<a href="courseList.jsp">강좌 목록으로 이동</a><br>
			<a href="index.jsp">로그아웃</a>
		</p>
	</div>
</body>
</html>