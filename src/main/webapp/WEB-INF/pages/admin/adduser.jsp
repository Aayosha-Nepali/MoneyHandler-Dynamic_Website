<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Add User | MoneyHandler Admin</title>
    <link rel="stylesheet" href="${contextPath}/css/addincome.css" />
</head>
<body>

<div class="container">
    <div class="form-section">
        <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="logo" alt="Logo" />
        <h2>Add New User</h2>

        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>
        <c:if test="${not empty success}">
            <p class="success-message">${success}</p>
        </c:if>

        <form action="${contextPath}/adduser" method="post">
            <input type="text" name="username" placeholder="Username" value="${param.username}" required />
            <input type="email" name="email" placeholder="Email" value="${param.email}" required />
            <input type="date" name="dob" value="${param.dob}" required />
            <input type="text" name="contactnumber" placeholder="Contact Number" value="${param.contactnumber}" required />
            <input type="password" name="password" placeholder="Password" required />
            <input type="password" name="retypePassword" placeholder="Retype Password" required />

            <button type="submit">Add User</button>
        </form>

        <div class="signin-link" style="margin-top: 1rem;">
            <a href="${contextPath}/admin/users">← Back to Users</a>
        </div>

        <!-- User Table Section -->
        <h3 style="margin-top: 3rem; color: #2c4c3b;">Recently Added Users</h3>
        <div class="table-container">
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Contact</th>
                        <th>DOB</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Example rows; replace with JSTL loop if dynamic -->
                    <tr>
                        <td>001</td>
                        <td>John Doe</td>
                        <td>john@example.com</td>
                        <td>9800000000</td>
                        <td>2000-01-01</td>
                    </tr>
                    <tr>
                        <td>002</td>
                        <td>Jane Smith</td>
                        <td>jane@example.com</td>
                        <td>9800000001</td>
                        <td>2002-03-15</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- End Table -->
    </div>

    <div class="side-section">
        <button class="signup-btn">Admin Panel</button>
        <span class="join-text">Quick Actions</span>
        <div class="image-placeholder">
            <img src="${contextPath}/resources/images/icons/signup-placeholder.png" alt="Admin UI" />
        </div>
        <h3>Manage with Ease</h3>
        <p>Use this form to manually register users into the system.</p>
    </div>
</div>

</body>
</html>
