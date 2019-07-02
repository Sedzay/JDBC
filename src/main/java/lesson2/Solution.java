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
        String query = "UPDATE PRODUCT SET price = price + 100 WHERE price < 970";
        executeQueryUpdate(query);
    }


    //void changeDescription() - удаляет последнее предложение с описания всех продуктов с длинной описания больше 100

    public void changeDescription() {
        //1. Отобрать все продукты, у которых в описании больше 100 символов  +
        //2. Разделить такое описание по предложениям  +
        //3. Удалить последнее предложение  +
        //4. Обновить описание в продукте в базе данных  +

        ArrayList<Product> productArrayList = getProductsByDescription();

        for (Product product: productArrayList) {

            String[]allStrings = product.getDescription().split("\\.");

            if(allStrings.length > 1) {
                allStrings[allStrings.length-1] = null;
                String newDescription = null;
                for (String s : allStrings) {
                    if(newDescription == null)
                        newDescription = s;
                    else if (s != null){
                        newDescription += ".";
                        newDescription += s;
                    }
                }
                String query = "UPDATE PRODUCT SET description = " + "\'"+ newDescription + "\'"+ " WHERE " + product.getId() + " = id";
                executeQueryUpdate(query);
            }
        }
    }

    private ArrayList<Product> getProductsByDescription() {

        String query = "SELECT ID, NAME, DESCRIPTION, PRICE FROM PRODUCT WHERE LENGTH(DESCRIPTION) > 100";
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

    private void executeQueryUpdate(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()){

            int response = statement.executeUpdate(query);
            System.out.println(response);

        }catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private Product getProduct(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        int price = resultSet.getInt(4);

        return new Product(id, name, description, price);
    }




//------------------------------------------------------
    //из предыдущих заданий
    public ArrayList<Product> getAllProducts() {
        String query = "SELECT * FROM PRODUCT_";
        return executeQuery(query);
    }

    public ArrayList<Product> getProductsByPrice() {
        String query = "SELECT * FROM PRODUCT_ WHERE PRICE <=100";
        return executeQuery(query);
    }
}
