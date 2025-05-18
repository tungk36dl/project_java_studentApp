<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String pageActive = request.getParameter("pageActive");
%>
<c:set var="loggedInUser" value="${sessionScope.loggedInUser}" />
<c:set var="role" value="${sessionScope.role}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang Quản Lý</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/style.css">

<!-- Bootstrap 5.3.0 từ CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

	<div class="custom-admin-navbar">
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<!-- <a class="navbar-brand" href="#">Quản Lý</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button> -->
			<div class="collapse navbar-collapse " id="navbarNav">
				<ul class="navbar-nav align-items-center">
                     <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
                     <img class="img-fluid" src="https://biu.edu.vn/wp-content/uploads/2021/06/logo-Bac-Ha-chuan-1.png" alt="Đại học Quốc tế Bắc Hà">
                     </a>
					<!-- Nếu role là PDT thì hiển thị tất cả -->
					<c:if test="${role eq 'PDT'}">
						<li class="nav-item"><a
							class="nav-link <%= "user".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/user/user_view"> <i
								class="bi bi-person-gear me-1"></i> Quản lý tài khoản
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "major".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/major/major_view"> <i
								class="bi bi-diagram-3 me-1"></i> Chuyên Ngành
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "cohort".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/cohort/cohort_view">
								<i class="bi bi-calendar-range me-1"></i> Khóa Học
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "subject".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subject/subject_view">
								<i class="bi bi-journal-bookmark me-1"></i> Môn Học
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "classes".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/classes/classes_view">
								<i class="bi bi-people-fill me-1"></i> Lớp
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "subjectDetail".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subjectDetail/subjectDetail_view">
								<i class="bi bi-card-list me-1"></i> Quản Lý Môn Học
						</a></li>


					</c:if>

					<!-- Nếu role là Teacher -->
					<c:if test="${role eq 'TEACHER'}">
						<li class="nav-item"><a
							class="nav-link <%= "subjectDetail".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subjectDetail/subjectDetailByTeacher">SubjectDetail</a>
						</li>

					</c:if>

					<!-- Nếu role là Student -->
					<c:if test="${role eq 'STUDENT'}">
						<li class="nav-item"><a
							class="nav-link <%= "score".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/score/scoreByStudent">Score</a>
						</li>
					</c:if>

				</ul>

				<div class="d-flex ms-auto align-items-center">

					<div class="dropdown">
    <button class="btn btn-light dropdown-toggle d-flex align-items-center" type="button"
            id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
        <i class="fa-solid fa-circle-user me-1"></i>
        <strong class="username-text">${loggedInUser.username != null ? loggedInUser.username : "User"}</strong>
    </button>
    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
        <li>
            <a class="dropdown-item" href="${pageContext.request.contextPath}/user/editInfo">
                Sửa thông tin cá nhân
            </a>
        </li>
        <li>
            <a class="dropdown-item" href="${pageContext.request.contextPath}/user/changePassword">
                Đổi mật khẩu
            </a>
        </li>
        <li><hr class="dropdown-divider"></li>
        <li>
            <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                Đăng xuất <i class="fa-solid fa-right-from-bracket ms-1"></i>
            </a>
        </li>
    </ul>
</div>


				</div>

			</div>
		</div>
	</nav>
	</div>

	<script>
    let accessToken = '<%=session.getAttribute("accessToken")%>';
</script>


	<script>
async function callApi(url, options = {}) {
    if (!options.headers) options.headers = {};
    options.headers['Authorization'] = 'Bearer ' + accessToken;

    let res = await fetch(url, options);

    // accessToken hết hạn
    if (res.status === 401) {
        const refreshRes = await fetch("/refresh-token", { method: 'POST' });
        if (refreshRes.ok) {
            accessToken = await refreshRes.text();
            options.headers['Authorization'] = 'Bearer ' + accessToken;
            return fetch(url, options); // retry call
        } else {
            alert("Phiên đăng nhập đã hết. Vui lòng đăng nhập lại!");
            window.location.href = "/login";
        }
    }

    return res;
}
</script>