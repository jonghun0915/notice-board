package user;

import java.sql.*;

public class UserDAO {
	private String uid = "root";
	private String upw = "1234";
	private String url = "jdbc:mysql://localhost:3306/ui_test";

	private UserDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static UserDAO dao = new UserDAO();

	public static UserDAO getInstance() {
		return dao;
	}

	public int insert(User user) {
		String sql = "INSERT INTO users (student_id, password, name) VALUES (?, ?, ?)";
		int res = 0;
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getStudentId());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			res = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public User login(String studentId, String password) {
		User user = null;
		String sql = "SELECT * FROM users WHERE student_id = ? AND password = ?";
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, studentId);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User(rs.getString("student_id"), rs.getString("password"), rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isStudentIdAvailable(String studentId) {
		boolean available = true;
		String sql = "SELECT COUNT(*) FROM users WHERE student_id = ?";
		try (Connection conn = DriverManager.getConnection(url, uid, upw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, studentId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next() && rs.getInt(1) > 0) {
					available = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return available;
	}
}