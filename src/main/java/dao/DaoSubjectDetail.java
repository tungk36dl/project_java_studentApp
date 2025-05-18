package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import beans.SubjectDetail;
import beans.Semester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class DaoSubjectDetail {
	@Autowired
    private JdbcTemplate template;

    public int save(SubjectDetail sd) {
        String sql = "INSERT INTO subject_detail (id, teacherId, subjectId, classId, semester, credit, status, createdDate, updatedDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql,
                sd.getId(),
                sd.getTeacherId(),
                sd.getSubjectId(),
                sd.getClassId(),
                sd.getSemester().name(),
                sd.getCredit(),
                sd.isStatus(),
                new java.sql.Timestamp(sd.getCreatedDate().getTime()),
                new java.sql.Timestamp(sd.getUpdatedDate().getTime())
        );
    }

    public int update(SubjectDetail sd) {
        String sql = "UPDATE subject_detail SET teacherId = ?, subjectId = ?, classId = ?, semester = ?, credit = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql,
                sd.getTeacherId(),
                sd.getSubjectId(),
                sd.getClassId(),
                sd.getSemester().name(),
                sd.getCredit(),
                sd.isStatus(),
                new java.sql.Timestamp(System.currentTimeMillis()),
                sd.getId()
        );
    }

    public int delete(String id) {
        String sql = "DELETE FROM subject_detail WHERE id = ?";
        return template.update(sql, id);
    }

    public SubjectDetail getById(String id) {
        String sql = "SELECT * FROM subject_detail WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new RowMapper<SubjectDetail>() {
            @Override
            public SubjectDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                SubjectDetail sd = new SubjectDetail();
                sd.setId(rs.getString("id"));
                sd.setTeacherId(rs.getString("teacherId"));
                sd.setSubjectId(rs.getString("subjectId"));
                sd.setClassId(rs.getString("classId"));
                sd.setSemester(Semester.valueOf(rs.getString("semester")));
                sd.setCredit(rs.getInt("credit"));
                sd.setStatus(rs.getBoolean("status"));
                sd.setCreatedDate(rs.getTimestamp("createdDate"));
                sd.setUpdatedDate(rs.getTimestamp("updatedDate"));
                return sd;
            }
        });
    }

    public List<SubjectDetail> getAll() {
        String sql = "SELECT * FROM subject_detail";
        return template.query(sql, new RowMapper<SubjectDetail>() {
            @Override
            public SubjectDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                SubjectDetail sd = new SubjectDetail();
                sd.setId(rs.getString("id"));
                sd.setTeacherId(rs.getString("teacherId"));
                sd.setSubjectId(rs.getString("subjectId"));
                sd.setClassId(rs.getString("classId"));
                sd.setSemester(Semester.valueOf(rs.getString("semester")));
                sd.setCredit(rs.getInt("credit"));
                sd.setStatus(rs.getBoolean("status"));
                sd.setCreatedDate(rs.getTimestamp("createdDate"));
                sd.setUpdatedDate(rs.getTimestamp("updatedDate"));
                return sd;
            }
        });
    }
    
    public List<SubjectDetail> getSubjectDetailByTeacherId(String teacherId){
    	String sql = "SELECT * FROM subject_detail WHERE teacherId = ?";
    	return template.query(sql, new Object[] {teacherId}, new BeanPropertyRowMapper<>(SubjectDetail.class));
    }
    
    public SubjectDetail getSubjectDetailByClassIdAndSubjectId(String classId, String subjectId){
    	String sql = "SELECT * FROM subject_detail WHERE classId = ? AND subjectId = ?";
    	List<SubjectDetail> list = template.query(sql, new Object[] {classId, subjectId}, new BeanPropertyRowMapper<>(SubjectDetail.class));
    	return list.isEmpty() ? null : list.get(0);
    }
}
