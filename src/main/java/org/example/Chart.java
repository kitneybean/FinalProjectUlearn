package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chart {

    public static void main(String[] args) {
        // Подключение к базе данных SQLite
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            // Получение среднего количества студентов для каждой страны
            CategoryDataset dataset = createDataset(connection);

            // Создание графика
            JFreeChart chart = ChartFactory.createBarChart(
                    "Среднее количество студентов в странах",
                    "Страна",
                    "Среднее количество студентов",
                    dataset
            );

            // Отображение графика
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            chartPanel.setEnforceFileExtensions(false);

            javax.swing.JFrame frame = new javax.swing.JFrame("Среднее количество студентов в странах");
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(chartPanel);
            frame.pack();
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static CategoryDataset createDataset(Connection connection) throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Список выбранных стран
        String[] countries = {"Alameda", "Butte", "Fresno", "San Joaquin", "Kern", "Tulare", "Monterey", "Sacramento", "Los Angeles", "Ventura"};

        for (String country : countries) {
            // SQL-запрос для получения среднего количества студентов в стране
            String query = "SELECT AVG(school_stat.students) AS avg_students " +
                    "FROM schools " +
                    "JOIN school_stat ON schools.school_id = school_stat.school_id " +
                    "WHERE schools.county = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, country);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Добавление данных в датасет для графика
                if (resultSet.next()) {
                    double avgStudents = resultSet.getDouble("avg_students");
                    dataset.addValue(avgStudents, "Среднее количество студентов", country);
                }
            }
        }

        return dataset;
    }
}
