package response;

public class InforScoreStudentResponse {
	private String fullName;
	private String dateOfBirth;
	private String className;
	private String studentId;
	private Integer totalCredits;
	private Float avgGPA;
	private String rating;
	private String address;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Integer getTotalCredits() {
		return totalCredits;
	}
	public void setTotalCredits(Integer totalCredits) {
		this.totalCredits = totalCredits;
	}
	public Float getAvgGPA() {
		return avgGPA;
	}
	public void setAvgGPA(Float avgGPA) {
		this.avgGPA = avgGPA;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
