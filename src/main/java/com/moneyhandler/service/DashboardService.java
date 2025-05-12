package com.moneyhandler.service;

import com.moneyhandler.dao.TransactionDAO;

import java.util.*;

/**
 * Service for user dashboard logic.
 */
public class DashboardService {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    public double getTotalIncome(int userId) {
        return transactionDAO.getTotalAmount(userId, "income");
    }

    public double getTotalExpense(int userId) {
        return transactionDAO.getTotalAmount(userId, "expense");
    }

    public double getTotalSavings(int userId) {
        return getTotalIncome(userId) - getTotalExpense(userId);
    }

    public Map<String, List<?>> getLineChartData(int userId) {
        Map<String, Double> monthlyIncome = transactionDAO.getMonthlyIncomeData(userId);
        Map<String, Double> monthlyExpense = transactionDAO.getMonthlyExpenseData(userId);

        Set<String> months = new LinkedHashSet<>();
        months.addAll(monthlyIncome.keySet());
        months.addAll(monthlyExpense.keySet());

        List<String> labels = new ArrayList<>();
        List<Double> income = new ArrayList<>();
        List<Double> expense = new ArrayList<>();
        List<Double> savings = new ArrayList<>();

        for (String month : months) {
            labels.add(month);
            double inc = monthlyIncome.getOrDefault(month, 0.0);
            double exp = monthlyExpense.getOrDefault(month, 0.0);
            income.add(inc);
            expense.add(exp);
            savings.add(inc - exp);
        }

        Map<String, List<?>> chartData = new LinkedHashMap<>();
        chartData.put("labels", labels);
        chartData.put("income", income);
        chartData.put("expense", expense);
        chartData.put("savings", savings);

        return chartData;
    }
}
