package WorkingWithJDBC.ConnectingToPostgreSQL;

import WorkingWithJDBC.PSGSQL_User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("CommentedOutCode")
public class Main {
    // DB Connection URL
    private final static String HOST = PSGSQL_User.getHOST();
    private final static String DATABASENAME = PSGSQL_User.getDATABASENAME();
    private final static String USERNAME = PSGSQL_User.getUSERNAME();
    private final static String PASSWORD = PSGSQL_User.getPASSWORD();
    private static final String URL = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;

    // Status Messages
    private static final String conok = "Connection with DB is Successful. ";
    private static final String conerr = "Error with Connection to the DB. ";

    public static void main(String[] args) {
        // Connection to the DB
        try (Connection connection = DriverManager.getConnection(URL)) {
            System.out.printf("%s%n", conok); // Text IF connection is Successful.
            // SQL Query
            String exampleSqlQuery = "SELECT * FROM test;";
            // PrepareStatement Initialisation
            try(PreparedStatement preparedStatement = connection.prepareStatement(exampleSqlQuery)) { // IMPORTANT to close Statement to save progress. Try-with-resources makes it easier to do.
                // Another way to execute statement.
                ResultSet resultSet = preparedStatement.executeQuery(exampleSqlQuery);
                System.out.println("ID");
                System.out.println("||---||");
                while (resultSet.next()){
                    System.out.println(resultSet.getInt("ID"));
                }
                System.out.println("||---||");
            }

            // Normal Statement Initialisation
//            Statement statement = connection.createStatement(); // Creating object needed to send SQL queries.

            // One way to execute statement.
//            boolean isExecuted = statement.execute(exampleSqlQuery); // Boolean SQL Execution Logic.
//            if (isExecuted) {
//                System.out.println("SELECT Statement Executed");
//            }
        } catch (SQLException sqlException) {
            System.out.printf("%s\n", conerr); // Text IF connection is NOT Successful.
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }
}
