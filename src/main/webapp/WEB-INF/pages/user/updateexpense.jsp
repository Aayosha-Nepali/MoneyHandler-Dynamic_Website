<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Update Expense | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/userdashboard.css" />
    <link rel="stylesheet" href="${contextPath}/css/addincome.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/user/userdashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/user/report"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/user/income"><i class="fas fa-coins"></i> Incomes</a></li>
        <li><a href="${contextPath}/user/expense"><i class="fas fa-receipt"></i> Expenses</a></li>
        <li><a href="${contextPath}/user/savings"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/setting"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<main>
    <h1 class="dashboard-title">Update Expense</h1>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>

    <form method="post" action="${contextPath}/user/update-expense" class="form-layout">
        <input type="hidden" name="expenseId" value="${expense.expenseId}" />

        <label>Amount:</label>
        <input type="number" name="amount" step="0.01" value="${expense.amount}" required />

        <label>Vendor:</label>
        <input type="text" name="vendor" value="${expense.vendor}" required />

        <label>Type:</label>
        <select name="expenseTypeId" required>
            <c:forEach var="type" items="${expenseTypeList}">
                <option value="${type.expenseTypeId}" 
                        <c:if test="${type.expenseTypeId == expense.expenseTypeId}">selected</c:if>>
                    ${type.typeName} (${type.vendor})
                </option>
            </c:forEach>
        </select>

        <label>Date:</label>
        <input type="date" name="date" value="${expense.date}" required />

        <button type="submit" class="btn btn-primary">Update Expense</button>
    </form>
</main>

</body>
</html>
