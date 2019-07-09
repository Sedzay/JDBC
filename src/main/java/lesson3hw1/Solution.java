package lesson3hw1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "EkimovSergey";

    //findProductsByPrice(int price, int delta) - будет искать продукты с заданной ценной
    // в диапазоне +=delta включительно. Например, если нужно найти продукты с ценой 100 и дельтой 10,
    // то ищем все от 90 до 110
    //
    public List<Product> findProductsByPrice(int price, int delta) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE price >= (? - ?) AND price <= (? + ?)")) {

            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, delta);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, delta);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    //findProductsByName(String word) - продукты, которые содержат в своем имене слово word.
    // Если word является некоректным (больше одного слова в стринге, длина меньше 3, содержит спецсимволы),
    // выбрасывать ошибку, которая в описании обязательно должна содержать само слово и описание ошибки
    //
    public List<Product> findProductsByName(String word) throws Exception {

        validationWord(word);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE name LIKE \'%?%\'")) {

            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    //findProductsWithEmptyDescription() - продукты с пустым полем описания

    public List<Product> findProductsWithEmptyDescription() {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE description IS NULL");

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    private void validationWord(String word) throws Exception {
        if (word.length() < 3)
            throw new Exception("word  -" + word + "-  less than three characters");

        char[] chars = word.toCharArray();
        for (char ch : chars) {
            if (!Character.isLetter(ch))
                throw new Exception("word  -" + word + "-  is not correct");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
