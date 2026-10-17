
import java.util.Scanner;

public class DashboardService {

    public static void showDashboard(
            Scanner sc,
            int organizationId,
            String organizationName) {

        int choice = 0;

        do {
            System.out.println("\n===== HRMS DASHBOARD =====");
            System.out.println("Organization: " + organizationName);
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Notice / Holiday Management");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    EmployeeService.employeeMenu(sc, organizationId);
                    break;

                case 2:
                 AttendanceService.showAttendanceMenu(sc, organizationId);
                    break;

                case 3:
                   SalaryService.showSalaryMenu(sc);
                    break;

                case 4:
                    NoticeService.showNoticeMenu(sc);
                    break;

                case 5:
                    System.out.println("Logout successful.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }
}
