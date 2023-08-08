package WorkingWithJDBC.DataManipulationPractical;

import WorkingWithJDBC.PSGSQL_User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DMP_Main {
    private final static String HOST = PSGSQL_User.getHOST();
    private final static String DATABASENAME = PSGSQL_User.getDATABASENAME();
    private final static String USERNAME = PSGSQL_User.getUSERNAME();
    private final static String PASSWORD = PSGSQL_User.getPASSWORD();

    @SuppressWarnings("ConstantValue")
    public static void main(String[] args) {
        // URL for DB Connection
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        // Connection to the DB
        try {
            Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            if (connection == null)
                System.err.println("No connection with DB.");
            else {
                System.out.println("Connected to DB.");
            }

            // SQL Query Processing
            String SQL = "UPDATE test set name='Alex' where ID=10;"; // Example SQL Query
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.executeUpdate();
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
}

