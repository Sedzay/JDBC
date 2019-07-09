package lesson3hw3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ccepy0l4ctc8.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "TQZvd36H";

    //testSavePerformance() - который будет успешно добавлять 1000 записей в таблицу TEST_SPEED
    // c произвольными значениями
    //
    public long testSavePerformance() {

        TestSpeed testSpeed = new TestSpeed(0, "some text", 0);

        long startTime = System.currentTimeMillis();

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {

            for (int i = 1; i <= 10; i++) {
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
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
