<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration Form | MoneyHandler</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/register.css" />
</head>
<body>
    <div class="container">

        <!-- LEFT SIDE: Registration Form -->
        <div class="form-section">
            <!-- Logo -->
            <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png"
                 alt="MoneyHandler Logo" class="logo" />

            <h2>Sign Up Now!</h2>

            <!-- Display error message if available -->
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>

            <!-- Display success message if available -->
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

            <!-- Registration Form -->
            <form action="${contextPath}/RegisterController" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="email" name="email" placeholder="Email address" required>
                <input type="date" name="dob" placeholder="Date of Birth" required>
                <input type="text" name="phone" placeholder="Phone Number" required>

                <button type="submit">Register &#x27A4;</button>
            </form>

            <p class="signin-link">
                Already have an account? <a href="${contextPath}/pages/login.jsp">Sign in</a>
            </p>
        </div>

        <!-- RIGHT SIDE: Design / Info -->
        <div class="side-section">
            <button class="signup-btn">Sign up</button>
            <span class="join-text">Join us</span>

            <div class="image-placeholder">
                <img src="${contextPath}/resources/images/icons/signup-placeholder.png" alt="Join us" />
            </div>

            <h3>Discover the hidden gems!</h3>
            <p>
                Track your income, expenses, and savings easily.  
                Get control over your personal finances with MoneyHandler.
                So, What are you waiting for?
            </p>
        </div>

    </div>
</body>
</html>
