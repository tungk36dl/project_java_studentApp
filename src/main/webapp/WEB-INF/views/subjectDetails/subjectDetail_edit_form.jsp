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
        <i class="bi bi-person-plus-fill me-2"></i>Sửa chi tiết môn học 
        </div>
        
            <form:form action="${pageContext.request.contextPath}/subjectDetail/editsave" method="post" modelAttribute="subjectDetail">
                <input type="hidden" name="id" value="${subjectDetail.id}">

                <!-- Môn học -->
                      <div class="mb-3">
                    <label class="form-label">Môn học :</label>
				<select class="form-control" name="subjectId">
				    <c:forEach var="subject" items="${subjects}">
				        <option value="${subject.id}" <c:if test="${subject.id == subjectDetail.subjectId}">selected</c:if>>
				            ${subject.name}
				        </option>
				    </c:forEach>
				</select>
				</div>
				
				<!-- Lớp -->
				      <div class="mb-3">
                    <label class="form-label">Lớp :</label>
				<select class="form-control" name="classId">
				    <c:forEach var="cl" items="${classes}">
				        <option value="${cl.id}" <c:if test="${cl.id == subjectDetail.classId}">selected</c:if>>
				            ${cl.className}
				        </option>
				    </c:forEach>
				</select>
				</div>
				
				<!-- Giảng viên -->
				      <div class="mb-3">
                    <label class="form-label">Giảng viên :</label>
				<select class="form-control" name="teacherId">
				    <c:forEach var="teacher" items="${teachers}">
				        <option value="${teacher.id}" <c:if test="${teacher.id == subjectDetail.teacherId}">selected</c:if>>
				            ${teacher.username}
				        </option>
				    </c:forEach>
				</select>
				</div>
				
				<!-- Học kỳ -->
				      <div class="mb-3">
                    <label class="form-label">Học kì :</label>
				<select class="form-control" name="semester">
				    <c:forEach var="semester" items="${semesters}">
				        <option value="${semester}" <c:if test="${semester == subjectDetail.semester}">selected</c:if>>
				            ${semester}
				        </option>
				    </c:forEach>
				</select>
				</div>

                
                <div class="mb-3">
                    <label class="form-label">Tín Chỉ:</label>
                    <input type="number" class="form-control" name="credit" value="${subjectDetail.credit}" required step="1" min="0" max="10">
                </div>
                
          
                
               <div class="mb-3">
                    <label class="form-label">Trạng thái:</label>
                    <select class="choise" name="status">
                    	
                        <option value="true" ${subjectDetail.status ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="false" ${!subjectDetail.status ? 'selected' : ''}>Ngừng hoạt động</option>
                        
                    </select>
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
