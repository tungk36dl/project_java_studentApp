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



<div class="container-my">
    <div class="form-container-my">
        <div class="form-title ">
        <i class="bi bi-person-plus-fill me-2"></i>Thêm người dùng mới
        </div>
        <form:form method="post" action="save" modelAttribute="user" enctype="multipart/form-data">
            <div class="form-grid">
                <div>
                    <label class="form-label">Mã sinh viên / giảng viên:</label>
                    <form:input path="code" class="form-control" placeholder="Nhập mã sinh viên/ Giảng viên" />
                    <c:if test="${codeExist == true}">
                        <p class="text-danger">Mã đã tồn tại</p>
                    </c:if>
                </div>

                <div>
                    <label class="form-label">Tên đăng nhập:</label>
                    <form:input path="username" class="form-control" placeholder="Nhập tên đăng nhập" />
                    <c:if test="${userNameExist == true}">
                        <p class="text-danger">Username đã tồn tại</p>
                    </c:if>
                </div>

                <div>
                    <label class="form-label">Mật khẩu:</label>
                    <div class="input-group">
        				<form:password path="password" class="form-control" placeholder="Nhập mật khẩu" id="passwordField" />
        					<span class="input-group-text" onclick="togglePassword()">
            					<i class="bi bi-eye" id="toggleIcon"></i>
        					</span>
   					</div>
                </div>

                <div>
                    <label class="form-label">Họ tên:</label>
                    <form:input path="fullName" class="form-control" placeholder="Nhập họ tên" />
                </div>

                <div>
                    <label class="form-label">Email:</label>
                    <form:input path="email" type="email" class="form-control" placeholder="Nhập email" />
                    <c:if test="${emailExist == true}">
                        <p class="text-danger">Email đã tồn tại</p>
                    </c:if>
                </div>

                <div>
                    <label class="form-label">Ngày sinh:</label>
                    <form:input path="dateOfBirth" type="date" class="form-control" />
                </div>

                <div>
                    <label class="form-label">Giới tính:</label>
                    <form:select path="gender" class="form-select">
                        <form:option value="" label=" Chọn giới tính " />
                        <form:option value="Nam" label="Nam" />
                        <form:option value="Nữ" label="Nữ" />
                        <form:option value="Khác" label="Khác" />
                    </form:select>
                </div>

              <div>
			    <label class="form-label">Ảnh đại diện:</label>
			    <div class="mb-2">
			        <input type="radio" name="avatarInputType" value="url" id="inputUrl" checked onchange="toggleAvatarInput()" />
			        <label for="inputUrl">Nhập URL</label>
			
			        <input type="radio" name="avatarInputType" value="file" id="inputFile" class="ms-3" onchange="toggleAvatarInput()" />
			        <label for="inputFile">Tải ảnh từ máy</label>
			    </div>
			
			    <!-- Nhập URL ảnh -->
			    <div id="avatarUrlInput">
			        <form:input path="avatar" class="form-control" placeholder="Nhập URL ảnh đại diện" />
			    </div>
			
			    <!-- Upload file ảnh -->
			    <div id="avatarFileInput" style="display: none;">
			        <input type="file" name="avatarFile" accept="image/*" class="form-control" />
			    </div>
			</div>


                <div>
                    <label class="form-label">Số CCCD/CMND:</label>
                    <form:input path="identificationNumber" class="form-control" placeholder="Nhập số CMND/CCCD" />
                </div>

                <div>
                    <label class="form-label">Số điện thoại:</label>
                    <form:input path="phone" class="form-control" placeholder="Nhập số điện thoại" />
                </div>

                <div>
                    <label class="form-label">Địa chỉ:</label>
                    <form:input path="address" class="form-control" placeholder="Nhập địa chỉ" />
                </div>

                <div>
                    <label class="form-label">Chọn lớp:</label>
                    <select name="classId" class="form-select">
                        <option value="">Không có</option>
                        <c:forEach var="cls" items="${classes}">
                            <option value="${cls.id}" ${param.classId == cls.id ? 'selected' : ''}>${cls.className}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="form-label">Chọn role:</label>
                    <select name="role" class="form-select">
                        <option value="">Không có</option>
                        <c:forEach var="r" items="${roles}">
                            <option value="${r}" ${param.role == r ? 'selected' : ''}>${r}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-check full-width mt-2">
                    <form:checkbox path="status" class="form-check-input" id="status" />
                    <label for="status" class="form-check-label ">Người dùng đang hoạt động</label>
                </div>

                <div class="full-width">
                    <button type="submit" class="button button-submit mt-3 "> <i class="bi bi-save me-2"></i>Lưu</button>
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
<script>
    document.querySelector("form").addEventListener("submit", function (e) {
        let valid = true;

        const selects = [
            { id: "gender", name: "Giới tính" },
            { name: "classId", nameVN: "Lớp" },
            { name: "role", nameVN: "Vai trò" }
        ];

        selects.forEach(s => {
            const selectEl = document.querySelector(`select[name='${s.name}']`);
            if (selectEl && selectEl.value === "") {
                selectEl.classList.add("input-error");
                valid = false;
            } else if (selectEl) {
                selectEl.classList.remove("input-error");
            }
        });

        if (!valid) {
            e.preventDefault(); // Ngăn submit nếu có lỗi
            alert("Vui lòng chọn đầy đủ các trường bắt buộc.");
        }
    });
</script>

<script>
    function toggleAvatarInput() {
        const urlInput = document.getElementById('avatarUrlInput');
        const fileInput = document.getElementById('avatarFileInput');

        const selected = document.querySelector('input[name="avatarInputType"]:checked').value;
        if (selected === 'url') {
            urlInput.style.display = 'block';
            fileInput.style.display = 'none';
        } else {
            urlInput.style.display = 'none';
            fileInput.style.display = 'block';
        }
    }
</script>




