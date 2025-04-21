package beans;

import java.util.Date;
import utils.IdUtil;

public class Major {
    private String id;
    private String majorName;
    private boolean status;
    private Date createdDate;
    private Date updatedDate;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Major(String id, String name, boolean status, Date createdDate, Date updatedDate) {
        super();
        this.id = IdUtil.generateId(); // Tạo id tự động
        this.majorName = name;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Major() {
        // Constructor mặc định bắt buộc cho Spring, JSP hoặc BeanUtils
    }
}