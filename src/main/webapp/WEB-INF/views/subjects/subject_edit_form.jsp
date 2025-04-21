<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa môn học</title>
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
            <h4>Chỉnh sửa Môn học</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/subject/editsave" method="post">
                <input type="hidden" name="id" value="${subject.id}">

                <div class="mb-3">
                    <label for="name" class="form-label">Tên môn học:</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${subject.name}" placeholder="Nhập tên môn học" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="form-control" name="status">
                        <option value="true" ${subject.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!subject.status ? 'selected' : ''}>Ngừng hoạt động</option>
                    </select>
                </div>

               <div class="mb-3">
                    <label class="form-label">Ngày tạo:</label>
                    <input type="text" class="form-control" value="${subject.createdDate != null ? subject.createdDate : 'N/A'}" readonly>
               </div>

               <div class="mb-3">
                    <label class="form-label">Ngày sửa:</label>
                    <input type="text" class="form-control" value="${subject.updatedDate != null ? subject.updatedDate : 'N/A'}" readonly>       
               </div>


                <div class="text-center">
                    <button type="submit" class="btn btn-success">Lưu</button>
                    <a href="${pageContext.request.contextPath}/subject/subject_view" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
