<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="subject" />
</jsp:include>

<div class="container-my">
    <div class="form-container-my">
         <div class="form-title ">
        <i class="bi bi-tools me-2"></i>Sửa môn học
        </div>
        
            <form:form action="${pageContext.request.contextPath}/subject/editsave" method="post" modelAttribute = "subject">
                <input type="hidden" name="id" value="${subject.id}">
                <input type="hidden" name="code" value="${subject.code}">
                

                <div class="mb-3">
                    <label for="name" class="form-label">Tên môn học:</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${subject.name}" placeholder="Nhập tên môn học" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="choise" name="status">
                    	
                        <option value="true" ${subject.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!subject.status ? 'selected' : ''}>Ngừng hoạt động</option>
                        
                    </select>
                </div>

               
                <div class="full-width">
                    <button type="submit" class="button button-submit mt-3 "> <i class="bi bi-save me-2"></i>Lưu</button>
                    <a href="${pageContext.request.contextPath}/subject/subject_view" class="button button-secondary mt-2">Hủy</a>
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
