package beans;

import java.util.Date;

public class ScoreComponent {
    private String id;
    private String scoreId;
    private TypeScore typeScore;
    private float score;
    private boolean status;
    private Date createdDate;
    private Date updatedDate;
    
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
	public TypeScore getTypeScore() {
		return typeScore;
	}
	public void setTypeScore(TypeScore typeScore) {
		this.typeScore = typeScore;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
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
