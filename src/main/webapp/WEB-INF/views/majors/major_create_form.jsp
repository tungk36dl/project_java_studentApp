<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="major" />
</jsp:include>

<style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: bold;
        }
        .btn {
            width: 100%;
        }
    </style>
    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Thêm Major mới</h2>
           <form:form method="post" action="save" modelAttribute="major">


                <div class="mb-3">
                    <label for="name" class="form-label">Tên Major:</label>
                    <form:input path="name" class="form-control" placeholder="Nhập tên Major" />
                </div>
                
                <div class="mb-3 form-check">
			        <form:checkbox path="status" class="form-check-input" id="status" />
			        <label for="status" class="form-check-label">Major đang hoạt động</label>
			    </div>
                
                <button type="submit" class="btn btn-success">Lưu</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    
    
<!-- Nhúng nội dung file footer -->
<jsp:include page="../footer.jsp" />
