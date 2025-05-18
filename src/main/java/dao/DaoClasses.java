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

@Repository
public class DaoClasses {
    @Autowired
    private JdbcTemplate template;

    public int save(Classes c) {
        String sql = "INSERT INTO Classes (id, className, majorId, cohortId, status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, c.getId(), c.getClassName(), c.getMajorId(), c.getCohortId(), c.isStatus(), c.getCreatedDate(), c.getUpdatedDate());
    }

    public int update(Classes c) {
        String sql = "UPDATE Classes SET className = ?, majorId = ?, cohortId = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, c.getClassName(), c.getMajorId(), c.getCohortId(), c.isStatus(), c.getUpdatedDate(), c.getId());
    }

    public int delete(String id) {
        String sql = "DELETE FROM Classes WHERE id = ?";
        return template.update(sql, id);
    }

    public Classes getClassesById(String id) {
        String sql = "SELECT * FROM Classes WHERE id = ?";
        List<Classes> list = template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Classes.class));
        return list.isEmpty() ? null : list.get(0);
    }
    
    public Classes getClassesByClassName(String className) {
        String sql = "SELECT * FROM Classes WHERE className = ?";
        List<Classes> list = template.query(sql, new Object[]{className}, new BeanPropertyRowMapper<>(Classes.class));
        return list.isEmpty() ? null : list.get(0);
    }


    public List<Classes> getAll() {
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
    
    public Classes getClassesByMajorIdAndCohortId(String majorId, String cohortId) {
    	String sql = "SELECT * FROM Classes WHERE majorId = ? AND cohortId = ?";
    	List<Classes> list = template.query(sql, new Object[]{majorId, cohortId}, new BeanPropertyRowMapper<>(Classes.class));
    	return list.isEmpty() ? null : list.get(0);
    }
}
