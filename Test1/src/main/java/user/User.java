package user;

public class User {
	private String studentId;
	private String password;
	private String name;

	public User(String studentId, String password, String name) {
		this.studentId = studentId;
		this.password = password;
		this.name = name;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}