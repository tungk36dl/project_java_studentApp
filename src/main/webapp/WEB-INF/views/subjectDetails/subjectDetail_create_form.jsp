<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="subjectDetail" />
</jsp:include>


    <div class="container-my">
        <div class="form-container-my">
           <div class="form-title ">
        <i class="bi bi-person-plus-fill me-2"></i>Thêm chi tiết môn học
        </div>
            <form:form method="post" action="save" modelAttribute="subjectDetail">
              <div class="mb-3">
                  <c:if test="${not empty nameExist}">
			        <div class="alert alert-warning text-center">${nameExist}</div>
			    </c:if>
                    <label class="form-label">Môn học:</label>
					<select class="form-control" name="subjectId">
    				<c:forEach var="subject" items="${subjects}">
    				
        					<option value="${subject.id}">${subject.name}</option>
        					
   					</c:forEach>
					</select>
                </div>
                 <div class="mb-3">
                    <label class="form-label">Lớp:</label>
					<select class="form-control" name="classId">
    				<c:forEach var="cl" items="${classes}">
        					<option value="${cl.id}">${cl.className}</option>
    				</c:forEach>
					</select>

                </div>
                
                 <div class="mb-3">
                    <label class="form-label">Giảng viên:</label>
					<select class="form-control" name="teacherId">
    				<c:forEach var="teacher" items="${teachers}">
    				
        					<option value="${teacher.id}">${teacher.username}</option>
    				</c:forEach>
					</select>

                </div>
                
                <div class="mb-3">
                    <label for="credit" class="form-label">Số tín chỉ:</label>
                    <form:input path="credit" class="form-control" type = "Number" placeholder="Nhập so tin chi" step="1" min="0" max="10"/>
                </div>
                
              <div class="mb-3">
                    <label class="form-label">Học kì:</label>
					<select class="form-control" name="semester">
    				<c:forEach var="semester" items="${semesters}">
    				
        					<option value="${semester}">${semester}</option>
    				</c:forEach>
					</select>

                </div>
                
               <div class="mb-3 form-check">
                <form:checkbox path="status" class="form-check-input" id="status" />
                <label for="status" class="form-check-label"> đang hoạt động</label>
            </div>
              
                <div class="full-width">
                    <button type="submit" class="button button-submit mt-3 "> <i class="bi bi-save me-2"></i>Lưu</button>
                    <a href="${pageContext.request.contextPath}/subjectDetail/subjectDetail_view" class="button button-secondary mt-2">Hủy</a>
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