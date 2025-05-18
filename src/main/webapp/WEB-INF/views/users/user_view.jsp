<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>

<!-- Bootstrap 5.3.0 & Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



<div class="container mt-5">
    <div class="text-center mb-4">
        <h2><i class="bi bi-person-gear me-1"></i> Danh Sách Người Dùng</h2>
        <a href="${pageContext.request.contextPath}/user/create" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> Thêm mới người dùng
        </a>
    </div>

    <c:if test="${not empty filter}">
        <div class="alert alert-info text-center">${filter}</div>
    </c:if>

    <!-- Import Excel -->
    <div class="mb-4 text-center">
        <form action="${pageContext.request.contextPath}/user/import" method="post" enctype="multipart/form-data" class="d-inline-block">
            <input type="file" name="file" accept=".xlsx" required />
            <button type="submit" class="btn btn-success">
                <i class="bi bi-file-earmark-excel"></i> Import từ Excel
            </button>
        </form>
    <c:if test="${not empty messageImport}">
        <div class="alert alert-info text-center">${messageImport}</div>
    </c:if>
    </div>

    <!-- Tìm kiếm và lọc -->
    <form action="${pageContext.request.contextPath}/user/list" method="post" class="row g-3 align-items-end mb-4">
       <div class="col-md-4">
    <label class="form-label d-none d-md-block">&nbsp;</label> <!-- để căn đều chiều cao -->
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-search"></i></span>
        <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm tên, email, username"
               value="${param.keyword}"/>
    </div>
</div>

        <div class="col-md-3">
            <label class="form-label">Lọc theo lớp:</label>
            <select name="classId" class="form-select">
                <option value="">Tất cả</option>
                <c:forEach var="cls" items="${classes}">
                    <option value="${cls.id}" ${param.classId == cls.id ? 'selected' : ''}>${cls.className}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <label class="form-label">Lọc theo role:</label>
            <select name="role" class="form-select">
                <option value="">Tất cả</option>
                <c:forEach var="r" items="${roles}">
                    <option value="${r}" ${param.role == r ? 'selected' : ''}>${r}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">
                <i class="bi bi-funnel-fill"></i> Lọc
            </button>
        </div>
    </form>

    <!-- Bảng danh sách -->
    <div class="table-responsive">
    <div style="min-width: 1200px;">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark text-center">
                <tr>
                	<th>STT</th>
                    <th>Mã SV/GV</th>
                    <th>Avatar</th>
                    <th>Username</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>SĐT</th>
                    <th>Giới tính</th>
                    <th>Ngày sinh</th>
                    <th>Lớp</th>
                    <th>Role</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Ngày sửa</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${list}" varStatus="loop">
                    <tr>
                    	<td> ${loop.index + 1} </td>
                        <td>${user.code}</td>
                        <td class="text-center">
                            <c:if test="${not empty user.avatar}">
                                <!--<img src="${pageContext.request.contextPath}/images/avatars/${user.avatar}"
                                     alt="Avatar" class="avatar-img"/>   -->
                                     
                                     <img src="${user.avatar}" width="20"
                                     alt="Avatar" class="avatar-img"/>
                            </c:if>
                        </td>
                        <td>${user.username}</td>
                        <td title="${user.fullName}">${user.fullName}</td>
                        <td title="${user.email}">${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.gender}</td>
                        <td><fmt:formatDate value="${user.dateOfBirth}" pattern="dd-MM-yyyy"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty user.classId}">
                                    ${user.classId}
                                </c:when>
                                <c:otherwise>Không có</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${user.role}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.status}">
                                    <span class="badge bg-success">
                                        <i class="bi bi-check-circle"></i> Hoạt động
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">
                                        <i class="bi bi-pause-circle"></i> Ngừng 
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${user.createdDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td><fmt:formatDate value="${user.updatedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td>
                            <div class="d-flex flex-wrap gap-1 justify-content-center">
                                <a href="${pageContext.request.contextPath}/user/edit/${user.id}"
                                   class="btn btn-warning btn-sm">
                                    <i class="bi bi-pencil"></i> Sửa
                                </a>
                                <a href="${pageContext.request.contextPath}/user/delete/${user.id}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn có chắc muốn xóa người dùng này?');">
                                    <i class="bi bi-trash"></i> Xóa
                                </a>
                                <c:if test="${user.role == 'STUDENT'}">
                                    <a href="${pageContext.request.contextPath}/score/scoreByStudentFromPDT/${user.id}"
                                       class="btn btn-primary btn-sm">
                                        <i class="bi bi-card-checklist"></i> Xem điểm
                                    </a>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp" />
