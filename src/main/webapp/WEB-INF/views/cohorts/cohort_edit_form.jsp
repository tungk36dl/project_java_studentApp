<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa khoá</title>
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
      .form-check-input {
    vertical-align: middle;
    margin-right: 10px;
}

.form-check-label {
    display: inline-block;
    vertical-align: middle;
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
                <form action="/cohort/cohort_view" method="post">
                    <input type="hidden" name="id" value="${cohort.id}">

                    <div class="mb-3">
                        <label for="cohortName" class="form-label">Tên khoá:</label>
                        <input type="text" class="form-control" id="cohortName" name="cohortName" 
                               value="${cohort.cohortName}" placeholder="Nhập khoá" required>
                    </div>
					<div class="mb-3 form-check">
    					<input type="checkbox" class="form-check-input" id="status" name="status" ${cohort.status ? 'checked' : ''}>
    					<label for="status" class="form-check-label">Sản phẩm đang hoạt động</label>
						</div>

   		               

                    <div class="text-center">
                        <button type="submit" class="btn btn-success">Lưu</button>
                        <a href="viewcohort" class="btn btn-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
