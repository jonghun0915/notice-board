package course;

import java.sql.*;
import java.util.*;

public class CourseDAO {
	private String uid = "root";
	private String upw = "1234";
	private String url = "jdbc:mysql://localhost:3306/ui_test";

	private CourseDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static CourseDAO dao = new CourseDAO();

	public static CourseDAO getInstance() {
		return dao;
	}

	public List<Course> getCourses(String department, String courseName, String year) {
		List<Course> courses = new ArrayList<>();
		String sql = "SELECT * FROM courses WHERE 1=1";
		List<String> params = new ArrayList<>();

		if (department != null && !department.isEmpty()) {
			sql += " AND department = ?";
			params.add(department);
		}
		if (courseName != null && !courseName.isEmpty()) {
			sql += " AND name LIKE ?";
			params.add("%" + courseName + "%");
		}
		if (year != null && !year.isEmpty()) {
			sql += " AND year = ?";
			params.add(year);
		}

		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, params.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Course course = new Course(rs.getString("id"), rs.getString("name"), rs.getString("professor"),
							rs.getInt("credit"), rs.getString("schedule"), rs.getString("department"),
							rs.getString("year"));
					courses.add(course);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public void enrollCourse(String studentId, String courseId) {
		String sql = "INSERT INTO enrollments (user_id, course_id) VALUES (?, ?)";
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, studentId);
			stmt.setString(2, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Course> getEnrolledCourses(String userId) {
		List<Course> courses = new ArrayList<>();
		String sql = "SELECT c.id, c.name, c.professor, c.credit, c.schedule, c.department, c.year "
				+ "FROM courses c INNER JOIN enrollments e ON c.id = e.course_id " + "WHERE e.user_id = ?";
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Course course = new Course(rs.getString("id"), rs.getString("name"), rs.getString("professor"),
							rs.getInt("credit"), rs.getString("schedule"), rs.getString("department"),
							rs.getString("year"));
					courses.add(course);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public void unenrollCourse(String userId, String courseId) {
		String sql = "DELETE FROM enrollments WHERE user_id = ? AND course_id = ?";
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			stmt.setString(2, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
