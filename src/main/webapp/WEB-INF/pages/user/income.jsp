<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Income Records | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/income.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Header -->
<jsp:include page="/WEB-INF/pages/common/header.jsp" />

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
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main content -->
<main class="main-content">

    <div class="page-header">
        <h2>Income Records</h2>
        <div class="button-group">
            <a href="${contextPath}/pages/add_income.jsp" class="btn add-btn">+ Add Income</a>
            <a href="#" class="btn export-btn">Export Report</a>
        </div>
    </div>
    
    <!-- Alerts -->
	<c:if test="${param.success eq 'deleted'}">
	    <div class="alert success">Income deleted successfully.</div>
	</c:if>
	
	<c:if test="${param.error eq 'failed'}">
	    <div class="alert error">Failed to delete Income. Try again.</div>
	</c:if>
	
	<c:if test="${param.error eq 'invalid'}">
	    <div class="alert error">Invalid request. Income ID is incorrect or unauthorized.</div>
	</c:if>
	    

    <!-- Filter/Search Form -->
    <form method="get" action="${contextPath}/user/incomes" class="filter-form">
        <select name="type">
            <option value="all">All Types</option>
            <c:forEach var="type" items="${incomeTypes}">
                <option value="${type.typeName}" ${type.typeName == selectedType ? 'selected' : ''}>${type.typeName}</option>
            </c:forEach>
        </select>

        <input type="date" name="from" value="${fromDate}" placeholder="From date" />
        <input type="date" name="to" value="${toDate}" placeholder="To date" />
        <input type="text" name="search" placeholder="Search by source or amount..." value="${search}" />

        <button type="submit" class="btn filter-btn">Apply</button>
    </form>
    
    <form method="get" action="${contextPath}/user/incomes" class="search-form">
    <input type="text" name="search" placeholder="Search incomes..." value="${search}" />
    <button type="submit"><i class="fas fa-search"></i></button>
</form>
    

    <!-- Income Table -->
    <table class="data-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Type</th>
            <th>Source</th>
            <th>Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="income" items="${incomeList}">
            <tr>
                <td>${income.incomeId}</td>
                <td>Rs. ${income.amount}</td>
                <td>${income.typeName}</td>
                <td>${income.source}</td>
                <td>${income.date}</td>
                <td>
                    <a href="${contextPath}/user/income/edit?id=${income.incomeId}" title="Edit">
                        <img src="${contextPath}/resources/images/icons/edit.png" class="icon-btn" />
                    </a>
                    <a href="${contextPath}/user/income/delete?id=${income.incomeId}"
                       onclick="return confirm('Are you sure you want to delete this income?')"
                       title="Delete">
                        <img src="${contextPath}/resources/images/icons/delete.png" class="icon-btn" />
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty incomeList}">
            <tr>
                <td colspan="6" class="no-record">No records found.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</main>

<!-- Footer -->
<jsp:include page="/WEB-INF/pages/common/footer.jsp" />

</body>
</html>
