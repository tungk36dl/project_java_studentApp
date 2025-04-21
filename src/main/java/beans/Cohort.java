package beans;

import java.util.Date;

import utils.IdUtil;


public class Cohort {
	private String id;
	private String cohortName;
	private	boolean	status; 
	private	Date createdDate;
	private Date updatedDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCohortName() {
		return cohortName;
	}
	public void setCohortName(String cohortName) {
		this.cohortName = cohortName;
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
	
	

	public Cohort(String id, String cohortName, boolean status, Date createdDate, Date updatedDate) {
		super();
		this.id = IdUtil.generateId();
		this.cohortName = cohortName;
		this.status = status;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	public Cohort() {
		// TODO Auto-generated constructor stub
	}
	
	
}




