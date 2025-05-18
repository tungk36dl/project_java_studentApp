<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 700px;
            margin: 50px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: bold;
        }
        .btn {
            width: 100%;
        }
    </style>

    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Sửa thông tin các nhân</h2>
            <form:form method="post" action="${pageContext.request.contextPath}/user/saveInfo" modelAttribute="user">

                <!-- Giữ lại id của người dùng để gửi khi cập nhật -->
                <form:hidden path="id" />

               <!-- Username: cho phép sửa -->
				<div class="mb-3">
				    <label for="username" class="form-label">Tên đăng nhập:</label>
				    <form:input path="username" class="form-control" value="${user.username}" />
				</div>
				
				<!-- Gender: chỉ hiển thị, không cho chọn lại -->
				<div class="mb-3">
				    <label for="gender" class="form-label">Giới tính:</label>
				    <form:select path="gender" class="form-select" disabled="true">
				        <form:option value="" label="-- Chọn giới tính --" />
				        <form:option value="Nam" label="Nam" />
				        <form:option value="Nữ" label="Nữ" />
				        <form:option value="Khác" label="Khác" />
				    </form:select>
				</div>
				
				<!-- Avatar: chỉ hiển thị, không cho sửa -->
				<div class="mb-3">
				    <label for="avatar" class="form-label">Ảnh đại diện (URL):</label>
				    <form:input path="avatar" class="form-control" value="${user.avatar}" readonly="true" />
				</div>
				
				<!-- Email: cho phép sửa -->
				<div class="mb-3">
				    <label for="email" class="form-label">Email:</label>
				    <form:input path="email" type="email" class="form-control" value="${user.email}" placeholder="Nhập email" />
				</div>
				
				<!-- Identification Number: chỉ hiển thị -->
				<div class="mb-3">
				    <label for="identificationNumber" class="form-label">Số CCCD/CMND:</label>
				    <form:input path="identificationNumber" class="form-control" value="${user.identificationNumber}" readonly="true" />
				</div>
				
				<!-- Phone: cho phép sửa -->
				<div class="mb-3">
				    <label for="phone" class="form-label">Số điện thoại:</label>
				    <form:input path="phone" class="form-control" value="${user.phone}" placeholder="Nhập số điện thoại" />
				</div>
				
				<!-- Address: chỉ hiển thị -->
				<div class="mb-3">
				    <label for="address" class="form-label">Địa chỉ:</label>
				    <form:input path="address" class="form-control" value="${user.address}" readonly="true" />
				</div>

                <button type="submit" class="btn btn-warning">Cập nhật</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../footer.jsp" />
