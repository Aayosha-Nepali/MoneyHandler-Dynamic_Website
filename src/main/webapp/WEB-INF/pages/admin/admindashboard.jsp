<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard | MoneyHandler</title>
    <link rel="stylesheet" href="${contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="Logo" />
    <ul>
        <li><a class="active" href="${contextPath}/admindashboard"><i class="fas fa-chart-bar"></i> Dashboard</a></li>
        <li><a href="${contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/transactions"><i class="fas fa-exchange-alt"></i> Transactions</a></li>
        <li><a href="${contextPath}/admin/categories"><i class="fas fa-layer-group"></i> Categories</a></li>
        <li><a href="${contextPath}/admin/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/admin/contact"><i class="fas fa-envelope"></i> Contact</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>

<!-- Main Section -->
<main>
    <h1 class="dashboard-title">Welcome, ${sessionScope.loggedInUser.username} 👑 </h1>

    <!-- Cards -->
    <div class="card-container">
        <div class="card">
            <i class="fas fa-user"></i>
            <div>Users</div>
            <strong>${userCount}</strong>
        </div>
        <div class="card">
            <i class="fas fa-arrow-down"></i>
            <div>Total Incomes</div>
            <strong>${incomeCount}</strong>
        </div>
        <div class="card">
            <i class="fas fa-arrow-up"></i>
            <div>Total Expenses</div>
            <strong>${expenseCount}</strong>
        </div>
    </div>

    <!-- Pie Chart -->
    <div class="chart-section">
        <h2>Overall Income, Expense & Savings</h2>
        <canvas id="adminPieChart"></canvas>
    </div>

    <!-- Line Chart -->
    <div class="chart-section" style="margin-top: 2rem;">
        <h2>Monthly Trends</h2>
        <canvas id="adminLineChart"></canvas>
    </div>
</main>

<script>
	const labels = ${chartLabels != null ? chartLabels : '[]'};
	const incomeData = ${incomeValues != null ? incomeValues : '[]'};
	const expenseData = ${expenseValues != null ? expenseValues : '[]'};
	const savingData = ${savingValues != null ? savingValues : '[]'};
</script>

<script>
    new Chart(document.getElementById('adminPieChart'), {
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

    new Chart(document.getElementById('adminLineChart'), {
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
