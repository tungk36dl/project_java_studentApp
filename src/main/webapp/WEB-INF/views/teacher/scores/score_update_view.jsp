<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>

<!-- Bootstrap 5.3.0 từ CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<title>Nhập điểm sinh viên</title>
<style>


input[type="number"]:focus {
    background-color: #e6f7ff; /* Màu nền xanh nhạt */
    border-color: #3498db;
    outline: none;
}

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    th, td {
        padding: 8px;
        border: 1px solid #ccc;
        text-align: center;
    }

    input[type="number"] {
        width: 80px;
        text-align: center;
        padding: 4px;
        border-radius: 4px;
        border: 1px solid #ccc;
        transition: border-color 0.3s ease;
    }

    input[type="number"]:focus {
        border-color: #3498db;
        outline: none;
    }

    tbody tr:hover {
        background-color: #f0f9ff;
    }

    .submit-btn {
        margin-top: 20px;
        background-color: #3498db;
        color: white;
        padding: 10px 20px;
        font-size: 16px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .submit-btn:hover {
        background-color: #2980b9;
    }

    thead th {
        background-color: #2980b9;
        color: white;
        font-weight: bold;
    }

    h2 {
        text-align: center;
        color: #2c3e50;
        margin-top: 20px;
    }

    h2:before {
        content: "📝 ";
    }

</style>
<script>
    function restoreIfEmpty(input, originalValue) {
        if (input.value.trim() === '') {
            input.value = originalValue;
        }
    }
</script>

<body>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

    

<h2>Nhập điểm cho sinh viên</h2>

<div class="container my-4"> 
	<div class="card"> 
		<div class="card-body"> 
			<div class="row mb-3"> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Tên lớp học:</span> ${className} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Tên môn học:</span> ${subjectName} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Sửa gần nhất bởi:</span> ${updatedBy} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Tên giảng viên:</span> ${teacherName} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Học kỳ:</span> ${subjectDetail.semester} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Ngày cập nhật:</span> ${updatedDate} </div> 
				<div class="col-md-4 mb-2"> <span class="fw-bold">Số tín chỉ:</span> ${subjectDetail.credit} </div> 
			</div> 
		</div> 
	</div> 
</div>



<form action="${pageContext.request.contextPath}/score/submitScores" method="post">
<input type="hidden" name="subjectDetailId" value="${subjectDetail.id}" />

    <table>
        <thead>
        <tr>
            <th>Mã sinh viên</th>
            <th>Họ tên</th>
            <th>Điểm chuyên cần</th>
            <th>Điểm kiểm tra</th>
            <th>Điểm thi</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="score" items="${inputScoreResponses}" varStatus="status">
            <tr>
                 <td>
		            <input type="hidden" name="scores[${status.index}].studentId" value="${score.studentId}" />
		            <input type="hidden" name="scores[${status.index}].scoreId" value="${score.scoreId}" />
		            ${score.studentId}
		        </td>
                <td>
                    <input type="hidden" name="scores[${status.index}].studentName" value="${score.studentName}" />
                    ${score.studentName}
                </td>
<td>
    <input type="hidden" name="scores[${status.index}].originalScoreAttendance" value="${score.scoreAttendance}" />
    <c:choose>
        <c:when test="${score.scoreAttendance == -1}">
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreAttendance"
                   value=""
                   onblur="restoreIfEmpty(this, '')" />
        </c:when>
        <c:otherwise>
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreAttendance"
                   value="${score.scoreAttendance}"
                   onblur="restoreIfEmpty(this, '${score.scoreAttendance}')" />
        </c:otherwise>
    </c:choose>
</td>

<td>
    <input type="hidden" name="scores[${status.index}].originalScoreTest" value="${score.scoreTest}" />
    <c:choose>
        <c:when test="${score.scoreTest == -1}">
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreTest"
                   value=""
                   onblur="restoreIfEmpty(this, '')" />
        </c:when>
        <c:otherwise>
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreTest"
                   value="${score.scoreTest}"
                   onblur="restoreIfEmpty(this, '${score.scoreTest}')" />
        </c:otherwise>
    </c:choose>
</td>

<td>
    <input type="hidden" name="scores[${status.index}].originalScoreFinalExam" value="${score.scoreFinalExam}" />
    <c:choose>
        <c:when test="${score.scoreFinalExam == -1}">
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreFinalExam"
                   value=""
                   onblur="restoreIfEmpty(this, '')" />
        </c:when>
        <c:otherwise>
            <input type="number" step="0.1" min="0" max="10"
                   name="scores[${status.index}].scoreFinalExam"
                   value="${score.scoreFinalExam}"
                   onblur="restoreIfEmpty(this, '${score.scoreFinalExam}')" />
        </c:otherwise>
    </c:choose>
</td>




            </tr>
        </c:forEach>
        </tbody>
    </table>

<div class="d-flex justify-content-start mt-3">
    <button type="submit" class="btn btn-primary me-2">
        <i class="bi bi-save"></i> Lưu điểm
    </button>
    <a href="${pageContext.request.contextPath}/subjectDetail/subjectDetail_view" class="btn btn-secondary">
        <i class="bi bi-x-circle"></i> Hủy
    </a>
</div>
    
</form>

<!-- Nội dung file footer -->
<jsp:include page="../../footer.jsp" />
