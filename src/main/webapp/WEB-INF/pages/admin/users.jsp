<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Manage Users | MoneyHandler Admin</title>
    <link rel="stylesheet" href="${contextPath}/css/dashboard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="Logo" />
    <ul>
        <li><a href="${contextPath}/admindashboard"><i class="fas fa-chart-bar"></i> Dashboard</a></li>
        <li><a class="active" href="${contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/transactions"><i class="fas fa-exchange-alt"></i> Transactions</a></li>
        <li><a href="${contextPath}/admin/categories"><i class="fas fa-list"></i> Categories</a></li>
        <li><a href="${contextPath}/admin/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/admin/admincontact"><i class="fas fa-envelope"></i> Contact</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Content -->
<main>
    <h1 class="dashboard-title">All Registered Users</h1>

    <div style="margin-bottom: 1rem;">
        <a href="${contextPath}/adduser" class="btn btn-primary"><i class="fas fa-user-plus"></i> Add User</a>
        <a href="${contextPath}/admin/export/users" class="btn btn-secondary"><i class="fas fa-file-pdf"></i> Export Users</a>
    </div>

    <table class="styled-table">
        <thead>
        <tr>
            <th>#</th>
            <th>Username</th>
            <th>Email</th>
            <th>DOB</th>
            <th>Contact</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.dateOfBirth}</td>
                <td>${user.contactNumber}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
</html>
