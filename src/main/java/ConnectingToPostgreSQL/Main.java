package ConnectingToPostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    // DB Connection URL
    private static final String URL = "jdbc:postgresql://localhost/testDB?user=postgres&password=****";

    // Status Messages
    private static final String conok = "Connection with DB is Successful. ";
    private static final String conerr = "Error with Connection to the DB. ";

    public static void main(String[] args) {
        // Connection to the DB
        try (Connection connection = DriverManager.getConnection(URL)) {
            System.out.printf("%s%n", conok); // Text IF connection is Successful.

            // SQL Queries
            String exampleSqlQuery = "SELECT * FROM test;";
            Statement statement = connection.createStatement(); // Creating object needed to send SQL queries.
            // One way to execute statement.
//            boolean isExecuted = statement.execute(exampleSqlQuery); // Boolean SQL Execution Logic.
//            if (isExecuted) {
//                System.out.println("SELECT Statement Executed");
//            }
            // Another way to execute statement.
            ResultSet resultSet = statement.executeQuery(exampleSqlQuery);
            System.out.println("ID");
            System.out.println("||---||");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("ID"));
            }
            System.out.println("||---||");
            statement.close(); // IMPORTANT to close Statement to save progress.
        } catch (SQLException sqlException) {
            System.out.printf("%s\n", conerr); // Text IF connection is NOT Successful.
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }
}
