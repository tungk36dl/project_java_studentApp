<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authorization</title>
  <style>
        body {
            background-color: #f8f9fa;
            text-align: center;
            padding-top: 100px;
        }
        h1 {
            font-size: 100px;
            color: #dc3545;
        }
        h3 {
            color: #6c757d;
        }
        a {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>403</h1>
    <h3>Banj không được cấp quyền.</h3>
    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Quay về trang chủ</a>
</body>
</html>