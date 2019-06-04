import java.sql.*;
import java.util.Date;

public class JDBCExemples {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "TQZvd36H";

    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()){

//            boolean res = statement.execute("INSERT INTO PRODUCT_ VALUES (2, 'toy111', 'for children', 60)");
//            System.out.println(res);

//            boolean res = statement.execute("DELETE FROM PRODUCT_ WHERE NAME = 'toy111'");
//            System.out.println(res);

//            int response = statement.executeUpdate("INSERT INTO PRODUCT_ VALUES (5, 'car', 'for children', 770)");
//            System.out.println(response);

            int response = statement.executeUpdate("DELETE FROM PRODUCT_ WHERE NAME = 'car'");
            System.out.println(response);

        }catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
