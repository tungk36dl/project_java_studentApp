package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import beans.Cohort;


public class DaoCohort {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

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
        String sql = "SELECT * FROM Cohort WHERE id = ?";
        return  template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Cohort>(Cohort.class));
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
