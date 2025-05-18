<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../../header.jsp">
    <jsp:param name="pageActive" value="subjectDetail" />
</jsp:include>

<!-- Bootstrap 5.3.0 từ CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f9f9f9;
        padding: 20px;
    }

    h2 {
        text-align: center;
        color: #2c3e50;
        margin-bottom: 20px;
    }

    .student-info {
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px 20px;
        margin-bottom: 20px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
    }

    .student-info p {
        margin: 8px 0;
        color: #333;
    }

    .student-info span {
        display: inline-block;
        margin-right: 25px;
        font-size: 15px;
    }

    .summary {
        margin-top: 10px;
        font-weight: bold;
        color: #2c3e50;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background-color: #fff;
        box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        border-radius: 6px;
        overflow: hidden;
    }

    th, td {
        padding: 10px 8px;
        text-align: center;
        border-bottom: 1px solid #ddd;
        font-size: 14px;
    }

    th {
        background-color: #3498db;
        color: white;
        font-weight: bold;
    }

    tbody tr:hover {
        background-color: #f1f1f1;
    }

    td {
        color: #333;
    }

    td:last-child {
        font-weight: bold;
        color: #2c3e50;
    }

    /* Responsive tweak (optional) */
    @media (max-width: 768px) {
        .student-info span {
            display: block;
            margin-bottom: 5px;
        }

        table, thead, tbody, th, td, tr {
            font-size: 12px;
        }
    }
</style>


<h2>BẢNG ĐIỂM HỌC PHẦN</h2>

<div class="student-info">
    <p>
        <span><strong>Họ tên:</strong> ${inforScoreStudentResponse.fullName}</span>
        <span><strong>Mã SV:</strong> ${inforScoreStudentResponse.studentId}</span>
        <span><strong>Ngày sinh:</strong> ${inforScoreStudentResponse.dateOfBirth}</span>
    </p>
    <p>
        <span><strong>Lớp:</strong> ${inforScoreStudentResponse.className}</span>
        <span><strong>Địa chỉ:</strong> ${inforScoreStudentResponse.address}</span>
    </p>
    <p class="summary">
        <span><strong>Tổng số tín chỉ đã học:</strong> ${inforScoreStudentResponse.totalCredits}</span>
        <span><strong>GPA trung bình:</strong> ${inforScoreStudentResponse.avgGPA}</span>
    </p>
</div>

<div class="float-end">
	<a class="btn btn-success mb-3" href="${pageContext.request.contextPath}/score/export?studentId=${studentId}">
	    <i class="bi bi-download me-1"></i> Xuất Excel
	</a>
</div>


<table>
    <thead>
        <tr>
            <th>Mã HP</th>
            <th>Tên học phần</th>
            <th>Số TC</th>
            <th>HK</th>
            <th>Điểm 10</th>
            <th>Điểm chữ</th>
            <th>GPA</th>
            <th>Điểm miễn</th>
            <th>TP L1</th>
            <th>Thi L1</th>
            <th>Điểm 10 L1</th>
            <th>TP L2</th>
            <th>Thi L2</th>
            <th>Điểm 10 L2</th>
            <th>Trạng thái</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="score" items="${scoreResponses}">
            <tr>
                <td>${score.subjectCode}</td>
                <td>${score.subjectName}</td>
                <td>${score.credits}</td>
                <td>${score.semester}</td>
                <td>${score.score10}</td>
<td>
    <c:choose>
        <c:when test="${score.scoreLetter == 'A'}">
            <span style="background-color:#2ecc71; color:white; padding:3px 8px; border-radius:4px;">${score.scoreLetter}</span>
        </c:when>
        <c:when test="${score.scoreLetter == 'B' || score.scoreLetter == 'C'}">
            <span style="background-color:#3498db; color:white; padding:3px 8px; border-radius:4px;">${score.scoreLetter}</span>
        </c:when>
        <c:when test="${score.scoreLetter == 'D'}">
            <span style="background-color:#f1c40f; color:black; padding:3px 8px; border-radius:4px;">${score.scoreLetter}</span>
        </c:when>
        <c:when test="${score.scoreLetter == 'F'}">
            <span style="background-color:#e74c3c; color:white; padding:3px 8px; border-radius:4px;">${score.scoreLetter}</span>
        </c:when>
        <c:otherwise>
            ${score.scoreLetter}
        </c:otherwise>
    </c:choose>
</td>
                <td>${score.GPA4}</td>
                <td>${score.examExemption}</td>
                <td>${score.scoreAttendance}</td>
                <td>${score.scoreTest}</td>
                <td>${score.scoreFinalExam}</td>
                <td>${score.scoreAttendanceL2}</td>
                <td>${score.scoreTestL2}</td>
                <td>${score.scoreFinalExamL2}</td>
<td>
    <c:choose>
        <c:when test="${score.GPA4 >= 2.0}">
            <span style="background-color:#2ecc71; color:white; padding:3px 8px; border-radius:4px;">Đạt</span>
        </c:when>
        <c:otherwise>
            <span style="background-color:#e74c3c; color:white; padding:3px 8px; border-radius:4px;">Không đạt</span>
        </c:otherwise>
    </c:choose>
</td>

            </tr>
        </c:forEach>
    </tbody>
</table>

<jsp:include page="../../footer.jsp" />
