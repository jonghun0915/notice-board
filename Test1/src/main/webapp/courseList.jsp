<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, course.CourseDAO, course.Course"%>
<%
request.setCharacterEncoding("utf-8");
%>
<%
String department = request.getParameter("department");
String courseName = request.getParameter("courseName");
String year = request.getParameter("year");

CourseDAO courseDAO = CourseDAO.getInstance();
List<Course> courses = courseDAO.getCourses(department, courseName, year);

String studentId = (String) session.getAttribute("userId");
String courseId = request.getParameter("courseId");
if (courseId != null && studentId != null) {
	courseDAO.enrollCourse(studentId, courseId);
	response.sendRedirect("myCourses.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>개설 강좌 목록</title>
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

form {
	margin-bottom: 20px;
}

input[type="text"], input[type="submit"] {
	padding: 10px;
	margin-right: 10px;
	border: 1px solid #ccc;
	border-radius: 3px;
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
}
</style>
</head>
<body>
	<div class="container">
		<h2>개설 강좌 목록</h2>
		<form action="courseList.jsp" method="get">
			학과: <input type="text" name="department"> 과목명: <input
				type="text" name="courseName"> 학년: <input type="text"
				name="year"> <input type="submit" value="검색">
		</form>
		<table>
			<tr>
				<th>과목코드</th>
				<th>강좌명</th>
				<th>교수명</th>
				<th>학점</th>
				<th>강의시간</th>
				<th>학과</th>
				<th>신청</th>
			</tr>
			<%
			for (Course course : courses) {
			%>
			<tr>
				<td><%=course.getId()%></td>
				<td><%=course.getName()%></td>
				<td><%=course.getProfessor()%></td>
				<td><%=course.getCredit()%></td>
				<td><%=course.getSchedule()%></td>
				<td><%=course.getDepartment()%></td>
				<td>
					<form action="courseList.jsp" method="post">
						<input type="hidden" name="courseId" value="<%=course.getId()%>">
						<input type="submit" value="신청">
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>
</html>