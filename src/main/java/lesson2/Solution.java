package lesson2;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private static final String JDBCDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "EkimovSergey";

    public ArrayList<Product> getAllProducts() {
        String query = "SELECT * FROM PRODUCT";
        return executeQuery(query);
    }

    public ArrayList<Product> getProductsByPrice() {
        String query = "SELECT * FROM PRODUCT WHERE PRICE <=100";
        return executeQuery(query);
    }

    public ArrayList<Product> getProductsByDescription() {

        String query = "SELECT x.ID, x.NAME, x.DESCRIPTION, x.PRICE FROM PRODUCT x, (SELECT ID, LENGTH(DESCRIPTION) as len FROM PRODUCT) y WHERE x.ID = y.ID AND y.len > 50";
        return executeQuery(query);
    }

    private ArrayList<Product> executeQuery (String query) {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()){

            try (ResultSet resultSet = statement.executeQuery(query)){
                while (resultSet.next()) {
                    products.add(getProduct(resultSet));
                }
            }

        }catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return products;
    }

    private Product getProduct(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        int price = resultSet.getInt(4);

        return new Product(id, name, description, price);
    }
}
