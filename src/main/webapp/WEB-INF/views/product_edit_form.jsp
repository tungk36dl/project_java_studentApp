<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
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
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header bg-primary text-white text-center">
                <h4>Chỉnh sửa sản phẩm</h4>
            </div>
            <div class="card-body">
                <form action="/TranMinhDuc/editsave" method="post">
                    <input type="hidden" name="idSanPham" value="${product.idSanPham}">

                    <div class="mb-3">
                        <label for="tenSanPham" class="form-label">Tên sản phẩm:</label>
                        <input type="text" class="form-control" id="tenSanPham" name="tenSanPham" 
                               value="${product.tenSanPham}" placeholder="Nhập tên sản phẩm" required>
                    </div>

                    <div class="mb-3">
                        <label for="giaSanPham" class="form-label">Giá sản phẩm:</label>
                        <input type="number" step="0.01" class="form-control" id="giaSanPham" name="giaSanPham" 
                               value="${product.giaSanPham}" placeholder="Nhập giá sản phẩm" required>
                    </div>

                    <div class="mb-3">
                        <label for="soLuong" class="form-label">Số lượng:</label>
                        <input type="number" class="form-control" id="soLuong" name="soLuong" 
                               value="${product.soLuong}" placeholder="Nhập số lượng sản phẩm" required>
                    </div>

                    <div class="mb-3">
                        <label for="ngaySanXuat" class="form-label">Ngày sản xuất:</label>
                        <input type="date" class="form-control" id="ngaySanXuat" name="ngaySanXuat" 
                               value="${product.ngaySanXuat}">
                    </div>

                    <div class="mb-3">
                        <label for="moTa" class="form-label">Mô tả:</label>
                        <textarea class="form-control" id="moTa" name="moTa" rows="4" placeholder="Nhập mô tả sản phẩm">${product.moTa}</textarea>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-success">Lưu</button>
                        <a href="viewproducts" class="btn btn-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>