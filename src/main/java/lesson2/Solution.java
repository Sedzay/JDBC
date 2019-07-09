package lesson2;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private static final String JDBCDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "EkimovSergej";

    //void increasePrice() - увеличивает значение цены на 100, во всех продуктах, где цена меньше 970

    public void increasePrice() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("UPDATE PRODUCT SET price = price + 100 WHERE price < 970");
            System.out.println(response);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    //void changeDescription() - удаляет последнее предложение с описания всех продуктов с длинной описания больше 100

    public void changeDescription() {
        //1. Отобрать все продукты, у которых в описании больше 100 символов  +
        //2. Разделить такое описание по предложениям  +
        //3. Удалить последнее предложение  +
        //4. Обновить описание в продукте в базе данных  +

        ArrayList<Product> productArrayList = getProductsByDescription();

        for (Product product : productArrayList) {

            String[] allStrings = product.getDescription().split("\\.");

            if (allStrings.length <= 1)
                continue;

            String newDescription = "";
            for (int i = 0; i < allStrings.length-1; i++) {
                newDescription += allStrings[i];
                newDescription += ".";
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

                int response = statement.executeUpdate("UPDATE PRODUCT SET description = " + "\'" + newDescription + "\'" + " WHERE " + product.getId() + " = id");
                System.out.println(response);

            } catch (SQLException e) {
                System.err.println("Something went wrong");
                e.printStackTrace();
            }

        }
    }

    private ArrayList<Product> getProductsByDescription() {

        ArrayList<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, DESCRIPTION, PRICE FROM PRODUCT WHERE LENGTH(DESCRIPTION) > 100")) {
                while (resultSet.next()) {
                    products.add(getProduct(resultSet));
                }
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return products;
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        int price = resultSet.getInt(4);

        return new Product(id, name, description, price);
    }

}
