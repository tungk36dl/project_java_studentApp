package beans;

import java.util.Date;

public class Classes {
	  private String id; 
	  private String className ;
	  private String majorId ;
	  private String cohortId;
	  private boolean status;
	  private Date createdDate ;
	  private Date updatedDate ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public String getCohortId() {
		return cohortId;
	}
	public void setCohortId(String cohortId) {
		this.cohortId = cohortId;
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
	public Classes(String id, String className, String majorId, String cohortId, boolean status, Date createdDate,
			Date updatedDate) {
		super();
		this.id = id;
		this.className = className;
		this.majorId = majorId;
		this.cohortId = cohortId;
		this.status = status;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	
	public Classes() {
		// TODO Auto-generated constructor stub
	}
	  
	  
	  
	  
	  
}
