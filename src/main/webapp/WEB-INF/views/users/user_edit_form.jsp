<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>

<!-- Bootstrap 5.3.0 từ CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


    <style>
    body {
        background-color: #ffffff;
        color: #333;
    }

   .form-container-my {
        max-width: 600px;
        margin: 50px auto;
        padding: 30px;
        background-color: #ffffff;
        border-radius: 10px;
        border: 2px solid #6fd9ee;
        box-shadow: 0 4px 12px rgba(56, 58, 141, 0.2);
    }

    .form-title {
    	grid-column: span 2;
    	text-align: center;
    	font-size: 28px;
    	padding-bottom: 20px;
    	color: #ffffff;
    	background-color: #04bfe4;
    	border-top-left-radius: 7px;
    	border-top-right-radius: 7px;
    	padding: 15px 0;
    	margin: -30px -30px 30px -30px; 
}


    .form-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 20px 30px;
    }

    .form-label {
        font-weight: bold;
        margin-left: 3px;
        
    }
    
    .choise {
    	 width: 100%;
    padding: 0.5rem 0.75rem;
    font-size: 1rem;
       border: var(--bs-border-width) solid var(--bs-border-color);      
    border-radius: 0.375rem;
    background-color: #fff;
    appearance: auto;             
    -webkit-appearance: auto;
    -moz-appearance: auto;          
    transition: border-color 0.01s ease-in-out;
   
    }

    .button {
        width: 100% ;
        border-color: #198754;
        border: none;
    	border-radius: 4px;
        color: white ;
        padding: 4px  12px 4px 12px;
        font-size: 18px ;
        text-decoration: none;
        
    }

    .button-submit:hover {
    	color: #c1e9f1;
        background-color: #239f66 ;
        border-color: #5dd49e;
    }
    .button-secondary:hover {
    	color: #c1e9f1;
        background-color: #88a6ac ;
        border-color: #c1e9f1;
    }

   
   	.full-width {
        grid-column: span 2;
    }

    .text-danger {
        font-size: 0.9rem;
    }
    
   .button-submit {
   		background-color: #198754 ;
    
	}
	.button-secondary {
    background-color: #7f8f93;
    margin-top: 5px;
    display: block;
    width: 100%;
     text-align: center;
}
 
    .input-error {
    border: 2px solid red !important;
}
   
    
    
</style>


    <div class="container-my">
        <div class="form-container-my">
            <div class="form-title">
            <i class="bi bi-tools me-2"></i>Chỉnh sửa người dùng
			</div>
            <form:form method="post" action="${pageContext.request.contextPath}/user/update" modelAttribute="user">

                <!-- Giữ lại id của người dùng để gửi khi cập nhật -->
                <form:hidden path="id" />
			<div class= "form-grid">
				<div class="mb-3">
                    <label for="code" class="form-label">Mã sinh viên / giảng viên:</label>
                    <form:input path="code" class="form-control" readonly="true" value="${user.code}" />
                      <c:if test="${codeExist == true}">
                        <p class="text-danger">Mã đã tồn tại</p>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập:</label>
                    <form:input path="username" class="form-control" readonly="true" value="${user.username}" />
                     <c:if test="${userNameExist == true}">
                        <p class="text-danger">Username đã tồn tại</p>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu:</label>
                    <div class="input-group">
    					<form:password path="password" class="form-control" placeholder="Nhập mật khẩu" id="passwordField" />
    						<span class="input-group-text" onclick="togglePassword()">
        						<i class="bi bi-eye" id="toggleIcon"></i>
   				 			</span>
					</div>
                </div>

                <div class="mb-3">
                    <label for="fullName" class="form-label">Họ tên:</label>
                    <form:input path="fullName" class="form-control" value="${user.fullName}" placeholder="Nhập họ tên" />
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <form:input path="email" type="email" class="form-control" value="${user.email}" placeholder="Nhập email" />
                </div>

                <div class="mb-3">
                    <label for="dateOfBirth" class="form-label">Ngày sinh:</label>
                    <form:input path="dateOfBirth" type="date" class="form-control" value="${user.dateOfBirth}" />
                </div>

                <div class="mb-3">
                    <label for="gender" class="form-label">Giới tính:</label>
                    <form:select path="gender" class="form-select">
                        <form:option value="" label="-- Chọn giới tính --" />
                        <form:option value="Nam" label="Nam" />
                        <form:option value="Nữ" label="Nữ" />
                        <form:option value="Khác" label="Khác" />
                    </form:select>
                </div>

                <div class="mb-3">
                    <label for="avatar" class="form-label">Ảnh đại diện (URL):</label>
                    <form:input path="avatar" class="form-control" value="${user.avatar}" placeholder="Nhập URL ảnh đại diện" />
                </div>

                <div class="mb-3">
                    <label for="identificationNumber" class="form-label">Số CCCD/CMND:</label>
                    <form:input path="identificationNumber" class="form-control" value="${user.identificationNumber}" placeholder="Nhập số CMND/CCCD" />
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại:</label>
                    <form:input path="phone" class="form-control" value="${user.phone}" placeholder="Nhập số điện thoại" />
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Địa chỉ:</label>
                    <form:input path="address" class="form-control" value="${user.address}" placeholder="Nhập địa chỉ" />
                </div>

                <div class="mb-3">
   					<label for="classId" class="form-label">Chọn lớp: </label>
   						<form:select path="classId" class="form-select">
       						<form:option value="">Không có</form:option>
       						<form:options items="${classes}" itemValue="id" itemLabel="className"/>
   						</form:select>
				</div>


                <div class="mb-3">
   					<label for="role" class="form-label">Chọn role:</label>
   						<form:select path="role" class="form-select">
       						<form:option value="">Không có</form:option>
       						<form:options items="${roles}" />
   						</form:select>
				</div>


                <!-- Nếu status không còn dùng thì bạn có thể xóa đoạn này -->
                <div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="choise" name="status">
                    	
                        <option value="true" ${user.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!user.status ? 'selected' : ''}>Ngừng hoạt động</option>
                        
                    </select>
                </div>

               <div class="full-width">
    				<button type="submit" class="button button-submit mt-3 w-100">
        			<i class="bi bi-save me-2"></i>Cập nhật
    				</button>
    				<a href="${pageContext.request.contextPath}/user/user_view" class="button button-secondary mt-2">Hủy</a>
				</div>

                </div>
            </form:form>
        </div>
    </div>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
      	rel="stylesheet"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
		rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

<jsp:include page="../footer.jsp" />
<script>
    function togglePassword() {
        const passwordField = document.getElementById("passwordField");
        const toggleIcon = document.getElementById("toggleIcon");

        const type = passwordField.getAttribute("type") === "password" ? "text" : "password";
        passwordField.setAttribute("type", type);

        toggleIcon.classList.toggle("bi-eye");
        toggleIcon.classList.toggle("bi-eye-slash");
    }
</script>
