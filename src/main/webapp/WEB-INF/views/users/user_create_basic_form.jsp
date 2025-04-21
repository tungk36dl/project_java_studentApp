<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm người dùng mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
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
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Thêm người dùng mới</h2>
            <form:form method="post" action="saveBasic" modelAttribute="user">

                <form:hidden path="id" />

                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập:</label>
                    <form:input path="username" class="form-control" placeholder="Nhập tên đăng nhập" />
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu:</label>
                    <form:password path="password" class="form-control" placeholder="Nhập mật khẩu" />
                </div>


                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <form:input path="email" type="email" class="form-control" placeholder="Nhập email" />
                </div>

				<div class="mb-3">
				    <label for="role" class="form-label">Vai trò:</label>
				    <form:select path="role" class="form-control">
				        <form:options items="${roles}" />
				    </form:select>
				</div>


                <!-- Nếu status không còn dùng thì bạn có thể xóa đoạn này -->
                <div class="mb-3 form-check">
                    <form:checkbox path="status" class="form-check-input" id="status" />
                    <label for="status" class="form-check-label">Người dùng đang hoạt động</label>
                </div>

                <button type="submit" class="btn btn-success">Lưu</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
