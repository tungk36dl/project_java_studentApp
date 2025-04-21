<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
    String pageActive = request.getParameter("pageActive");
%>
<c:set var="loggedInUser" value="${sessionScope.loggedInUser}" />
<c:set var="role" value="${sessionScope.role}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Trang Quản Lý</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Quản Lý</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link <%= "user".equals(pageActive) ? "active" : "" %>" href="${pageContext.request.contextPath}/user/user_view">User</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <%= "major".equals(pageActive) ? "active" : "" %>" href="${pageContext.request.contextPath}/major/major_view">Major</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <%= "cohort".equals(pageActive) ? "active" : "" %>" href="${pageContext.request.contextPath}/cohort/cohort_view">Cohort</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <%= "subject".equals(pageActive) ? "active" : "" %>" href="${pageContext.request.contextPath}/subject/subject_view">Subject</a>
        </li>
     
        <li class="nav-item">
          <a class="nav-link <%= "classes".equals(pageActive) ? "active" : "" %>" href="${pageContext.request.contextPath}/classes/classes_view">Classes</a>
        </li>
      </ul>
      
      <div class="d-flex ms-auto align-items-center">
		
		  <span class="text-white me-3">
		    Xin chào, <strong>${loggedInUser.username != null ? loggedInUser.username : "Khách"} </strong>
		  </span>
		  <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">Logout</a>
		</div>
      
    </div>
  </div>
</nav>

<script>
    let accessToken = '<%= session.getAttribute("accessToken") %>';
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

