package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.IncomeTypeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for income types (used in dropdowns and display).
 */
public class IncomeTypeDAO {

    public List<IncomeTypeModel> getAllTypes() {
        List<IncomeTypeModel> types = new ArrayList<>();
        String sql = "SELECT IncomeTypeID, TypeName, Source FROM IncomeType";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                IncomeTypeModel type = new IncomeTypeModel();
                type.setIncomeTypeId(rs.getInt("IncomeTypeID"));
                type.setTypeName(rs.getString("TypeName"));
                type.setSource(rs.getString("Source"));
                types.add(type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }
}
