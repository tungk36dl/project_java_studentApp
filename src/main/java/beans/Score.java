package beans;

import java.util.Date;

import utils.IdUtil;

public class Score {
    private String id;
    private String studentId;
    private String subjectDetailId;
    private Float score10; // Điểm tổng
    private String letterGrade; // Điểm chữ
    private Float gpa4; // Điểm GPA
    private Integer examTime; // Lần thi thứ mấy
    
    private boolean status;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
    
    
    
    
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Float getScore10() {
		return score10;
	}
	public void setScore10(Float score10) {
		this.score10 = score10;
	}
	public Float getGpa4() {
		return gpa4;
	}
	public void setGpa4(Float gpa4) {
		this.gpa4 = gpa4;
	}
	public Integer getExamTime() {
		return examTime;
	}
	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}
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

	public String getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
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
	public Score(String studentId, String subjectDetailId, boolean status) {
		super();
		this.id = IdUtil.generateId();
		this.studentId = studentId;
		this.subjectDetailId = subjectDetailId;
		this.status = status;
		this.createdDate = new Date();
		this.updatedDate = new Date();
	}
	public Score() {
		super();
	}
	
	
	
	


}
