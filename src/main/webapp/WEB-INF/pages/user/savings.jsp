<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Savings | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/savings.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
        <li><a class="active" href="${contextPath}/user/savings"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/setting"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

	<form action="${contextPath}/user/savings/export" method="post">
	    <button type="submit" class="export-btn"><i class="fas fa-file-export"></i> Export Report</button>
	</form>

<!-- Main Content -->
<main>
    <h1 class="title">Your Savings Overview</h1>

    <div class="summary-card">
        <i class="fas fa-piggy-bank"></i>
        <div class="summary-text">
            <h2>Total Savings</h2>
            <strong>Rs. ${totalSavings}</strong>
        </div>
    </div>

    <h3>Monthly Breakdown</h3>
    <table>
        <thead>
            <tr>
                <th>Month</th>
                <th>Savings (Rs.)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${monthlySavings}">
                <tr>
                    <td>${entry.key}</td>
                    <td>Rs. ${entry.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="chart-container">
        <h3>Monthly Savings Chart</h3>
        <canvas id="savingsChart"></canvas>
    </div>
</main>

<script>
    const savingsLabels = [
        <c:forEach var="entry" items="${monthlySavings}" varStatus="status">
            "${entry.key}"<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    const savingsData = [
        <c:forEach var="entry" items="${monthlySavings}" varStatus="status">
            ${entry.value}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById('savingsChart'), {
        type: 'bar',
        data: {
            labels: savingsLabels,
            datasets: [{
                label: 'Savings (Rs.)',
                data: savingsData,
                backgroundColor: '#306844',
                borderRadius: 5
            }]
        },
        options: {
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>

</body>
</html>
