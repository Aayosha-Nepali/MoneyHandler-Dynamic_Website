<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Contact Us | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/aboutuscontactus.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="Logo" />
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
            <li><a class="active" href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
            <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </div>

    <main>
        <div class="contact-form-container">
            <h1>Contact Us</h1>
            <form action="${contextPath}/user/contactus" method="post">
                <label for="name">Name:</label>
                <input type="text" name="name" required>

                <label for="email">Email:</label>
                <input type="email" name="email" required>

                <label for="subject">Subject:</label>
                <input type="text" name="subject" required>

                <label for="message">Message:</label>
                <textarea name="message" rows="6" required></textarea>

                <button type="submit" class="btn">Send Message</button>
            </form>

            <c:if test="${not empty success}">
                <div class="success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
        </div>
    </main>
</body>
</html>
