<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="classes" />
</jsp:include>

  <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 50px;
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
            background: white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .btn {
            border-radius: 8px;
        }
        .header-title {
            margin-bottom: 20px;
        }
    </style>
<body>
    <div class="container">
        <div class="header-title text-center">
            <h2>danh sach lop</h2>
            <a href="cohort_create_form" class="btn btn-primary">Thêm mới lớp</a>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên lớp</th>
                    <th>idmajor</th>
                    <th>idcohort</th>
                    <th>Trang thai</th>
                    <th>ngay tao</th>
                    <th>Ngày cap nhat</th>
                 
                </tr>
            </thead>
            <tbody>
                <c:forEach var="classes" items="${list}">
                    <tr>
                        <td>${classes.id}</td>
                        <td>${classes.className}</td>
                        <td>${classes.majorId}</td>
                        <td>${classes.cohortId}</td>
                        <td>${classes.status}</td>
                        <td>${classes.createdDate}</td>
                        <td>${classes.updatedDate}</td>
                       
                        <td>
                            <a href="editclass/${classes.id}" class="btn btn-warning btn-sm">Chá»nh sá»­a</a>
                            <a href="deletecohort/${classes.id}" class="btn btn-danger btn-sm" 
                               onclick="return confirm('Bạn có chắc muốn xóa khoa này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>


<!-- Nội dung file footer -->
<jsp:include page="../footer.jsp" />
