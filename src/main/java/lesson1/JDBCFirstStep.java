package lesson1;

import java.sql.*;

public class JDBCFirstStep {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "TQZvd36H";

    public static void main(String[] args) {
        //1. DB Driver
        //2. Create connection
        //3. Create query/statement
        //4. execute query
        //5. work with result
        //6. close all the connections

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()){
            try {
                Class.forName(JDBC_DRIVER);
            }catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
                return;
            }

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Test")){
                while (resultSet.next()) {
                    System.out.println("Object found");
                }
            }

        }catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
