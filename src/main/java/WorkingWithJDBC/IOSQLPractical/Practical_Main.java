package WorkingWithJDBC.IOSQLPractical;

import java.sql.*;
import java.util.Scanner;

public class Practical_Main {

    @SuppressWarnings("LoopStatementThatDoesntLoop") // It does loop. So ignore this.
    public static boolean checkedValue(int checkedValue) {
        String SQL = "Select * from test where ID=?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, checkedValue);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }

    private final static String HOST = "localhost"; // DB server
    private final static String DATABASENAME = "testDB";// DB name
    private final static String USERNAME = "postgres"; // Username
    private final static String PASSWORD = "P@r1$qLRUS@@@"; // User password
    static Connection connection;

    public static void main(String[] args) {

        // Connection to the DB
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        try {
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            if (connection == null) System.err.println("Error with DB Connection.");
            else {
                System.out.println("Successful Connection with DB");
                if (checkedValue(new Scanner(System.in).nextInt())) {
                    System.out.println("Data is present in the DB.");
                } else {
                    System.out.println("Data is not present in the DB.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


