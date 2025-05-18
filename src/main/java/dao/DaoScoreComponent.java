package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import beans.Score;
import beans.ScoreComponent;
import beans.TypeScore;

@Repository
public class DaoScoreComponent {
	@Autowired
    private JdbcTemplate template;

    
    public int save(ScoreComponent s) {
        String sql = "INSERT INTO scoreComponent (id, scoreId, typeScore,  status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, s.getId(), s.getScoreId(), s.getTypeScore(), s.isStatus() , s.getCreatedDate(), s.getUpdatedDate());
   }

    public int update(ScoreComponent s) {
        String sql = "UPDATE scoreComponent SET score = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, s.getScore(), s.isStatus(), new java.sql.Timestamp(System.currentTimeMillis()), s.getId());

    }

    public int delete(String id) {
        String sql = "DELETE FROM scoreComponent WHERE id = ?";
        return template.update(sql, id);
    }
    
    public ScoreComponent getSubjectById(String id) {
        String sql = "SELECT * FROM scoreComponent WHERE id = ?";
        return template.queryForObject(sql, new Object[] { id}, new BeanPropertyRowMapper<>(ScoreComponent.class));
    }
    
 
    public List<ScoreComponent> getScoreComponentByScoreId(String scoreId) {
    	String sql = "SELECT * FROM scoreComponent WHERE scoreId = ?";
    	return template.query(sql, new Object[] {scoreId}, new BeanPropertyRowMapper<>(ScoreComponent.class));
    }
    
    public ScoreComponent getScoreComponentByScoreIdAndTypeScore(String scoreId, String typeScore) {
    	String sql = "SELECT * FROM scoreComponent WHERE scoreId = ? and typeScore = ?";
    	return template.queryForObject(sql, new Object[] {scoreId, typeScore}, new BeanPropertyRowMapper<>(ScoreComponent.class));
    }
}
