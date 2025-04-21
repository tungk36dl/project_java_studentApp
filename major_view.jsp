
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- NhÃÂºng nÃ¡Â»Âi dung file header  -->
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
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
            <h2>Danh SÃ¡ch Major</h2>
            <a href="major_create_form" class="btn btn-primary">ThÃªm má»i Major</a>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>TÃªn Major</th>
                    <th>Tráº¡ng thÃ¡i</th>
                    <th>NgÃ y táº¡o</th>
                    <th>NgÃ y sá»­a</th>
                    <th>HÃ nh Äá»ng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="major" items="${list}">
                    <tr>
                        <td>${major.id}</td>
                        <td>${major.majorName}</td>
                        <td>
                           <c:choose>
                               <c:when test="${major.status}">
                                    <span class="text-success fw-semibold">Äang hoáº¡t Äá»ng</span>
                               </c:when>
                               <c:otherwise>
                                     <span class="text-danger fw-semibold">Ngá»«ng hoáº¡t Äá»ng</span>
                               </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${major.createdDate}</td>
                        <td>${major.updatedDate}</td>
                        <td>
                            <a href="editmajor/${major.id}" class="btn btn-warning btn-sm">Chá»nh sá»­a</a>
                            <a href="deletemajor/${major.id}" class="btn btn-danger btn-sm" 
                               onclick="return confirm('Báº¡n cÃ³ cháº¯c muá»n xÃ³a Major nÃ y?');">XÃ³a</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<hr>
<footer class="text-center text-muted mb-4">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
  &copy; 2025 - HÃ¡Â»Â thÃ¡Â»Âng quÃ¡ÂºÂ£n lÃÂ½ - NhÃÂ³m 2
</footer>

