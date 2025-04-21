package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import beans.User;

public class DaoUser {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return template.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            return null;
        }
    }


    // CREATE
    public int save(User user) {
        String sql = "INSERT INTO user (id, username, password, fullName, email, dateOfBirth, gender, avatar, identificationNumber, phone, address, classId, role, status, createdDate, updatedDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql,
        		user.getId(),
        		user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getAvatar(),
                user.getIdentificationNumber(),
                user.getPhone(),
                user.getAddress(),
                user.getClassId(),
                user.getRole(),
                user.isStatus(),
                user.getCreatedDate(),
                user.getUpdatedDate());
    }
    
    public int saveBasic(User user) {
    	String sql = "INSERT INTO user (id, username, password, email, role, status, createdDate, updatedDate)"
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    	return template.update(sql,
    			user.getId(),
    			user.getUsername(),
    			user.getPassword(), 
    			user.getEmail(),
    			user.getRole(),
    			user.isStatus(), 
    			user.getCreatedDate(), 
    			user.getUpdatedDate());
    }

    // UPDATE
    public int update(User user) {
        String sql = "UPDATE user SET username = ?, password = ?, fullName = ?, email = ?, dateOfBirth = ?, gender = ?, avatar = ?, identificationNumber = ?, phone = ?, address = ?, classId = ?, roleId = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getAvatar(),
                user.getIdentificationNumber(),
                user.getPhone(),
                user.getAddress(),
                user.getClassId(),
                user.getRole(),
                user.isStatus(),
                user.getUpdatedDate(),
                user.getId());
    }

    // DELETE
    public int delete(String id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return template.update(sql, id);
    }

    // READ - get by ID
    public User getUserById(String id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    // READ - get all
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return template.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setDateOfBirth(rs.getDate("dateofbirth"));
                user.setGender(rs.getString("gender"));
                user.setAvatar(rs.getString("avatar"));
                user.setIdentificationNumber(rs.getString("identificationnumber"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));

                user.setClassId(rs.getString("classid"));

                user.setRole(rs.getString("role"));
                user.setStatus(rs.getBoolean("status"));
                user.setCreatedDate(rs.getTimestamp("createddate"));
                user.setUpdatedDate(rs.getTimestamp("updateddate"));
                return user;
            }
        });
    }
    
 // LỌC THEO roleId
    public List<User> getUsersByRoleId(String roleId) {
        String sql = "SELECT * FROM user WHERE roleId = ?";
        return template.query(sql, new Object[]{roleId}, new BeanPropertyRowMapper<>(User.class));
    }

    // LỌC THEO classId
    public List<User> getUsersByClassId(Integer classId) {
        String sql = "SELECT * FROM user WHERE classId = ?";
        return template.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(User.class));
    }
    
    

}
