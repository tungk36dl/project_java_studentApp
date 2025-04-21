package beans;

import java.util.Date;

public class Score {
    private String id;
    private String studentId;
    private String subjectDetailId;
    private float score10;
    private String letterGrade;
    private float gpa4;
    
    private boolean status;
    private Date createdDate;
    private Date updatedDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getSubjectDetailId() {
		return subjectDetailId;
	}
	public void setSubjectDetailId(String subjectDetailId) {
		this.subjectDetailId = subjectDetailId;
	}
	public float getScore10() {
		return score10;
	}
	public void setScore10(float score10) {
		this.score10 = score10;
	}
	public String getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	public float getGpa4() {
		return gpa4;
	}
	public void setGpa4(float gpa4) {
		this.gpa4 = gpa4;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


}
