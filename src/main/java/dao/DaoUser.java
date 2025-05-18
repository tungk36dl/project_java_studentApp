package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import beans.User;


@Repository
public class DaoUser {
	
	@Autowired
    private JdbcTemplate template;

    
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
        String sql = "INSERT INTO user (id, code, username, password, fullName, email, dateOfBirth, gender, avatar, identificationNumber, phone, address, classId, role, status, createdDate, updatedDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql,
        		user.getId(),
        		user.getCode(),
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
        String sql = "UPDATE user SET code = ?, username = ?, password = ?, fullName = ?, email = ?, dateOfBirth = ?, gender = ?, avatar = ?, identificationNumber = ?, phone = ?, address = ?, classId = ?, role = ?, status = ?, updatedDate = ? WHERE id = ?";
        return template.update(sql,
        		user.getCode(),
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
  
    
//    public Optional<User> getUserById(String id) {
//        String sql = "SELECT * FROM user WHERE id = ?";
//        try {
//            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
//            return Optional.of(user);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
    
    public User getUserById(String id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // hoặc throw custom exception nếu muốn
        }
    }
    
    public User getUserByCode(String code) {
        String sql = "SELECT * FROM user WHERE code = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (EmptyResultDataAccessException e) {
            return null; // hoặc throw custom exception nếu muốn
        }
    }




    // READ - get all
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return template.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setCode(rs.getString("code"));
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
    public List<User> getUsersByRole(String role) {
        String sql = "SELECT * FROM user WHERE role = ?";
        return template.query(sql, new Object[]{role}, new BeanPropertyRowMapper<>(User.class));
    }

    // LỌC THEO classId
    public List<User> getUsersByClassId(String classId) {
        String sql = "SELECT * FROM user WHERE classId = ?";
        return template.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(User.class));
    }
    
    
    // Đếm số lượng phần tử
    public int countFillterUser(String classId, String role) {
    	StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM user WHERE 1=1 ");
    	List<Object> params = new ArrayList<>();
    	
    	if(classId != null) {
    		sql.append("AND classId = ?");
    		params.add(classId);
    	}
    	if(role != null) {
    		sql.append("AND role = ?");
    		params.add(role);
    	}
    	return template.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
    
    public List<User> filterUsers(String classId, String role) {
        String sql = "SELECT * FROM user WHERE classId = ? AND role = ? ORDER BY id ASC";
        return template.query(sql, new Object[]{classId, role}, new BeanPropertyRowMapper<>(User.class));
    }
    
    
    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            // Trả về user nếu tìm thấy
            return template.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            // Trả về null nếu không tìm thấy bản ghi
            return null;
        }
    }

    
    // Cập nhật mật khẩu của người dùng theo email
//    public int updatePassword(String email, String newPassword) {
//        String encodedPassword = passwordEncoder.encode(newPassword); // Mã hóa mật khẩu trước
//        String sql = "UPDATE user SET password = ? WHERE email = ?";
//        return template.update(sql, encodedPassword, email);
//    }

    

}
