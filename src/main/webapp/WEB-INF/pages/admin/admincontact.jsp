<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Contact Us | Admin</title>
    <link rel="stylesheet" href="${contextPath}/css/admincontact.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="Logo" />
    <ul>
        <li><a href="${contextPath}/admin/admindashboard"><i class="fas fa-chart-bar"></i> Dashboard</a></li>
        <li><a href="${contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/transactions"><i class="fas fa-list"></i> Transactions</a></li>
        <li><a href="${contextPath}/admin/categories"><i class="fas fa-table-cells-large"></i> Categories</a></li>
        <li><a href="${contextPath}/admin/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a class="active" href="${contextPath}/admin/admincontact"><i class="fas fa-envelope"></i> Contact</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Content -->
<div class="main">
    <div class="top-bar">
        <h2>Contact Us List</h2>
        <a href="${contextPath}/admin/export/contacts" class="btn-export"><i class="fas fa-file-pdf"></i> Export Contacts</a>
    </div>

    <c:if test="${not empty contacts}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Subject</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="contact" items="${contacts}">
                <tr>
                    <td>${contact.contactId}</td>
                    <td>${contact.name}</td>
                    <td>${contact.email}</td>
                    <td>${contact.subject}</td>
                    <td>
                        <form method="post" action="${contextPath}/admin/contact">
                            <input type="hidden" name="action" value="updateStatus"/>
                            <input type="hidden" name="contactId" value="${contact.contactId}"/>
                            <select name="status" onchange="this.form.submit()">
                                <option value="Pending" ${contact.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                <option value="Solved" ${contact.status == 'Solved' ? 'selected' : ''}>Solved</option>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${contextPath}/admin/contact" onsubmit="return confirm('Are you sure you want to delete this message?');">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="contactId" value="${contact.contactId}"/>
                            <button type="submit" <c:if test="${contact.status != 'Solved'}">disabled</c:if> title="Delete">
                                <i class="fas fa-trash"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty contacts}">
        <p>No contact messages found.</p>
    </c:if>
</div>

</body>
</html>
