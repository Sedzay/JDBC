package lesson4;

import lesson3.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDemo {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "EkimovSergej";



    public void save(List<Product> products) {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            connection.setAutoCommit(false);

            for (Product product : products) {
                saveList(product, connection);
            }
            connection.commit();

        } catch (SQLException e) {
            /*System.err.println("Something went wrong");
            e.printStackTrace();*/
        }
    }

    private void saveList(Product product, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {

            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());

            int res = preparedStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            connection.rollback();
            System.out.println(e.getMessage() + " id: " + product.getId());
            throw e;
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
