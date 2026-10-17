
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {
            System.out.println(
                    "MySQL connection successful!");
        } catch (SQLException e) {
            System.out.println(
                    "Database connection failed!");
            e.printStackTrace();
        }
    }
}