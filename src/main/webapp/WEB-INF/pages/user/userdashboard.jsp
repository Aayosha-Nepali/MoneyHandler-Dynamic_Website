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
    <img src="${contextPath}/resources/images/logo/MoneyHandler_small.png" class="sidebar-logo" alt="MoneyHandler Logo" />
    <ul>
        <li><i class="fas fa-wallet"></i> Wallet</li>
        <li><i class="fas fa-plus-circle"></i> Add Income</li>
        <li><i class="fas fa-minus-circle"></i> Add Expense</li>
        <li><i class="fas fa-chart-line"></i> Reports</li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Dashboard -->
<main>
    <h1 class="dashboard-title">Welcome, ${sessionScope.currentUser.username} 👋</h1>

    <!-- Dashboard Cards -->
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

    <!-- Pie Chart -->
    <div class="chart-section">
        <h2>Financial Distribution</h2>
        <canvas id="pieChart"></canvas>
    </div>

    <!-- Line Chart -->
    <div class="chart-section" style="margin-top: 3rem;">
		<h2>Monthly Income, Expense &amp; Savings</h2>
        <canvas id="lineChart"></canvas>
    </div>
</main>

<!-- Charts Script -->
<script>
    const pieCtx = document.getElementById('pieChart').getContext('2d');
    const pieChart = new Chart(pieCtx, {
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

    const lineCtx = document.getElementById('lineChart').getContext('2d');
    const lineChart = new Chart(lineCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'], // Replace with dynamic labels if available
            datasets: [
                {
                    label: 'Income',
                    data: [10000, 15000, 12000, 18000, 20000, 22000], // Replace with dynamic values
                    borderColor: '#065465',
                    backgroundColor: 'rgba(6,84,101,0.2)',
                    fill: true,
                    tension: 0.4
                },
                {
                    label: 'Expenses',
                    data: [5000, 7000, 8000, 6000, 9000, 8500], // Replace with dynamic values
                    borderColor: '#aa6f73',
                    backgroundColor: 'rgba(170,111,115,0.2)',
                    fill: true,
                    tension: 0.4
                },
                {
                    label: 'Savings',
                    data: [5000, 8000, 4000, 12000, 11000, 13500], // Replace with dynamic values
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
