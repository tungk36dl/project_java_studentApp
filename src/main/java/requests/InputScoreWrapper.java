package requests;

import java.util.List;

public class InputScoreWrapper {
    private String subjectDetailId;
    private List<InputScoreRequest> scores;
	public String getSubjectDetailId() {
		return subjectDetailId;
	}
	public void setSubjectDetailId(String subjectDetailId) {
		this.subjectDetailId = subjectDetailId;
	}
	public List<InputScoreRequest> getScores() {
		return scores;
	}
	public void setScores(List<InputScoreRequest> scores) {
		this.scores = scores;
	}

    // Getters và Setters
}
