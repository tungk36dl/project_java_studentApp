package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import beans.Subject;

public class DaoSubject {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public int save(Subject s) {
        String sql = "INSERT INTO subject (id, name, status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?)";
        return template.update(sql, s.getId(), s.getName(), s.isStatus(), s.getCreatedDate(), s.getUpdatedDate());
    }

    public int update(Subject s) {
        String sql = "UPDATE subject SET name = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, s.getName(), s.isStatus(), new java.sql.Timestamp(System.currentTimeMillis()), s.getId());

    }

    public int delete(String id) {
        String sql = "DELETE FROM subject WHERE id = ?";
        return template.update(sql, id);
    }
    
    public Subject getSubjectById(String id) {
        String sql = "SELECT * FROM subject WHERE id = ?";
        return template.queryForObject(sql, new Object[] { id}, new BeanPropertyRowMapper<>(Subject.class));
    }
    
    public List<Subject> getSubject() {
        String sql = "SELECT * FROM subject";
        return template.query(sql, new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
                Subject subject = new Subject(sql, sql, false, null, null);
                subject.setId(rs.getString("id"));
                subject.setName(rs.getString("name"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setCreatedDate(rs.getDate("createdDate"));
                subject.setUpdatedDate(rs.getDate("updatedDate"));
                
                return subject;
            }
        });
    }
}
