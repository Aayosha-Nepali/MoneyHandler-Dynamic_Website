<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>About Us | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/aboutuscontactus.css" />
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
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Content -->
<div class="content-container">
    <h1>About MoneyHandler</h1>
    <p>
        MoneyHandler is your personal finance management tool, designed to simplify the way you track income, expenses, and savings. 
        With clean visual dashboards, financial reports, and category breakdowns, you can stay in control of your finances.
    </p>
    <p>
        Whether you're saving for a goal or just monitoring your spending, MoneyHandler helps you make smarter decisions. 
        Your data is secure, your experience is smooth, and your insights are always available.
    </p>
    <p>
        Built with 💡 by passionate developers, designed for real-world financial control.
    </p>

    <a href="${contextPath}/user/contactus" class="contact-button">Contact Us</a>
</div>

</body>
</html>
