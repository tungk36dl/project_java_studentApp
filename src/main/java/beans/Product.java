package beans;
import java.math.BigDecimal;


public class Product {
	private int idSanPham;
    private String tenSanPham;
    private BigDecimal giaSanPham;
    private int soLuong;
    private String ngaySanXuat;
    private String moTa;
	public int getIdSanPham() {
		return idSanPham;
	}
	public void setIdSanPham(int idSanPham) {
		this.idSanPham = idSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public BigDecimal getGiaSanPham() {
		return giaSanPham;
	}
	public void setGiaSanPham(BigDecimal giaSanPham) {
		this.giaSanPham = giaSanPham;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getNgaySanXuat() {
		return ngaySanXuat;
	}
	public void setNgaySanXuat(String ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	} 
    
    
    
}
