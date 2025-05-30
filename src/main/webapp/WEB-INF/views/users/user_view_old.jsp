<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>
 <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 50px;
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
            background: white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .btn {
            border-radius: 8px;
        }
        .header-title {
            margin-bottom: 20px;
        }
        .avatar-img {
            width: 40px;
            height: 40px;
            object-fit: cover;
            border-radius: 50%;
        }
    </style>
<div class="container">
    <div class="header-title text-center">
        <h2>Danh Sách Người Dùng</h2>
        <a href="${pageContext.request.contextPath}/user/create" class="btn btn-primary">Thêm mới người dùng</a>
    </div>

    <c:if test="${not empty messageImport}">
        <div class="alert alert-info text-center">${messageImport}</div>
    </c:if>
    
    <c:if test="${not empty filter}">
        <div class="alert alert-info text-center">${filter}</div>
    </c:if>

	<div class="mb-4 text-center">
	    <form action="${pageContext.request.contextPath}/user/import" method="post" enctype="multipart/form-data">
	        <input type="file" name="file" accept=".xlsx" required />
	        <button type="submit" class="btn btn-success">Import từ Excel</button>
	    </form>
	</div>
	
	<div class="mb-4">
    <form action="${pageContext.request.contextPath}/user/list" method="post" class="row g-3 align-items-center">
        <div class="col-auto">
            <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm tên, email, username"
                   value="${param.keyword}"/>
        </div>

        <div class="col-auto">
            <label for="classId" class="col-form-label">Lọc theo lớp:</label>
        </div>
        <div class="col-auto">
            <select name="classId" id="classId" class="form-select">
                <option value="">Tất cả</option>
                <c:forEach var="cls" items="${classes}">
                    <option value="${cls.id}" ${param.classId == cls.id ? 'selected' : ''}>${cls.className}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-auto">
            <label for="role" class="col-form-label">Lọc theo role:</label>
        </div>
        <div class="col-auto">
            <select name="role" id="role" class="form-select">
                <option value="">Tất cả</option>
                <c:forEach var="r" items="${roles}">
                    <option value="${r}" ${param.role == r ? 'selected' : ''}>${r}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Lọc / Tìm</button>
        </div>
    </form>
</div>

	

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Avatar</th>
            <th>Username</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>SĐT</th>
            <th>Giới tính</th>
            <th>Lớp</th>
            <th>Role</th>
            <th>Trạng thái</th>
            <th>Ngày tạo</th>
            <th>Ngày sửa</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${list}">
            <tr>
                <td>${user.id}</td>
                <td>
                    <c:if test="${not empty user.avatar}">
                        <img src="${pageContext.request.contextPath}/images/avatars/${user.avatar}" alt="Avatar" class="avatar-img"/>
                    </c:if>
                </td>
                <td>${user.username}</td>
                <td>${user.fullName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.gender}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty user.classId}">
                            ${user.classId}
                        </c:when>
                        <c:otherwise>
                            Không có
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${user.role}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.status}">
                            <span class="text-success fw-semibold">Đang hoạt động</span>
                        </c:when>
                        <c:otherwise>
                            <span class="text-danger fw-semibold">Ngừng hoạt động</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><fmt:formatDate value="${user.createdDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                <td><fmt:formatDate value="${user.updatedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/user/edit/${user.id}" class="btn btn-warning btn-sm">Chỉnh sửa</a>
                    <a href="${pageContext.request.contextPath}/user/delete/${user.id}" class="btn btn-danger btn-sm"
                       onclick="return confirm('Bạn có chắc muốn xóa người dùng này?');">Xóa</a>
					<c:if test="${user.role == 'STUDENT'}">
					    <a href="${pageContext.request.contextPath}/score/scoreByStudentFromPDT/${user.id}" class="btn btn-primary btn-sm">Xem điểm sinh viên</a>
					</c:if>
                       
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

<jsp:include page="../footer.jsp" />


