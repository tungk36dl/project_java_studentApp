package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import beans.Classes;

public class DaoClasses {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public int save(Classes c) {
        String sql = "INSERT INTO Cohort (id, className, majorId, cohortId status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, c.getId() ,c.getClassName(), c.getMajorId(), c.getCohortId(), c.isStatus(), c.getCreatedDate(), c.getUpdatedDate());
    }

    public int update(Classes c) {
        String sql = "UPDATE Classes SET classesName = ?, majorId = ?, cohortId = ?,  Status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, c.getClassName(), c.getMajorId(), c.getCohortId(), c.isStatus(), c.getUpdatedDate(), c.getId());
    }

    public int delete(String id) {
        String sql = "DELETE FROM Classes WHERE id = ?";
        return template.update(sql, id);
    }

    public Classes getClassesById(String id) {
        String sql = "SELECT * FROM Classes WHERE id = ?";
        return  template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Classes>(Classes.class));
    }
  
    public List<Classes> getClasses() {
        String sql = "SELECT * FROM Classes";
        return template.query(sql, new RowMapper<Classes>() {
            @Override
            public Classes mapRow(ResultSet rs, int rowNum) throws SQLException {
               Classes classes = new Classes();
                classes.setId(rs.getString("id"));
                classes.setClassName(rs.getString("className"));
                classes.setMajorId(rs.getString("majorId"));
                classes.setCohortId(rs.getString("cohortId"));
                classes.setStatus(rs.getBoolean("status"));
                classes.setCreatedDate(rs.getDate("createdDate"));
                classes.setUpdatedDate(rs.getDate("updatedDate"));
                return classes;
            }
        });
    }
}
