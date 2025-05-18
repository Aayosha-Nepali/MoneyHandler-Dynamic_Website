<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Expense Records | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/dashboard.css" />
    <link rel="stylesheet" href="${contextPath}/css/income.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/user/userdashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/user/report"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/user/income"><i class="fas fa-coins"></i> Incomes</a></li>
        <li><a class="active" href="${contextPath}/user/expense"><i class="fas fa-receipt"></i> Expenses</a></li>
        <li><a href="${contextPath}/user/saving"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/setting"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Section -->
<main>
    <h1 class="dashboard-title">Expense Records</h1>
    

	<!-- Alerts -->
	<c:if test="${param.success eq 'deleted'}">
	    <div class="alert success">Expense deleted successfully.</div>
	</c:if>
	
	<c:if test="${param.error eq 'failed'}">
	    <div class="alert error">Failed to delete expense. Try again.</div>
	</c:if>
	
	<c:if test="${param.error eq 'invalid'}">
	    <div class="alert error">Invalid request. Expense ID is incorrect or unauthorized.</div>
	</c:if>

    <!-- Filter & Search -->
    <form method="get" action="${contextPath}/user/expenses" class="filter-form">
        <select name="type">
            <option value="all">All Types</option>
            <c:forEach var="type" items="${expenseTypeList}">
                <option value="${type.typeName}" <c:if test="${type.typeName == selectedType}">selected</c:if>>
                    ${type.typeName}
                </option>
            </c:forEach>
        </select>

        <input type="date" name="from" value="${fromDate}" />
        <input type="date" name="to" value="${toDate}" />
        <input type="text" name="search" placeholder="Search..." value="${searchQuery}" />
        <button type="submit"><i class="fas fa-filter"></i> Filter</button>
    </form>

    <!-- Actions -->
    <div class="actions">
        <a href="${contextPath}/user/add-expense" class="btn btn-primary">
            <i class="fas fa-plus-circle"></i> Add Expense
        </a>
        <a href="#" class="btn btn-secondary">
            <i class="fas fa-file-export"></i> Export Report
        </a>
    </div>

    <!-- Expense Table -->
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Amount</th>
                    <th>Vendor</th>
                    <th>Type</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="expense" items="${expenseList}">
                    <tr>
                        <td>${expense.expenseId}</td>
                        <td>Rs. ${expense.amount}</td>
                        <td>${expense.vendor}</td>
                        <td>${expense.typeName}</td>
                        <td>${expense.date}</td>
                        <td>
                            <a href="${contextPath}/user/update-expense?id=${expense.expenseId}" class="edit-icon">
                                <i class="fas fa-edit"></i>
                            </a>
                            <a href="${contextPath}/user/delete-expense?id=${expense.expenseId}" class="delete-icon"
                               onclick="return confirm('Are you sure you want to delete this expense?')">
                                <i class="fas fa-trash-alt"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty expenseList}">
                    <tr>
                        <td colspan="6" style="text-align: center;">No records found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</main>

</body>
</html>
