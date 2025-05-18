package com.moneyhandler.service;

import com.moneyhandler.dao.TransactionDAO;

import java.util.*;

public class AdminDashboardService {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    public Map<String, List<?>> getLineChartData() {
        Map<String, Double> monthlyIncome = transactionDAO.getGlobalMonthlyIncome();
        Map<String, Double> monthlyExpense = transactionDAO.getGlobalMonthlyExpense();

        Set<String> allMonths = new LinkedHashSet<>();
        allMonths.addAll(monthlyIncome.keySet());
        allMonths.addAll(monthlyExpense.keySet());

        List<String> labels = new ArrayList<>();
        List<Double> income = new ArrayList<>();
        List<Double> expense = new ArrayList<>();
        List<Double> savings = new ArrayList<>();

        for (String month : allMonths) {
            labels.add(month);
            double inc = monthlyIncome.getOrDefault(month, 0.0);
            double exp = monthlyExpense.getOrDefault(month, 0.0);
            income.add(inc);
            expense.add(exp);
            savings.add(inc - exp);
        }

        Map<String, List<?>> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("income", income);
        chartData.put("expense", expense);
        chartData.put("savings", savings);

        return chartData;
    }
}
