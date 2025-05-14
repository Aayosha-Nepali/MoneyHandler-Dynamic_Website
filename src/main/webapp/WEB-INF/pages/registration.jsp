<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Form | MoneyHandler</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/registration.css" />
</head>
<body>
    <div class="container">
        <div class="form-section">
            <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png"
                 alt="MoneyHandler Logo" class="logo" />

            <h2>Sign Up Now!</h2>

            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

			<form action="${contextPath}/registration" method="post" enctype="multipart/form-data">
                <input type="text" name="username" placeholder="Username" value="${username}" required>
                <input type="email" name="email" placeholder="Email address" value="${email}" required>
                <input type="date" name="dob" value="${dob}" required>
                <input type="text" name="contact" placeholder="Phone Number" value="${phone}" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="password" name="retypePassword" placeholder="Retype Password" required>
                <label for="profilePicture" class="upload-label">Upload Profile Picture:</label>
				<input type="file" name="profilePicture" accept="image/*">
                

                <button type="submit">Register &#x27A4;</button>
            </form>

            <p class="signin-link">
                Already have an account? <a href="${contextPath}/login">Sign in</a>
            </p>
        </div>

        <div class="side-section">
            <button class="signup-btn">Sign up</button>
            <span class="join-text">Join us</span>

            <div class="image-placeholder">
                <img src="${contextPath}/resources/images/logo/MoneyHandler_medium.png" alt="Join us" />
            </div>

            <h3>Discover the hidden gems!</h3>
            <p>Track your income, expenses, and savings easily.  
                Get control over your personal finances with MoneyHandler.</p>
               <p>So, What are you waiting for?</p>
        </div>
    </div>
</body>
</html>