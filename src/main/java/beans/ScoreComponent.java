package beans;

import java.util.Date;

import utils.IdUtil;

public class ScoreComponent {
    private String id;
    private String scoreId;
    private String typeScore;
    private Float score;
    private boolean status;
    private Date createdDate;
    private Date updatedDate;
    
    
    
    
    
	public Float getScore() {
		return score;
	}


	public void setScore(Float score) {
		this.score = score;
	}


	public ScoreComponent(String scoreId, String typeScore, boolean status) {
		super();
		this.id = IdUtil.generateId();
		this.scoreId = scoreId;
		this.typeScore = typeScore;
		this.status = status;
		this.createdDate = new Date();
		this.updatedDate = new Date();
	}
	
	
	public ScoreComponent() {
		super();
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public String getTypeScore() {
		return typeScore;
	}
	public void setTypeScore(String typeScore) {
		this.typeScore = typeScore;
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
