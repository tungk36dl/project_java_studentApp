package response;

public class ScoreResponse {
    private String subjectCode;        // Mã học phần (VD: NCKH)
    private String subjectName;        // Tên học phần (VD: PP Nghiên cứu khoa học)
    private int credits;               // Số tín chỉ (Số TC)
    private String semester;
    
    public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	private Float score10;            // Điểm hệ 10
    private String scoreLetter;        // Điểm chữ (A, B, C, D, F)
    private Float GPA4;             // Điểm hệ 4
    private String examExemption;      // Điểm miễn (nếu có)

    private Float scoreAttendance;            // Điểm TP L1 (thực hành/giữa kỳ, nếu có)
    private Float scoreTest;          // Điểm thi L1 (cuối kỳ)
    private Float scoreFinalExam;          // Điểm hệ 10 L1

    private Float scoreAttendanceL2;          // Điểm TP L2 (nếu thi lại)
    private Float scoreTestL2;        // Điểm thi L2 (nếu thi lại)
    private Float scoreFinalExamL2;          // Điểm hệ 10 L2 (nếu thi lại)

    private boolean status;

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Float getScore10() {
		return score10;
	}

	public void setScore10(Float score10) {
		this.score10 = score10;
	}

	public String getScoreLetter() {
		return scoreLetter;
	}

	public void setScoreLetter(String scoreLetter) {
		this.scoreLetter = scoreLetter;
	}

	public Float getGPA4() {
		return GPA4;
	}

	public void setGPA4(Float gPA4) {
		GPA4 = gPA4;
	}

	public String getExamExemption() {
		return examExemption;
	}

	public void setExamExemption(String examExemption) {
		this.examExemption = examExemption;
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

	public Float getScoreAttendanceL2() {
		return scoreAttendanceL2;
	}

	public void setScoreAttendanceL2(Float scoreAttendanceL2) {
		this.scoreAttendanceL2 = scoreAttendanceL2;
	}

	public Float getScoreTestL2() {
		return scoreTestL2;
	}

	public void setScoreTestL2(Float scoreTestL2) {
		this.scoreTestL2 = scoreTestL2;
	}

	public Float getScoreFinalExamL2() {
		return scoreFinalExamL2;
	}

	public void setScoreFinalExamL2(Float scoreFinalExamL2) {
		this.scoreFinalExamL2 = scoreFinalExamL2;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}

