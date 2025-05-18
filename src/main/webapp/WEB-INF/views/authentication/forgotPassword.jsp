<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Forgot-Password</title>

  <!-- Bootstrap 5.3.0 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Google Font -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

  <style>
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #f5f8fc;
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

    .card {
      border: none;
      box-shadow: 0 0 10px rgba(0,0,0,0.08);
    }

    .btn-primary {
      background-color: #002147;
      border-color: #002147;
    }

    .btn-primary:hover {
      background-color: #001530;
    }

    .btn-danger {
      background-color: #c0392b;
      border-color: #c0392b;
    }

    .btn-danger:hover {
      background-color: #a93226;
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

  <!-- Main content -->
  <div class="container mt-5 mb-5">
    <div class="row justify-content-center">
      <div class="col-md-6 col-lg-5">
        <div class="card p-4">
          <h5 class="text-center text-primary mb-3">Quên Mật Khẩu</h5>
          <p class="text-center text-muted mb-4">Vui lòng nhập địa chỉ email bạn đã đăng ký để nhận mã xác thực (OTP).</p>

          <form action="forgotPassword" method="POST">
            <div class="mb-3">
              <label for="email" class="form-label">Địa chỉ Email</label>
              <input type="email" class="form-control" id="email" name="email" required placeholder="example@gmail.com">
               <c:if test="${not empty message}">
			        <div class="alert alert-warning text-center">${message}</div>
			    </c:if>
            </div>

            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary">Gửi mã OTP</button>
              <a href="${pageContext.request.contextPath}/login" class="btn btn-danger">Quay lại đăng nhập</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer class="text-center text-muted py-3">
    © 2025 Trường Đại học Quốc tế Bắc Hà. Mọi quyền được bảo lưu.
  </footer>

</body>
</html>
