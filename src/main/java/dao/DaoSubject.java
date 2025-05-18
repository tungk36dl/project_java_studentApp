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
import beans.Subject;


@Repository
public class DaoSubject {
	@Autowired
    private JdbcTemplate template;

    
    public int save(Subject s) {
        String sql = "INSERT INTO subject (id, code, name, status, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, s.getId(), s.getCode(), s.getName(), s.isStatus(), s.getCreatedDate(), s.getUpdatedDate());
    }

    public int update(Subject s) {
        String sql = "UPDATE subject SET code = ?, name = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql, s.getCode(), s.getName(), s.isStatus(), new java.sql.Timestamp(System.currentTimeMillis()), s.getId());

    }

    public int delete(String id) {
        String sql = "DELETE FROM subject WHERE id = ?";
        return template.update(sql, id);
    }
    
    public Subject getSubjectById(String id) {
    	String sql = "SELECT * FROM subject WHERE id = ?";
        List<Subject> list = template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subject.class));
        return list.isEmpty() ? null : list.get(0);
    }
    
    public Subject getSubjectByName(String subjectName) {
    	String sql = "SELECT * FROM subject WHERE name = ?";
        List<Subject> list = template.query(sql, new Object[]{subjectName}, new BeanPropertyRowMapper<>(Subject.class));
        return list.isEmpty() ? null : list.get(0);
    }
    
    public Subject getSubjectByCode(String code) {
    	String sql = "SELECT * FROM subject WHERE code = ?";
        List<Subject> list = template.query(sql, new Object[]{code}, new BeanPropertyRowMapper<>(Subject.class));
        return list.isEmpty() ? null : list.get(0);
    }
    
    
    public List<Subject> getAll() {
        String sql = "SELECT * FROM subject";
        return template.query(sql, new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
                Subject subject = new Subject();
                subject.setId(rs.getString("id"));
                subject.setCode(rs.getString("code"));
                subject.setName(rs.getString("name"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setCreatedDate(rs.getDate("createdDate"));
                subject.setUpdatedDate(rs.getDate("updatedDate"));
                
                return subject;
            }
        });
    }
}
