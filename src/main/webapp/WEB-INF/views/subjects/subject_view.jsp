<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
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
    <div class="container">
        <div class="header-title text-center">
            <h2>Danh Sách Môn học</h2>
            <a href="subject_create_form" class="btn btn-primary">Thêm mới môn học</a>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên tên</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Ngày sửa</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="subject" items="${list}">
                    <tr>
                        <td>${subject.id}</td>
                        <td>${subject.name}</td>
                        <td>
                           <c:choose>
                               <c:when test="${subject.status}">
                                    <span class="text-success fw-semibold">Đang hoạt động</span>
                               </c:when>
                               <c:otherwise>
                                     <span class="text-danger fw-semibold">Ngừng hoạt động</span>
                               </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${subject.createdDate}</td>
                        <td>${subject.updatedDate}</td>
                        <td>
                            <a href="editsubject/${subject.id}" class="btn btn-warning btn-sm">Chỉnh sửa</a>
                            <a href="deletesubject/${subject.id}" class="btn btn-danger btn-sm" 
                               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<!-- NhÃºng ná»i dung file footer -->
<jsp:include page="../footer.jsp" />
