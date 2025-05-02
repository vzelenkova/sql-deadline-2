package ru.netology.bank.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }


    public static String getVerificationCode(String login) {
    String code = null;
    String query = "SELECT code FROM auth_codes ac JOIN users u ON ac.user_id = u.id WHERE u.login = ? ORDER BY ac.created DESC LIMIT 1;";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "test_user", "test_password");
        PreparedStatement stmt = conn.prepareStatement(query)) 
    {
        stmt.setString(1, login);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                code = rs.getString("code");
            }
        }
    } 
        catch (SQLException e) {
        e.printStackTrace();
    }
        return code;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
}
