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
        <i class="bi bi-person-plus-fill me-2"></i>Thêm lớp mới
        </div>
            <form:form method="post" action="save" modelAttribute="classes">
                <div class="mb-3">
                    <label for="className" class="form-label">Tên lớp:</label>
                    <form:input path="className" class="form-control" placeholder="Nhập tên lớp" />
                    <c:if test="${not empty nameEmpty}">
			        <div class="alert alert-warning text-center">${nameEmpty}</div>
			    </c:if>
			    <c:if test="${not empty nameExist}">
			        <div class="alert alert-warning text-center">${nameExist}</div>
			    </c:if>
                </div>
              <div class="mb-3 form-check">
               <form:checkbox path="status" class="form-check-input" id="status" />
			        <label for="status" class="form-check-label">Môn học đang hoạt động</label>
   
    </div>
    
    			
              
              <div class="mb-3">
                   
                    <label class="form-label">Chuyên ngành:</label>
					<select class="form-control" name="majorId">
    				<c:forEach var="major" items="${majors}">
    				
        					<option value="${major.id}">${major.majorName}</option>
        					
   					</c:forEach>
					</select>
                </div>
                 <div class="mb-3">
                    <label class="form-label">Khoá:</label>
					<select class="form-control" name="cohortId">
    				<c:forEach var="cohort" items="${cohorts}">
    				
        					<option value="${cohort.id}">${cohort.cohortName}</option>
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

<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
      	rel="stylesheet"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
		rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../footer.jsp" />
