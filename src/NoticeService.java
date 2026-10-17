
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeService {

    public static void showNoticeMenu(Scanner sc) {

        int choice;

        do {
            System.out.println("\n=== NOTICE MANAGEMENT ===");
            System.out.println("1. View Notices");
            System.out.println("2. View Holidays");
            System.out.println("3. Back to Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewNotices();
                    break;

                case 2:
                    System.out.println("No holidays available yet.");
                    break;

                case 3:
                    System.out.println("Returning to Dashboard...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);
    }

    private static void viewNotices() {
    }

    private static void viewNotices(int organizationId) {

        String sql = "SELECT notice_id, title, description, notice_date " +
                "FROM notices " +
                "WHERE organization_id = ? " +
                "ORDER BY notice_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, organizationId);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println("\nNotice ID: " +
                            rs.getInt("notice_id"));
                    System.out.println("Title: " +
                            rs.getString("title"));
                    System.out.println("Description: " +
                            rs.getString("description"));
                    System.out.println("Date: " +
                            rs.getDate("notice_date"));
                    System.out.println("-------------------------");
                }

                if (!found) {
                    System.out.println("No notices available yet.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing notices: " + e.getMessage());
        }
    }
    }

