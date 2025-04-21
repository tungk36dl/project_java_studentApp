<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <h2>Login</h2>
    
 	   <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Username:</label>
        <input type="text" name="username" required /><br/>
        <label>Password:</label>
        <input type="password" name="password" required /><br/>
        <input type="submit" value="Login"/>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </form>
</body>
</html>
