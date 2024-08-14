package org.example;


import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://ep-noisy-sun-a5xe7udr.us-east-2.aws.neon.tech:5432/jevaval592db";
    private static final String USER = "jevaval592db_owner";
    private static final String PASSWORD = "j9Msf1UPDSZw";

    //Підключення до БД
    private static Connection connection;

    public static void main(String[] args)  {
        try {
            //createTable();


            Abonent ivan = new Abonent();
            ivan.setName("Підкаблучник Іван Васильович");
            ivan.setPhoneNumber("098 78 67 567");
            ivan.setAddress("м. Рівне вул. Соборна 24, кв.45");
            ivan.setEmail("ivan@gmail.com");

            String insertSQL = "INSERT INTO Phonebook (Name, PhoneNumber, Email, Address) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = null;

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Створення PreparedStatement для виконання SQL-запиту
            preparedStatement = connection.prepareStatement(insertSQL);

            // Встановлення значень для параметрів запиту
            preparedStatement.setString(1, ivan.getName());
            preparedStatement.setString(2, ivan.getPhoneNumber());
            preparedStatement.setString(3, ivan.getEmail());
            preparedStatement.setString(4, ivan.getAddress());

            // Виконання SQL-запиту
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new contact was inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Щось пішло не так! " + e.getMessage());
        }
    }

    private static void createTable() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL-запит для створення таблиці
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Phonebook (" +
                    "ID SERIAL PRIMARY KEY, " +
                    "Name VARCHAR(100) NOT NULL, " +
                    "PhoneNumber VARCHAR(15) NOT NULL, " +
                    "Email VARCHAR(100), " +
                    "Address VARCHAR(255)" +
                    ");";
            Statement command = connection.createStatement();

            // Виконання SQL-запиту на створення таблиці
            command.execute(createTableSQL);
            System.out.println("Успішно створено таблицю Phonebook :)");

        } catch (Exception e) {
            System.out.println("Помилка зяднання до БД!!!");;
        }
        finally {
            if (connection!=null)
                connection.close();
        }
    }
}