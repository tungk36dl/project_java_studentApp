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

@Repository
public class DaoScore {
	@Autowired
    private JdbcTemplate template;

    
    public int save(Score s) {
        String sql = "INSERT INTO score (id, subjectDetailId, studentId,  status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, s.getId(), s.getSubjectDetailId(), s.getStudentId(), s.isStatus() , s.getCreatedDate(), s.getUpdatedDate());
    }

    public int update(Score s) {
        String sql = "UPDATE score SET score10 = ?, gpa4 = ?, letterGrade = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, s.getScore10(), s.getGpa4(), s.getLetterGrade(), s.isStatus(), new java.sql.Timestamp(System.currentTimeMillis()), s.getId());

    }

    public int delete(String id) {
        String sql = "DELETE FROM score WHERE id = ?";
        return template.update(sql, id);
    }
    
    public Score getScoreById(String id) {
        String sql = "SELECT * FROM score WHERE id = ?";
        return template.queryForObject(sql, new Object[] { id}, new BeanPropertyRowMapper<>(Score.class));
    }
    
    public List<Score> getAll() {
        String sql = "SELECT * FROM score";
        return template.query(sql, new RowMapper<Score>() {
            @Override
            public Score mapRow(ResultSet rs, int rowNum) throws SQLException {
                Score score = new Score();
                score.setId(rs.getString("id"));
                score.setSubjectDetailId(rs.getString("subjectDetailId"));
                score.setStudentId(rs.getString("studentId"));
                score.setScore10(rs.getFloat("score10"));
                score.setLetterGrade(rs.getString("letterGrade"));
                score.setGpa4(rs.getFloat("gpa4"));
                score.setStatus(rs.getBoolean("status"));
                score.setCreatedDate(rs.getDate("createdDate"));
                score.setUpdatedDate(rs.getDate("updatedDate"));
                
                return score;
            }
        });
    }
    
    public List<Score> getScoreByStudentId(String studentId) {
    	String sql = "SELECT * FROM score WHERE studentId = ?";
    	return template.query(sql, new Object[]{studentId},  new BeanPropertyRowMapper<>(Score.class));
    	
    }
    
    public List<Score> getScoreBySubjectDetailId(String subjectDetailId) {
    	String sql = "SELECT * FROM score WHERE subjectDetailId = ?";
    	return template.query(sql, new Object[] {subjectDetailId}, new BeanPropertyRowMapper<>(Score.class));
    }
    
//    public Score getScoreByStudentIdAndSubjectDetailId(String studentId, String subjectDetailId) {
//    	String sql = "SELECT * FROM score WHERE studentId = ? AND subjectDetailId = ?";
//    	return template.queryForObject(sql, new Object[] {studentId, subjectDetailId}, new BeanPropertyRowMapper<>(Score.class));
//    }
    
    public Score getScoreByStudentIdAndSubjectDetailId(String studentId, String subjectDetailId) {
        String sql = "SELECT * FROM score WHERE studentId = ? AND subjectDetailId = ?";
        List<Score> list = template.query(sql, new Object[] {studentId, subjectDetailId}, new BeanPropertyRowMapper<>(Score.class));
        return list.isEmpty() ? null : list.get(0);
    }

    
    
    
}
