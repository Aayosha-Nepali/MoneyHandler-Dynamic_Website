<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/dashboard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_tiny.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><a href="${contextPath}/user/userdashboard"><i class="fas fa-chart-line"></i> Dashboard</a></li>
        <li><a href="${contextPath}/user/report"><i class="fas fa-file-alt"></i> Reports</a></li>
        <li><a href="${contextPath}/user/income"><i class="fas fa-coins"></i> Incomes</a></li>
        <li><a href="${contextPath}/user/expense"><i class="fas fa-receipt"></i> Expenses</a></li>
        <li><a href="${contextPath}/user/saving"><i class="fas fa-piggy-bank"></i> Savings</a></li>
        <li><a href="${contextPath}/user/setting"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/user/profile"><i class="fas fa-user"></i> Profile</a></li>
        <li><a href="${contextPath}/user/help"><i class="fas fa-question-circle"></i> Help</a></li>
        <li><a href="${contextPath}/user/aboutus"><i class="fas fa-info-circle"></i> About Us</a></li>
        <li><a href="${contextPath}/user/contactus"><i class="fas fa-envelope"></i> Contact Us</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Dashboard -->
<main>
    <h1 class="dashboard-title">Welcome, ${sessionScope.currentUser.username} 👋</h1>

    <!-- Cards -->
    <div class="card-container">
        <div class="card">
            <i class="fas fa-arrow-down"></i>
            <div>Income</div>
            <strong>Rs. ${totalIncome}</strong>
        </div>
        <div class="card">
            <i class="fas fa-arrow-up"></i>
            <div>Expenses</div>
            <strong>Rs. ${totalExpense}</strong>
        </div>
        <div class="card">
            <i class="fas fa-piggy-bank"></i>
            <div>Savings</div>
            <strong>Rs. ${totalSaving}</strong>
        </div>
    </div>

    <!-- Charts -->
    <div class="chart-section">
        <h2>Financial Distribution</h2>
        <canvas id="pieChart"></canvas>
    </div>

    <div class="chart-section" style="margin-top: 3rem;">
        <h2>Monthly Income, Expense &amp; Savings</h2>
        <canvas id="lineChart"></canvas>
    </div>
</main>

<script>
    const labels = ${chartLabels};
    const incomeValues = ${incomeValues};
    const expenseValues = ${expenseValues};
    const savingValues = ${savingValues};

    // Pie Chart
    const pieChart = new Chart(document.getElementById('pieChart'), {
        type: 'pie',
        data: {
            labels: ['Income', 'Expenses', 'Savings'],
            datasets: [{
                data: [${totalIncome}, ${totalExpense}, ${totalSaving}],
                backgroundColor: ['#065465', '#aa6f73', '#306844'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });

    // Line Chart
    const lineChart = new Chart(document.getElementById('lineChart'), {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Income',
                    data: incomeValues,
                    borderColor: '#065465',
                    backgroundColor: 'rgba(6,84,101,0.2)',
                    fill: true,
                    tension: 0.4
                },
                {
                    label: 'Expenses',
                    data: expenseValues,
                    borderColor: '#aa6f73',
                    backgroundColor: 'rgba(170,111,115,0.2)',
                    fill: true,
                    tension: 0.4
                },
                {
                    label: 'Savings',
                    data: savingValues,
                    borderColor: '#306844',
                    backgroundColor: 'rgba(48,104,68,0.2)',
                    fill: true,
                    tension: 0.4
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom' }
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
