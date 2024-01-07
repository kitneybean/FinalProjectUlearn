package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HighMathSchools {

    public static void main(String[] args) {
        // Подключение к базе данных SQLite
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            // Получение данных по учебным заведениям
            getHighMathSchools(connection, 5000, 7500);
            getHighMathSchools(connection, 10000, 11000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getHighMathSchools(Connection connection, int minStudents, int maxStudents) {
        // SQL-запрос для получения учебных заведений с указанным количеством студентов и самым высоким показателем по математике
        String query = "SELECT s.school, ss.math " +
                "FROM Schools s " +
                "JOIN School_stat ss ON s.school_id = ss.school_id " +
                "WHERE ss.students BETWEEN ? AND ? " +
                "ORDER BY ss.math DESC " +
                "LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minStudents);
            preparedStatement.setInt(2, maxStudents);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String school = resultSet.getString("school");
                    double mathScore = resultSet.getDouble("math");
                    System.out.println("Учебное заведение: " + school + ", Количество студентов: " +
                            minStudents + " - " + maxStudents + ", Высший показатель по математике: " + mathScore);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
