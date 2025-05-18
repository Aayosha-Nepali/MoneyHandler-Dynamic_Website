<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Update Income | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/addincome.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/user/userdashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/user/report"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/user/income" class="active"><i class="fas fa-coins"></i> Incomes</a></li>
        <li><a href="${contextPath}/user/expense"><i class="fas fa-receipt"></i> Expenses</a></li>
        <li><a href="${contextPath}/user/saving"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/setting"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Content -->
<main class="main-content">
    <h2>Update Income</h2>

    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form method="post" action="${contextPath}/user/income/update">
        <input type="hidden" name="incomeId" value="${income.incomeId}" />

        <label>Amount:</label>
        <input type="number" name="amount" value="${income.amount}" required />

        <label>Type:</label>
        <select name="incomeTypeId" required>
            <c:forEach var="type" items="${incomeTypes}">
                <option value="${type.incomeTypeId}"
                    <c:if test="${type.incomeTypeId == income.incomeTypeId}">selected</c:if>>
                    ${type.typeName} (${type.source})
                </option>
            </c:forEach>
        </select>

        <label>Date:</label>
        <input type="date" name="date" value="${income.date}" required />

        <div class="form-buttons">
            <button type="submit" class="btn add-btn">Update</button>
            <a href="${contextPath}/user/incomes" class="btn cancel-btn">Cancel</a>
        </div>
    </form>
</main>

</body>
</html>
