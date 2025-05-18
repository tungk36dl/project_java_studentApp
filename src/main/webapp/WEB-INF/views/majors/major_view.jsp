<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Gọi header -->
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="major" />
</jsp:include>


<!-- Bootstrap 5.3.0 từ CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



<div class="container">
    <div class="card">
        <!-- Tiêu đề và nút thêm -->
        <div class="d-flex justify-content-between align-items-center mb-3">
<h4 class="mb-0">
    <i class="bi bi-diagram-3 me-1"></i> Danh sách chuyên ngành
</h4>
            <!-- Nút Thêm mới với icon -->
<a href="major_create_form" class="btn btn-primary">
    <i class="bi bi-plus-circle"></i> Thêm mới
</a>
        </div>

        <!-- Bảng dữ liệu -->
        <table class="table table-bordered table-hover text-center">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên chuyên ngành</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Ngày sửa</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="major" items="${list}">
                    <tr>
                        <td>${major.id}</td>
                        <td>${major.majorName}</td>
                       <td>
    <c:choose>
        <c:when test="${major.status}">
            <span class="badge bg-success">
                <i class="bi bi-check-circle"></i> Đang hoạt động
            </span>
        </c:when>
        <c:otherwise>
            <span class="badge bg-secondary">
                <i class="bi bi-pause-circle"></i> Ngừng hoạt động
            </span>
        </c:otherwise>
    </c:choose>
</td>

                        <td><fmt:formatDate value="${major.createdDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td><fmt:formatDate value="${major.updatedDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td>
<!-- Nút Sửa với icon -->
<a href="editmajor/${major.id}" class="btn btn-warning btn-sm">
    <i class="bi bi-pencil"></i> Sửa
</a>                            <!-- Nút Xóa với icon -->
<a href="deletemajor/${major.id}" class="btn btn-danger btn-sm"
   onclick="return confirm('Bạn có chắc muốn xóa chuyên ngành này?');">
   <i class="bi bi-trash"></i> Xóa
</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Gọi footer -->
<jsp:include page="../footer.jsp" />
