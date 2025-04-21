package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import beans.Product;


public class DaoProduct {
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public int save(Product p) {
        String sql = "INSERT INTO Products (tenSanPham, giaSanPham, soLuong, ngaySanXuat, moTa) VALUES (?, ?, ?, ?, ?)";
        return template.update(sql, p.getTenSanPham(), p.getGiaSanPham(), p.getSoLuong(), p.getNgaySanXuat(), p.getMoTa());
    }

    public int update(Product p) {
        String sql = "UPDATE Products SET tenSanPham = ?, giaSanPham = ?, soLuong = ?, ngaySanXuat = ?, moTa = ? WHERE idSanPham = ?";
        return template.update(sql, p.getTenSanPham(), p.getGiaSanPham(), p.getSoLuong(), p.getNgaySanXuat(), p.getMoTa(), p.getIdSanPham());
    }

    public int delete(int idSanPham) {
        String sql = "DELETE FROM Products WHERE idSanPham = ?";
        return template.update(sql, idSanPham);
    }

    public Product getProductById(int idSanPham) {
        String sql = "SELECT * FROM Products WHERE idSanPham = ?";
        return template.queryForObject(sql, new Object[] { idSanPham }, new BeanPropertyRowMapper<>(Product.class));
    }
    
    public List<Product> getProducts() {
        String sql = "SELECT * FROM Products";
        return template.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setIdSanPham(rs.getInt("idSanPham"));
                product.setTenSanPham(rs.getString("tenSanPham"));
                product.setGiaSanPham(rs.getBigDecimal("giaSanPham"));
                product.setSoLuong(rs.getInt("soLuong"));
                product.setNgaySanXuat(rs.getString("ngaySanXuat"));
                product.setMoTa(rs.getString("moTa"));
                return product;
            }
        });
    }
}

