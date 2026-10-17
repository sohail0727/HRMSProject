
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AttendanceService {

    public static void showAttendanceMenu(Scanner sc, int organizationId) {
        int choice=0;

        do {
            System.out.println("\n=== ATTENDANCE MANAGEMENT ===");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Back to Dashboard");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    markAttendance(sc);
                    break;

                case 2:
                    viewAttendance();
                    break;

                case 3:
                    System.out.println("Returning to dashboard...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 3);
    }

    private static void markAttendance(Scanner sc) {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter attendance date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.println("1. PRESENT");
            System.out.println("2. ABSENT");
            System.out.println("3. LEAVE");
            System.out.print("Enter status choice: ");

            int statusChoice = Integer.parseInt(sc.nextLine());

            String status;

            switch (statusChoice) {
                case 1:
                    status = "PRESENT";
                    break;
                case 2:
                    status = "ABSENT";
                    break;
                case 3:
                    status = "LEAVE";
                    break;
                default:
                    System.out.println("Invalid status choice.");
                    return;
            }

            String sql = "INSERT INTO attendance " +
                    "(employee_id, attendance_date, status) VALUES (?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setString(3, status);

                ps.executeUpdate();

                System.out.println("Attendance marked successfully!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid employee ID or date.");
            System.out.println("Use the date format YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void viewAttendance() {
        String sql = "SELECT attendance_id, employee_id, " +
                "attendance_date, status FROM attendance " +
                "ORDER BY attendance_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            System.out.println("\n=== ATTENDANCE RECORDS ===");

            while (rs.next()) {
                found = true;

                System.out.println(
                        "Attendance ID: " + rs.getInt("attendance_id") +
                                " | Employee ID: " + rs.getInt("employee_id") +
                                " | Date: " + rs.getDate("attendance_date") +
                                " | Status: " + rs.getString("status")
                );
            }

            if (!found) {
                System.out.println("No attendance records found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
