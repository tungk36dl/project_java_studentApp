package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import beans.Classes;
import beans.Cohort;

@Repository
public class DaoCohort {
	@Autowired
    private JdbcTemplate template;

    public int save(Cohort c) {
        String sql = "INSERT INTO Cohort (id, cohortName, status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?)";
        return template.update(sql, c.getId() ,c.getCohortName(), c.isStatus(), c.getCreatedDate(), c.getUpdatedDate());
    }

    public int update(Cohort c) {
        String sql = "UPDATE Cohort SET cohortName = ?, Status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, c.getCohortName(), c.isStatus(), c.getUpdatedDate(), c.getId());
    }

    public int delete(String id) {
        String sql = "DELETE FROM Cohort WHERE id = ?";
        return template.update(sql, id);
    }

    public Cohort getCohortById(String id) {
    	String sql = "SELECT * FROM cohort WHERE id = ?";
        List<Cohort> list = template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Cohort.class));
        return list.isEmpty() ? null : list.get(0);
    }
    
    public Cohort getCohortByName(String cohortName) {
    	String sql = "SELECT * FROM cohort WHERE cohortName = ?";
        List<Cohort> list = template.query(sql, new Object[]{cohortName}, new BeanPropertyRowMapper<>(Cohort.class));
        return list.isEmpty() ? null : list.get(0);
    }
  
    public List<Cohort> getCohort() {
        String sql = "SELECT * FROM Cohort";
        return template.query(sql, new RowMapper<Cohort>() {
            @Override
            public Cohort mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cohort cohort = new Cohort();
                cohort.setId(rs.getString("id"));
                cohort.setCohortName(rs.getString("cohortName"));
                cohort.setStatus(rs.getBoolean("status"));
                cohort.setCreatedDate(rs.getDate("createdDate"));
                cohort.setUpdatedDate(rs.getDate("updatedDate"));
                return cohort;
            }
        });
    }
}
