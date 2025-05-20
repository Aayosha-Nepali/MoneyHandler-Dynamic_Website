<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>All Transactions | Admin</title>
    <link rel="stylesheet" href="${contextPath}/css/transaction.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
</head>
<body>

<!-- Sidebar (Admin specific) -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/admindashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/admin/user"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/transaction"><i class="fas fa-exchange-alt"></i> Transactions</a></li>
        <li><a href="${contextPath}/admin/categories"><i class="fas fa-list"></i> Categories</a></li>
        <li><a href="${contextPath}/admin/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/admin/contact"><i class="fas fa-envelope"></i> Contact</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Content -->
<div class="main-content">
    <h1>All Transactions</h1>

    <div class="table-actions">
        <form method="get" action="${contextPath}/admin/export/transaction">
            <button type="submit" class="export-btn"><i class="fas fa-file-pdf"></i> Export to PDF</button>
        </form>
    </div>

    <table class="transactions-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>User</th>
                <th>Type</th>
                <th>Category</th>
                <th>Amount (Rs.)</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="txn" items="${transactions}">
                <tr>
                    <td>${txn.transactionId}</td>
                    <td>${txn.username}</td>
                    <td>${txn.type}</td>
                    <td>${txn.category}</td>
                    <td>${txn.amount}</td>
                    <td>${txn.date}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
