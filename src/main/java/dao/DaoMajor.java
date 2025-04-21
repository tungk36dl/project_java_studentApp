package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import beans.Major;  // Thay Subject thành Major

public class DaoMajor {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public int save(Major major) {  // Thay Subject thành Major
        String sql = "INSERT INTO major (id, majorName, status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?)";  // Thay subject thành major
        return template.update(sql, major.getId(), major.getMajorName(), major.isStatus(), major.getCreatedDate(), major.getUpdatedDate());
    }

    public int update(Major major) {  // Thay Subject thành Major
        String sql = "UPDATE major SET majorName = ?, status = ?, updatedDate = ? WHERE id = ?";  // Thay subject thành major
        return template.update(sql, major.getMajorName(), major.isStatus(), new java.sql.Timestamp(System.currentTimeMillis()), major.getId());
    }

    public int delete(String id) {
        String sql = "DELETE FROM major WHERE id = ?";  // Thay subject thành major
        return template.update(sql, id);
    }
    
    public Major getMajorById(String id) {  // Thay Subject thành Major
        String sql = "SELECT * FROM major WHERE id = ?";  // Thay subject thành major
        return template.queryForObject(sql, new Object[] { id}, new BeanPropertyRowMapper<>(Major.class));
    }
    
    public List<Major> getMajor() {  // Thay Subject thành Major
        String sql = "SELECT * FROM major";  // Thay subject thành major
        return template.query(sql, new RowMapper<Major>() {  // Thay Subject thành Major
            @Override
            public Major mapRow(ResultSet rs, int rowNum) throws SQLException {
                Major major = new Major(sql, sql, false, null, null);  // Thay Subject thành Major
                major.setId(rs.getString("id"));
                major.setMajorName(rs.getString("majorName"));
                major.setStatus(rs.getBoolean("status"));
                major.setCreatedDate(rs.getDate("createdDate"));
                major.setUpdatedDate(rs.getDate("updatedDate"));
                
                return major;
            }
        });
    }
}