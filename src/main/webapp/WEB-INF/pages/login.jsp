<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login | MoneyHandler</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css" />
</head>
<body>
    <div class="container">
        <!-- LEFT SIDE: Login Form -->
        <div class="form-section">
            <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" alt="MoneyHandler Logo" class="logo" />
            <h2>Sign In Now!</h2>

            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>

            <form action="${contextPath}/login" method="post">
                <input type="email" name="email" placeholder="Email address" value="${savedEmail != null ? savedEmail : ''}" required />
                <input type="password" name="password" placeholder="Password" required />
                <button type="submit">Login &#x27A4;</button>
            </form>

            <p class="signup-link">
                Don’t have an account? <a href="${contextPath}/registration">Sign up</a>
            </p>
        </div>

        <!-- RIGHT SIDE: Info -->
        <div class="side-section">
            <button class="signup-btn">Sign up</button>
            <span class="join-text">Join us</span>
            
            <div class="image-placeholder">
                <img src="${contextPath}/resources/images/logo/MoneyHandler_medium.png" alt="Join us" />
            </div>
            
            <h3>Discover the hidden gems!</h3>
            <p>Track your income, expenses, and savings easily with MoneyHandler.
            Login now!!!</p>
        </div>
    </div>
</body>
</html>
