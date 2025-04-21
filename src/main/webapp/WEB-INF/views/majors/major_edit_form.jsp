<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="major" />
</jsp:include>

body {
            background-color: #f8f9fa;
        }
        .card {
            margin: 50px auto;
            max-width: 600px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
        .form-control, .btn {
            border-radius: 8px;
        }
        .btn {
            padding: 10px 20px;
        }
<div class="container">
    <div class="card">
        <div class="card-header bg-primary text-white text-center">
            <h4>Chỉnh sửa Major</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/major/editsave" method="post">
                <input type="hidden" name="id" value="${major.id}">

                <div class="mb-3">
                    <label for="majorName" class="form-label">Tên Major:</label>
                    <input type="text" class="form-control" id="majorName" name="majorName"
                           value="${major.majorName}" placeholder="Nhập tên Major" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="form-control" name="status">
                        <option value="true" ${major.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!major.status ? 'selected' : ''}>Ngừng hoạt động</option>
                    </select>
                </div>

                <!-- Hiển thị ô trống, chỉ để nhìn -->
                <div class="mb-3">
                    <label class="form-label">Ngày tạo:</label>
                    <input type="text" class="form-control" value="" placeholder="" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Ngày sửa:</label>
                    <input type="text" class="form-control" value="" placeholder="" readonly>
                </div>


                <div class="text-center">
                    <button type="submit" class="btn btn-success">Lưu</button>
                    <a href="${pageContext.request.contextPath}/major/major_view" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Nhúng nội dung file footer -->
<jsp:include page="../footer.jsp" />
