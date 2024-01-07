package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Parser {

    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/schools.csv";

        // Подключение к базе данных SQLite
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            // Создание таблиц Schools и School_stat
            createTables(connection);

            // Парсинг CSV и вставка данных в базу данных
            parseCsvAndInsertData(connection, csvFilePath);

            System.out.println("Данные успешно загружены в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        // Создание таблицы Schools
        String createSchoolsTable = "CREATE TABLE IF NOT EXISTS Schools (" +
                "school_id INTEGER PRIMARY KEY, " +
                "district TEXT, " +
                "school TEXT, " +
                "county TEXT)";
        connection.createStatement().executeUpdate(createSchoolsTable);

        // Создание таблицы School_stat
        String createSchoolStatTable = "CREATE TABLE IF NOT EXISTS School_stat (" +
                "school_id INTEGER PRIMARY KEY, " +
                "grades TEXT, " +
                "students INTEGER, " +
                "teachers DOUBLE, " +
                "calworks DOUBLE, " +
                "lunch DOUBLE, " +
                "computer INTEGER, " +
                "expenditure DOUBLE, " +
                "income DOUBLE, " +
                "english DOUBLE, " +
                "read DOUBLE, " +
                "math DOUBLE)";
        connection.createStatement().executeUpdate(createSchoolStatTable);
    }

    private static void parseCsvAndInsertData(Connection connection, String csvFilePath) throws SQLException {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath)).withSkipLines(1).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Вставка данных в таблицу Schools
                String insertSchoolsQuery = "INSERT INTO Schools (school_id, district, school, county) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSchoolsQuery)) {
                    preparedStatement.setInt(1, Integer.parseInt(line[0]));
                    preparedStatement.setString(2, line[1]);
                    preparedStatement.setString(3, line[2]);
                    preparedStatement.setString(4, line[3]);
                    preparedStatement.executeUpdate();
                }

                // Вставка данных в таблицу School_stat
                String insertSchoolStatQuery = "INSERT INTO School_stat (school_id, grades, students, teachers, calworks, lunch, computer, expenditure, income, english, read, math) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSchoolStatQuery)) {
                    preparedStatement.setInt(1, Integer.parseInt(line[0]));
                    preparedStatement.setString(2, line[4]);
                    preparedStatement.setInt(3, Integer.parseInt(line[5]));
                    preparedStatement.setDouble(4, Double.parseDouble(line[6]));
                    preparedStatement.setDouble(5, Double.parseDouble(line[7]));
                    preparedStatement.setDouble(6, Double.parseDouble(line[8]));
                    preparedStatement.setInt(7, Integer.parseInt(line[9]));
                    preparedStatement.setDouble(8, Double.parseDouble(line[10]));
                    preparedStatement.setDouble(9, Double.parseDouble(line[11]));
                    preparedStatement.setDouble(10, Double.parseDouble(line[12]));
                    preparedStatement.setDouble(11, Double.parseDouble(line[13]));
                    preparedStatement.setDouble(12, Double.parseDouble(line[14]));
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
