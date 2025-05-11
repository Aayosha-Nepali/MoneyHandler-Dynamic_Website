<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="header">Welcome Admin, ${sessionScope.loggedInUser.username}</div>
    <div class="container">
        <div class="card">
            <div class="card-title">Registered Users</div>
            <div class="card-value">${userCount}</div>
        </div>
        <div class="card">
            <div class="card-title">Total Income Records</div>
            <div class="card-value">${incomeCount}</div>
        </div>
        <div class="card">
            <div class="card-title">Total Expense Records</div>
            <div class="card-value">${expenseCount}</div>
        </div>
    </div>
</body>
</html>
