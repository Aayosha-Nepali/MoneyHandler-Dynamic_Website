<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Manage Categories | Admin</title>
    <link rel="stylesheet" href="${contextPath}/css/category.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <img src="${contextPath}/resources/images/logo/MoneyHandler_big.png" class="sidebar-logo" alt="Logo" />
    <ul>
        <li><a href="${contextPath}/admin/admindashboard"><i class="fas fa-chart-bar"></i> Dashboard</a></li>
        <li><a href="${contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
        <li><a href="${contextPath}/admin/transactions"><i class="fas fa-exchange-alt"></i> Transactions</a></li>
        <li><a class="active" href="${contextPath}/admin/categories" class="active"><i class="fas fa-layer-group"></i> Categories</a></li>
        <li><a href="${contextPath}/admin/settings"><i class="fas fa-cog"></i> Settings</a></li>
        <li><a href="${contextPath}/admin/contact"><i class="fas fa-envelope"></i> Contact</a></li>
        <li><a href="${contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</div>
	<c:if test="${not empty message}">
    	<div class="alert-message">${message}</div>
	</c:if>


<!-- Main Section -->
<main>
    <h1>Manage Categories</h1>

    <!-- Add Category Form -->
    <form method="post" class="category-add-form">
        <input type="hidden" name="action" value="add" />
        <select name="type" required>
            <option value="">-- Select Type --</option>
            <option value="income">Income</option>
            <option value="expense">Expense</option>
        </select>
        <input type="text" name="name" placeholder="Category Name" required />
        <button type="submit"><i class="fas fa-plus-circle"></i> Add Category</button>
    </form>

    <div class="category-section">
        <!-- Income Categories -->
        <div class="category-box">
            <h2>Income Categories</h2>
            <c:forEach var="category" items="${incomeCategories}">
                <form method="post" class="category-item">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="categoryId" value="${category.categoryId}" />
                    <input type="text" name="name" value="${category.categoryName}" />
                    <button type="submit" title="Update"><i class="fas fa-edit"></i></button>
                </form>
                <form method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="categoryId" value="${category.categoryId}" />
                    <button type="submit" onclick="return confirm('Delete this category?')" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </form>
            </c:forEach>
        </div>

        <!-- Expense Categories -->
        <div class="category-box">
            <h2>Expense Categories</h2>
            <c:forEach var="category" items="${expenseCategories}">
                <form method="post" class="category-item">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="categoryId" value="${category.categoryId}" />
                    <input type="text" name="name" value="${category.categoryName}" />
                    <button type="submit" title="Update"><i class="fas fa-edit"></i></button>
                </form>
                <form method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="categoryId" value="${category.categoryId}" />
                    <button type="submit" onclick="return confirm('Delete this category?')" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </form>
            </c:forEach>
        </div>
    </div>
</main>

</body>
</html>
