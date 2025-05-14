<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_small.png" class="sidebar-logo" alt="Logo" />
    <ul>
        <li><a href="${contextPath}/admin/admindashboard"><i class="fas fa-chart-bar"></i> Dashboard</a></li>
        <li><a href="${contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/reports"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>
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
