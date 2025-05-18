<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác thực OTP - Trường Đại học Quốc Tế Bắc Hà</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f5f9ff;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }
      .header {
      background-color: #002147;
      color: white;
      padding: 20px 0;
      text-align: center;
    }

    .header h4 {
      margin: 0;
    }
        .verify-card {
            max-width: 500px;
            margin: 40px auto;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .university-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .university-header img {
            width: 80px;
            margin-bottom: 10px;
        }
        .btn-custom {
            min-width: 120px;
        }
        footer {
      font-size: 14px;
    }
    </style>
</head>
<body>
<!-- Header -->
  <div class="header">
    <h4>TRƯỜNG ĐẠI HỌC QUỐC TẾ BẮC HÀ</h4>
    <small>Hệ thống quản lý sinh viên</small>
  </div>
<div class="container">
    <div class="verify-card">
        <div class="university-header">
            <h5 class="mb-0">Trường Đại học Quốc Tế Bắc Hà</h5>
            <small class="text-muted">Hệ thống xác thực tài khoản</small>
        </div>
        <hr>
        <h5 class="text-center">Xác thực mã OTP</h5>
        <p class="text-center">Vui lòng nhập mã OTP 6 chữ số đã gửi tới email của bạn.</p>

        <form action="verifyOtp" method="POST">
            <div class="mb-3">
                <label for="otp" class="form-label">Mã OTP</label>
                <input type="text" class="form-control" id="otp" name="otp" maxlength="6" minlength="6" required placeholder="Nhập mã OTP">
                <div class="form-text">Mã này có hiệu lực trong 5 phút.</div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/forgotPassword" class="btn btn-outline-secondary btn-custom">Quay lại</a>
                <button type="submit" class="btn btn-primary btn-custom">Xác nhận</button>
            </div>
        </form>
    </div>
</div>
 <!-- Footer -->
  <footer class="text-center text-muted py-3">
    © 2025 Trường Đại học Quốc tế Bắc Hà. Mọi quyền được bảo lưu.
  </footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
