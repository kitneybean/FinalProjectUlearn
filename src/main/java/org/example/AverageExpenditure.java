package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AverageExpenditure {

    public static void main(String[] args) {
        // Подключение к базе данных SQLite
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            // Получение данных по среднему количеству расходов
            getAverageExpenditureByCounty(connection, "Fresno");
            getAverageExpenditureByCounty(connection, "Contra Costa");
            getAverageExpenditureByCounty(connection, "El Dorado");
            getAverageExpenditureByCounty(connection, "Glenn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getAverageExpenditureByCounty(Connection connection, String county) {
        // SQL-запрос для получения среднего количества расходов по указанному округу
        String query = "SELECT AVG(ss.expenditure) as average_expenditure " +
                "FROM Schools s " +
                "JOIN School_stat ss ON s.school_id = ss.school_id " +
                "WHERE s.county = ? AND ss.expenditure > 10";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, county);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double averageExpenditure = resultSet.getDouble("average_expenditure");
                    System.out.println("Среднее количество расходов в " + county + ": " + averageExpenditure);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

