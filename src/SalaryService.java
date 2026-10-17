import java.util.Scanner;

public class SalaryService {

    public static void showSalaryMenu(Scanner sc) {

        int choice;

        do {
            System.out.println("\n===== SALARY MANAGEMENT =====");
            System.out.println("1. Calculate Employee Salary");
            System.out.println("2. Back to Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Employee ID: ");
                    int employeeId = sc.nextInt();

                    System.out.print("Enter Basic Salary: ");
                    double basicSalary = sc.nextDouble();

                    System.out.print("Enter HRA: ");
                    double hra = sc.nextDouble();

                    System.out.print("Enter Allowances: ");
                    double allowances = sc.nextDouble();

                    System.out.print("Enter Deductions: ");
                    double deductions = sc.nextDouble();

                    if (basicSalary < 0 || hra < 0
                            || allowances < 0 || deductions < 0) {
                        System.out.println(
                                "Salary values cannot be negative."
                        );
                        break;
                    }

                    double grossSalary =
                            basicSalary + hra + allowances;

                    double netSalary =
                            grossSalary - deductions;

                    System.out.println("\n===== SALARY DETAILS =====");
                    System.out.println("Employee ID: " + employeeId);
                    System.out.printf(
                            "Basic Salary: %.2f%n", basicSalary);
                    System.out.printf("HRA: %.2f%n", hra);
                    System.out.printf(
                            "Allowances: %.2f%n", allowances);
                    System.out.printf(
                            "Deductions: %.2f%n", deductions);
                    System.out.printf(
                            "Gross Salary: %.2f%n", grossSalary);
                    System.out.printf(
                            "Net Salary: %.2f%n", netSalary);

                    break;

                case 2:
                    System.out.println(
                            "Returning to dashboard...");
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }

        } while (true);
    }
}