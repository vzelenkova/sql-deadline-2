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

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "test_user", "test_password");
    }

    public static DataHelper.VerificationCode getVerificationCode() throws SQLException {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            throw exception;
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
    @SneakyThrows
    public static void fillDatabase() {
        var connection = getConn();
        runner.execute(connection, "INSERT INTO test_db.users (id,login,password,status) VALUES\n" +
                "\t ('4271ff5d-4982-4169-8ab1-3e670f0e88e6','vasya','$2a$10$knL6Dw619udjKbjynB2zjO35vqJHMXineuU.biX34td/bHaD1FNnW','active'),\n" +
                "\t ('c599249d-5a5e-438d-bbd0-ff8e40301d3d','petya','$2a$10$vDUJNyZLaEAtVRgkm2lFq.3rs6B2olULPeIQUHIfugw56Hz5StKi2','active');\n");
        runner.execute(connection, "INSERT INTO test_db.cards (id,user_id,`number`,balance_in_kopecks) VALUES\n" +
                "\t ('0f3f5c2a-249e-4c3d-8287-09f7a039391d','4271ff5d-4982-4169-8ab1-3e670f0e88e6','5559 0000 0000 0002',1000000),\n" +
                "\t ('92df3f1c-a033-48e6-8390-206f6b1f56c0','4271ff5d-4982-4169-8ab1-3e670f0e88e6','5559 0000 0000 0001',1000000);\n");
    }

}