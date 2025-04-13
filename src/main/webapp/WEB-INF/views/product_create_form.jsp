<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm sản phẩm mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
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
            <h2 class="text-center text-primary mb-4">Thêm sản phẩm mới</h2>
            <form:form method="post" action="save" modelAttribute="product">
                <div class="mb-3">
                    <label for="tenSanPham" class="form-label">Tên sản phẩm:</label>
                    <form:input path="tenSanPham" class="form-control" placeholder="Nhập tên sản phẩm" />
                </div>
                <div class="mb-3">
                    <label for="giaSanPham" class="form-label">Giá sản phẩm:</label>
                    <form:input path="giaSanPham" class="form-control" placeholder="Nhập giá sản phẩm" />
                </div>
                <div class="mb-3">
                    <label for="soLuong" class="form-label">Số lượng:</label>
                    <form:input path="soLuong" class="form-control" placeholder="Nhập số lượng" />
                </div>
                <div class="mb-3">
                    <label for="ngaySanXuat" class="form-label">Ngày sản xuất:</label>
                    <form:input path="ngaySanXuat" type="date" class="form-control" />
                </div>
                <div class="mb-3">
                    <label for="moTa" class="form-label">Mô tả:</label>
                    <form:textarea path="moTa" class="form-control" placeholder="Nhập mô tả sản phẩm" rows="4"></form:textarea>
                </div>
                <button type="submit" class="btn btn-success">Lưu</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>