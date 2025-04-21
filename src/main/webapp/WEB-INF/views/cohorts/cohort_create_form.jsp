<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm khoá sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>

<body>

    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Thêm khoá sinh viên</h2>
            <form:form method="post" action="save" modelAttribute="cohort">
                <div class="mb-3">
                    <label for="cohortName" class="form-label">Tên sản phẩm:</label>
                    <form:input path="cohortName" class="form-control" placeholder="Nhập tên sản phẩm" />
                </div>
              <div class="mb-3 form-check">
        <form:checkbox path="status" class="form-check-input" id="status" />
        <label for="status" class="form-check-label">Sản phẩm đang hoạt động</label>
    </div>
              
                <button type="submit" class="btn btn-success">Lưu</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
