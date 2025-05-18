<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="subject" />
</jsp:include>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<div class="container">
    <div class="card">
        <!-- Tiêu đề và nút thêm -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="mb-0">
                <i class="bi bi-journal-bookmark me-1"></i> Danh sách môn học
            </h4>
            <a href="subject_create_form" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Thêm môn học
            </a>
        </div>

        <!-- Bảng dữ liệu -->
        <table class="table table-bordered table-hover text-center">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Mã môn học </th>
                    <th>Tên môn học</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Ngày sửa</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="subject" items="${list}">
                    <tr>
                        <td>${subject.id}</td>
                        <td>${subject.code}</td>
                        <td title="${subject.name}">${subject.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${subject.status}">
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
                        <td><fmt:formatDate value="${subject.createdDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td><fmt:formatDate value="${subject.updatedDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td>
                            <a href="editsubject/${subject.id}" class="btn btn-warning btn-sm">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                            <a href="deletesubject/${subject.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Bạn có chắc muốn xóa môn học này?');">
                                <i class="bi bi-trash"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp" />
