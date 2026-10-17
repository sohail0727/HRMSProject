
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginService {

    public static void login(Scanner sc) {

        System.out.println("\n===== ORGANIZATION LOGIN =====");

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String sql = "SELECT organization_id, organization_name "
                + "FROM organizations "
                + "WHERE email = ? AND password_hash = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int organizationId =
                            rs.getInt("organization_id");

                    String organizationName =
                            rs.getString("organization_name");

                    System.out.println(
                            "\nWelcome to " + organizationName);

                    DashboardService.showDashboard(
                            sc, organizationId, organizationName);

                } else {
                    System.out.println(
                            "Invalid email or password.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Login failed.");
            e.printStackTrace();
        }
    }
}
