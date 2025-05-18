package requests;

public class InputScoreRequest {
    private String studentId;
    private String studentName;
    private String scoreId; // mới thêm

    private Float scoreAttendance;
    private Float scoreTest;
    private Float scoreFinalExam;
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Float getScoreAttendance() {
		return scoreAttendance;
	}
	public void setScoreAttendance(Float scoreAttendance) {
		this.scoreAttendance = scoreAttendance;
	}
	public Float getScoreTest() {
		return scoreTest;
	}
	public void setScoreTest(Float scoreTest) {
		this.scoreTest = scoreTest;
	}
	public Float getScoreFinalExam() {
		return scoreFinalExam;
	}
	public void setScoreFinalExam(Float scoreFinalExam) {
		this.scoreFinalExam = scoreFinalExam;
	}
	
    
    
    
}
