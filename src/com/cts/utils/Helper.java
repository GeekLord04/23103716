package com.cts.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper {
    //reset the Auto Increment to 0
    public static void resetAutoIncrement(String tableName, int startValue) {
        String query = "ALTER TABLE " + tableName + " AUTO_INCREMENT = " + startValue;
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Check if the table is empty
    public static boolean isTableEmpty(String tableName) {
        boolean isEmpty = true;
        String query = "SELECT COUNT(*) FROM " + tableName;

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    isEmpty = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isEmpty;
    }
}
