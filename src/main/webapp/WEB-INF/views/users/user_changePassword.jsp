<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>
  

    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Sửa thông tin các nhân</h2>
            <form:form method="post"  id="changePasswordForm" action="${pageContext.request.contextPath}/user/changePassword" modelAttribute="changePasswordReq">



				<div class="mb-3">
				    <label for="currentPassword" class="form-label">Mật khẩu hiện tại :</label>
				    <form:input path="currentPassword" class="form-control"  />
				        <c:if test="${not empty notMatchPassword}">
					        <div class="alert alert-warning text-center">${notMatchPassword}</div>
					    </c:if>
				</div>
				<div class="mb-3">
				    <label for="newPassword" class="form-label">Mật khẩu mới :</label>
				    <form:input path="newPassword" class="form-control"   />
				</div>
				<div class="mb-3">
				    <label for="reNewPassword" class="form-label">Nhập lại mật khẩu mới :</label>
				    <form:input path="reNewPassword" class="form-control"  />
				        <c:if test="${not empty errorRePassword}">
					        <div class="alert alert-warning text-center">${errorRePassword}</div>
					    </c:if>
				</div>

                <button type="submit" class="btn btn-warning">Cập nhật</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    document.getElementById("changePasswordForm").addEventListener("submit", function(event) {
        const currentPassword = document.getElementsByName("currentPassword")[0].value.trim();
        const newPassword = document.getElementsByName("newPassword")[0].value.trim();
        const reNewPassword = document.getElementsByName("reNewPassword")[0].value.trim();

        if (!currentPassword || !newPassword || !reNewPassword) {
            alert("Vui lòng nhập đầy đủ các trường bắt buộc!");
            event.preventDefault(); // Chặn submit
        }
    });
</script>
    
<jsp:include page="../footer.jsp" />
