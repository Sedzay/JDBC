package lesson3hw3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "EkimovSergej";

    //testSavePerformance() - который будет успешно добавлять 1000 записей в таблицу TEST_SPEED
    // c произвольными значениями
    //
    public long testSavePerformance() {

        TestSpeed testSpeed = new TestSpeed(0, "some text", 0);

        long startTime = System.currentTimeMillis();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {

            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setLong(1, testSpeed.getId() + i);
                preparedStatement.setString(2, testSpeed.getSomeString() + i);
                preparedStatement.setInt(3, testSpeed.getSomeNumber() + i);

                int res = preparedStatement.executeUpdate();
                System.out.println("save was finished with result " + res);
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return System.currentTimeMillis() - startTime;

        //Итоговое время: 165053
    }


    //testDeleteByIdPerformance() - будет удалять 1000 добавленных перед этим записей,
    // отдельными запросами по полю ID

    public long testDeleteByIdPerformance() {

        long startTime = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE id = (?)")) {

            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setLong(1, i);
                int res = preparedStatement.executeUpdate();

                System.out.println("delete was finished with result " + res);
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return System.currentTimeMillis() - startTime;

        //Итоговое время: 167910
    }


    //testDeletePerformance - будет удалять 1000, одним SQL запросом()

    public long testDeletePerformance() {
        long startTime = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM TEST_SPEED")) {

            int res = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return System.currentTimeMillis() - startTime;

        //Итоговое время: 3937
    }


    //testSelectByIdPerformance() - будет выбирать по очереди 1000 добавленных перед этим записей,
    // отдельными запросами по полю ID

    public long testSelectByIdPerformance() {

        long startTime = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM TEST_SPEED WHERE id = ?")) {

            List<TestSpeed> testSpeeds = new ArrayList<>();
            for (int i = 1; i <=1000; i++) {
                preparedStatement.setLong(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    TestSpeed testSpeed = new TestSpeed(resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3));
                    testSpeeds.add(testSpeed);
                    System.out.println("select " + i);
                }
            }
            System.out.println(testSpeeds);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return System.currentTimeMillis() - startTime;

        //Итоговое время: 166377
    }


    //testSelectPerformance() - будет выбирать 1000 записей, одним SQL запросом

    public long testSelectPerformance() {
        long startTime = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM TEST_SPEED")) {
            List<TestSpeed> testSpeeds = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TestSpeed testSpeed = new TestSpeed(resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3));
                testSpeeds.add(testSpeed);
            }
            System.out.println(testSpeeds);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return System.currentTimeMillis() - startTime;

        //Итоговое время: 20387
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
