package response;

public class InputScoreResponse {
	private String studentName;
	private String studentId;
	private float scoreAttendance;
	private float scoreTest;
	private float scoreFinalExam;
	// Hỗ trợ lưu điẻm về sau
	private String scoreId;
	
	
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public float getScoreAttendance() {
		return scoreAttendance;
	}
	public void setScoreAttendance(float scoreAttendance) {
		this.scoreAttendance = scoreAttendance;
	}
	public float getScoreTest() {
		return scoreTest;
	}
	public void setScoreTest(float scoreTest) {
		this.scoreTest = scoreTest;
	}
	public float getScoreFinalExam() {
		return scoreFinalExam;
	}
	public void setScoreFinalExam(float scoreFinalExam) {
		this.scoreFinalExam = scoreFinalExam;
	}
	
	
	
}
