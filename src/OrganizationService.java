
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OrganizationService {

    public static void registerOrganization(Scanner sc) {

        System.out.println("\n=== Organization Registration ===");

        System.out.print("Organization name: ");
        String name = sc.nextLine();

        System.out.print("Address: ");
        String address = sc.nextLine();

        System.out.print("PAN: ");
        String pan = sc.nextLine();

        System.out.print("GST: ");
        String gst = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        System.out.print("Login email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Employee count: ");
        int employeeCount;

        try {
            employeeCount = Integer.parseInt(sc.nextLine());

            if (employeeCount < 0) {
                System.out.println("Employee count cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid employee count.");
            return;
        }

        String sql = """
                INSERT INTO organizations
                (organization_name, address, pan, gst,
                 department, email, password_hash, employee_count)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        // Temporary demonstration only:
        // Replace this with a secure password-hashing implementation
        // before using this for real accounts.
        String passwordHash = password;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, pan);
            ps.setString(4, gst);
            ps.setString(5, department);
            ps.setString(6, email);
            ps.setString(7, passwordHash);
            ps.setInt(8, employeeCount);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Organization registered successfully!");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Registration failed. Check your input and database.");
            e.printStackTrace();
        }
    }
}
