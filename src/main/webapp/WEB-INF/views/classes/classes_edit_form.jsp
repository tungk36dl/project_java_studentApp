<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="classes" />
</jsp:include>



    <div class="container-my">
        <div class="form-container-my">
            <div class="form-title ">
        <i class="bi bi-person-plus-fill me-2"></i>Sửa lớp
        </div>
            <div class="card-body">
                <form:form modelAttribute="classes" action="${pageContext.request.contextPath}/classes/editsave" method="post">

                    <input type="hidden" name="id" value="${classes.id}">

                    <div class="mb-3">
                        <label for="className" class="form-label">Tên lớp:</label>
                        <input type="text" class="form-control" id="className" name="className" 
                               value="${classes.className}" placeholder="Nhập lớp" required>
                    </div>
					<div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="choise" name="status">
                    	
                        <option value="true" ${classes.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!classes.status ? 'selected' : ''}>Ngừng hoạt động</option>
                        
                    </select>
                </div>
					<div class="mb-3">
    					<label for="majorId" class="form-label">Chuyên ngành:</label>
    					<select class="form-control" name="majorId" id="majorId">
        					<c:forEach var="major" items="${majors}">
            					<option value="${major.id}" ${major.id == classes.majorId ? 'selected' : ''}>${major.majorName}</option>
        					</c:forEach>
    					</select>
					</div>

					<div class="mb-3">
    					<label for="cohortId" class="form-label">Khoá:</label>
    					<select class="form-control" name="cohortId" id="cohortId">
        					<c:forEach var="cohort" items="${cohorts}">
            					<option value="${cohort.id}" ${cohort.id == classes.cohortId ? 'selected' : ''}>${cohort.cohortName}</option>
        					</c:forEach>
    					</select>
					</div>
						

   		               

                    <div class="full-width">
                    <button type="submit" class="button button-submit mt-3 "> <i class="bi bi-save me-2"></i>Lưu</button>
                    <a href="${pageContext.request.contextPath}/classes/classes_view" class="button button-secondary mt-2">Hủy</a>
                </div>
                </form:form> 
            </div>
        </div>
    </div>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
      	rel="stylesheet"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
		rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../footer.jsp" />
