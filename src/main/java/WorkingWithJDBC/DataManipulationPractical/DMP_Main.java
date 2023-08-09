package WorkingWithJDBC.DataManipulationPractical;

import WorkingWithJDBC.PSGSQL_User;

import java.sql.*;
import java.util.Scanner;

public class DMP_Main {
    private final static String HOST = PSGSQL_User.getHOST();
    private final static String DATABASENAME = PSGSQL_User.getDATABASENAME();
    private final static String USERNAME = PSGSQL_User.getUSERNAME();
    private final static String PASSWORD = PSGSQL_User.getPASSWORD();
    static Connection connection;

    public static void main(String[] args) {
        // URL for DB Connection
        String URL = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            sqlException.printStackTrace();
        }

        // Connection to the DB
        try {
            connection.setAutoCommit(false);
            if (connection == null)
                System.err.println("No connection with DB.");
            else {
                System.out.println("Connected to DB.");
            }

            if (checkValue(new Scanner(System.in).nextInt())) {
                System.out.println("Number exists already.");
            } else {
                System.out.println("Number doesn't exists.");
            }

            if (insertValue(new Scanner(System.in).nextInt())) {
                System.out.println("Number added to DB.");
            } else {
                System.out.println("Number not added to DB.");
            }

            // SQL Query Processing
            String SQL = "INSERT INTO test(ID,name) VALUES(?,?);"; // Example SQL Query
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, 10);
                preparedStatement.setString(2, "Olaf");
                preparedStatement.addBatch();

                preparedStatement.setInt(1, 11);
                preparedStatement.setString(2, "Erik");
                preparedStatement.addBatch();

                preparedStatement.setInt(1, 12);
                preparedStatement.setString(2, "Alex");
                preparedStatement.addBatch();

                int[] count = preparedStatement.executeBatch();
                connection.commit();
                System.out.println("Transaction is Successful.");
            } catch (SQLException sqlException) {
                connection.rollback();
                System.out.println("Transaction is not successful.");
                sqlException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public static boolean checkValue(int checkedValue) {
        String SQL = "SELECT * FROM test WHERE ID=?;";
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

    @SuppressWarnings("RedundantIfStatement")
    public static boolean insertValue(int insertedValue) {
        String SQL = "INSERT INTO test(id) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, insertedValue);
            int i = statement.executeUpdate();
            if (i >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}

