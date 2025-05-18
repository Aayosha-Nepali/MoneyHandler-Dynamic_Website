<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>User Profile | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/userprofile.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/user/userdashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/user/reports"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/user/income"><i class="fas fa-coins"></i> Incomes</a></li>
        <li><a href="${contextPath}/user/expenses"><i class="fas fa-receipt"></i> Expenses</a></li>
        <li><a href="${contextPath}/user/savings"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a class="active" href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/about"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contact"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Profile Content -->
<div class="profile-container">
    <h2>Your Profile</h2>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>

    <form action="${contextPath}/user/profile" method="post" enctype="multipart/form-data">
        <div class="profile-image">
            <img src="${contextPath}/uploads/user/${sessionScope.loggedInUser.imagePath}" alt="Profile Picture" />
            <label for="profilePicture">Change Picture</label>
            <input type="file" name="profilePicture" id="profilePicture" />
        </div>

        <div class="profile-info">
            <label>Username</label>
            <input type="text" name="username" value="${sessionScope.loggedInUser.username}" required />

            <label>Email (read-only)</label>
            <input type="email" value="${sessionScope.loggedInUser.email}" readonly />

            <label>Date of Birth</label>
            <input type="date" name="dob" value="${sessionScope.loggedInUser.dateOfBirth}" required />

            <label>Contact Number</label>
            <input type="text" name="contactnumber" value="${sessionScope.loggedInUser.contactNumber}" required />

            <button type="submit">Update Profile</button>
        </div>
    </form>
</div>

</body>
</html>
