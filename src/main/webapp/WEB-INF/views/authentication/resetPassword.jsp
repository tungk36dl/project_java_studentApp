<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đặt Lại Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
    <style>
        body {
            background-color: #eee;
            font-family: "Rubik", Helvetica, Arial, sans-serif;
            font-size: 14px;
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
        .reset-password-form {
            background-color: #fff;
            padding: 20px;
            border: 1px solid #dfdfdf;
            margin-top: 50px;
        }
        .form-control:focus {
            border-color: #76b7e9;
        }
        .btn {
            font-size: 14px;
        }
        .alert {
            display: none;
        }
        .form-group {
            margin-bottom: 15px; /* Thêm khoảng cách giữa các ô nhập */
        }
        .btn {
            margin-top: 15px; /* Thêm khoảng cách giữa nút và ô nhập */
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
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
            <div class="reset-password-form">
                <h2>Đặt Lại Mật Khẩu</h2>
                <form id="resetPasswordForm" action="${pageContext.request.contextPath}/resetPassword" method="POST">
                    <div class="form-group">
                        <label for="password">Mật khẩu mới</label>
                        <input type="password" class="form-control" id="password" name="password" required />
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required />
                    </div>
                    
                    <!-- Hiển thị thông báo lỗi nếu có -->
                    <div class="alert alert-danger" id="errorMessage"></div>
                    <div class="alert alert-success" id="successMessage"></div>
                    
                    <button type="submit" class="btn btn-success">Đặt lại mật khẩu</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal Success -->
<div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="successModalLabel">Thành Công</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Mật khẩu của bạn đã được đặt lại thành công!
      </div>
    </div>
  </div>
</div>
 <!-- Footer -->
  <footer class="text-center text-muted py-3">
    © 2025 Trường Đại học Quốc tế Bắc Hà. Mọi quyền được bảo lưu.
  </footer>

<script>
    // JavaScript code to handle form submission and validation
    $("form").submit(function(event) {
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        
        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu không khớp
        if (password !== confirmPassword) {
            event.preventDefault(); // Ngừng form gửi đi
            $("#errorMessage").text("Mật khẩu và xác nhận mật khẩu không khớp.").show();
        } else {
            $("#errorMessage").hide();
            
            // Hiển thị modal thông báo thành công
            var successModal = new bootstrap.Modal(document.getElementById('successModal'));
            successModal.show();
            
            // Sau 3 giây tự động chuyển hướng về trang login
            setTimeout(function () {
            	window.location.href = "<c:url value='/login'/>";
            }, 1000);
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
