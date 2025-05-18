<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="subjectDetail" />
</jsp:include>

<!-- Bootstrap & Icon -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
    body {
        background-color: #f1f3f5;
        font-family: 'Segoe UI', 'Open Sans', sans-serif;
    }

    .container {
        margin-top: 40px;
        max-width: 100%;
    }

    .card {
        background-color: #ffffff;
        border-radius: 12px;
        padding: 25px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    }

    h4 {
        font-weight: 600;
        border-left: 5px solid #dc3545;
        padding-left: 10px;
        color: #333;
    }

    .btn-primary {
        background-color: #007bff;
        border: none;
    }

    .btn-primary:hover {
        background-color: #0069d9;
    }

    .btn-warning {
        background-color: #ffc107;
        border: none;
        color: #212529;
    }

    .btn-warning:hover {
        background-color: #e0a800;
    }

    .btn-danger {
        background-color: #dc3545;
        border: none;
    }

    .btn-danger:hover {
        background-color: #c82333;
    }

    .table thead {
        background-color: #007bff;
        color: white;
    }

    .table th, .table td {
    vertical-align: middle;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    padding: 8px;
}


    .badge-success {
        background-color: #28a745;
    }

    .badge-danger {
        background-color: #dc3545;
    }
</style>

<div class="container">
    <div class="card">
        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4><i class="bi bi-card-list me-1"></i> Quản lý chi tiết môn học</h4>
            <a href="subjectDetail_create_form" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Thêm mới
            </a>
        </div>

        <!-- Table -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên lớp</th>
                        <th>Môn học</th>
                        <th>Giảng viên</th>
                        <th>Kỳ học</th>
                        <th>Tín chỉ</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                        <th>Ngày cập nhật</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="subjectDetail" items="${list}">
                        <tr>
                            <td>${subjectDetail.id}</td>
                            <td title="${subjectDetail.className}">${subjectDetail.className}</td>
                            <td title="${subjectDetail.subjectName}">${subjectDetail.subjectName}</td>
                            <td title="${subjectDetail.teacherName}">${subjectDetail.teacherName}</td>
                            <td>${subjectDetail.semester}</td>
                            <td>${subjectDetail.credit}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${subjectDetail.status}">
                                        <span class="badge bg-success">
                                            <i class="bi bi-check-circle"></i> Hoạt động
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">
                                            <i class="bi bi-pause-circle"></i> Ngừng
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate value="${subjectDetail.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td><fmt:formatDate value="${subjectDetail.updatedDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <div class="d-flex gap-1 justify-content-center flex-wrap">
                                    <c:if test="${subjectDetail.status}">
                                        <a href="${pageContext.request.contextPath}/score/inputScore/${subjectDetail.id}" class="btn btn-primary btn-sm">
                                            <i class="bi bi-pencil-square"></i> Nhập điểm
                                        </a>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/subjectDetail/editSubjectDetail/${subjectDetail.id}" class="btn btn-warning btn-sm">
                                        <i class="bi bi-pencil"></i> Sửa
                                    </a>
                                    <a href="${pageContext.request.contextPath}/subjectDetail/deletesubjectDetail/${subjectDetail.id}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa chi tiết môn học này?');">
                                        <i class="bi bi-trash"></i> Xóa
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp" />
