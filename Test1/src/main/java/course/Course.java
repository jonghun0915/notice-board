package course;

public class Course {
	private String id;
	private String name;
	private String professor;
	private int credit;
	private String schedule;
	private String department;
	private String year;

	public Course(String id, String name, String professor, int credit, String schedule, String department,
			String year) {
		this.id = id;
		this.name = name;
		this.professor = professor;
		this.credit = credit;
		this.schedule = schedule;
		this.department = department;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
