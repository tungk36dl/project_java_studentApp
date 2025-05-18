<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../../header.jsp">
    <jsp:param name="pageActive" value="subjectDetail" />
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
            <h2>danh sach chi tiet mon hoc</h2>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên lớp</th>
                    <th>Mon hoc</th>
                    <th>Giang vien</th>
                    <th>Ki hoc</th>
                    <th>Tin chi</th>
                    <th>Trang thai</th>
                    <th>ngay tao</th>
                    <th>Ngày cap nhat</th>
                    <th>Hanh dong</th>
                 
                </tr>
            </thead>
            <tbody>
                <c:forEach var="subjectDetail" items="${list}">
                    <tr>
                        <td>${subjectDetail.id}</td>
                        <td>${subjectDetail.className}</td>
                        <td>${subjectDetail.subjectName}</td>
                        <td>${subjectDetail.teacherName}</td>
                        <td>${subjectDetail.semester}</td>
                        <td>${subjectDetail.credit}</td>
                         <td>
                           <c:choose>
                               <c:when test="${subjectDetail.status}">
                                    <span class="text-success fw-semibold">Đang hoạt động</span>
                               </c:when>
                               <c:otherwise>
                                     <span class="text-danger fw-semibold">Ngừng hoạt động</span>
                               </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${subjectDetail.createdDate}</td>
                        <td>${subjectDetail.updatedDate}</td>
                       
                         <td>
						    <c:if test="${subjectDetail.status}">
						        <a href="${pageContext.request.contextPath}/score/inputScore/${subjectDetail.id}" class="btn btn-primary btn-sm">Nhập điểm</a>
						    </c:if>
							
						</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>


<!-- Nội dung file footer -->
<jsp:include page="../../footer.jsp" />
