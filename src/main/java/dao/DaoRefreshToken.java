package dao;

import beans.RefreshToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


@Repository
public class DaoRefreshToken {

	@Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void save(RefreshToken token) {
        String sql = "INSERT INTO refreshToken (id, userId, token, expiryDate, status, createdDate, updatedDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                token.getId(),
                token.getUserId(),
                token.getToken(),
                new java.sql.Timestamp(token.getExpiryDate().getTime()),
                token.isStatus(),
                new java.sql.Timestamp(token.getCreatedDate().getTime()),
                new java.sql.Timestamp(token.getUpdatedDate().getTime()));
    }

    public RefreshToken findByToken(String tokenStr) {
        String sql = "SELECT * FROM refreshtoken WHERE token = ? AND status = true";

        return jdbcTemplate.queryForObject(sql, new Object[]{tokenStr}, (rs, rowNum) -> mapRow(rs));
    }

    private RefreshToken mapRow(ResultSet rs) throws SQLException {
        RefreshToken token = new RefreshToken();
        token.setId(rs.getString("id"));
        token.setUserId(rs.getString("userId"));
        token.setToken(rs.getString("token"));
        token.setExpiryDate(rs.getTimestamp("expiryDate"));
        token.setStatus(rs.getBoolean("status"));
        token.setCreatedDate(rs.getTimestamp("createdDate"));
        token.setUpdatedDate(rs.getTimestamp("updatedDate"));
        return token;
    }

    public void revokeToken(String tokenStr) {
        String sql = "UPDATE refreshtoken SET status = false, updatedDate = ? WHERE token = ?";
        jdbcTemplate.update(sql, new Date(), tokenStr);
    }
    
    public void deleteToken(String tokenStr) {
    	String sql = "DELETE FROM refreshtoken where token = ?";
    	jdbcTemplate.update(sql, tokenStr);
    }
}
