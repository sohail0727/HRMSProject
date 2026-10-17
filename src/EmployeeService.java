
import java.sql.*;
import java.util.Scanner;

public class EmployeeService {

    public static void employeeMenu(
            Scanner sc, int organizationId) {

        int choice;

        do {
            System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back to Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(sc, organizationId);
                    break;

                case 2:
                    viewEmployees(organizationId);
                    break;

                case 3:
                    updateEmployee(sc, organizationId);
                    break;
                case 4:
                    deleteEmployee(sc,organizationId);
                    break;

                case 5:
                    System.out.println("Returning to dashboard...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    private static void addEmployee(
            Scanner sc, int organizationId) {

        System.out.print("Employee name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        System.out.print("Designation: ");
        String designation = sc.nextLine();

        System.out.print("Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Employee name cannot be empty.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Please enter a valid email address.");
            return;
        }

        if (salary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }

        String checkSql = "SELECT employee_id FROM employees WHERE organization_id = ? AND LOWER(email) = LOWER(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            checkPs.setInt(1, organizationId);
            checkPs.setString(2, email.trim());

            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("This email already exists in your organization.");
                    return;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking employee email: " + e.getMessage());
            return;
        }



        String sql = "INSERT INTO employees " +
                "(organization_id, employee_name, email, " +
                "department, designation, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, organizationId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, department);
            ps.setString(5, designation);
            ps.setDouble(6, salary);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee added successfully!");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not add employee: " + e.getMessage());
        }
    }

    private static void viewEmployees(int organizationId) {

        String sql = "SELECT employee_id, employee_name, email, " +
                "department, designation, salary " +
                "FROM employees WHERE organization_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, organizationId);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\n=== EMPLOYEE LIST ===");

                while (rs.next()) {
                    System.out.println(
                            "ID: " + rs.getInt("employee_id"));
                    System.out.println(
                            "Name: " + rs.getString("employee_name"));
                    System.out.println(
                            "Email: " + rs.getString("email"));
                    System.out.println(
                            "Department: " + rs.getString("department"));
                    System.out.println(
                            "Designation: " + rs.getString("designation"));
                    System.out.println(
                            "Salary: " + rs.getBigDecimal("salary"));
                    System.out.println("----------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not fetch employees: " + e.getMessage());
        }
    }

    private static void updateEmployee(Scanner sc, int organizationId) {
        System.out.print("Enter Employee ID to update: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter new Email: ");
        String email = sc.nextLine();

        System.out.print("Enter new Department: ");
        String department = sc.nextLine();

        System.out.print("Enter new Designation: ");
        String designation = sc.nextLine();

        System.out.print("Enter new Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        String sql = "UPDATE employees SET employee_name = ?, email = ?, " +
                "department = ?, designation = ?, salary = ? " +
                "WHERE employee_id = ? AND organization_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setString(4, designation);
            ps.setDouble(5, salary);
            ps.setInt(6, employeeId);
            ps.setInt(7, organizationId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found!");
            }

        } catch (SQLException e) {
            System.out.println("Could not update employee: " + e.getMessage());
        }
    }

    private static void deleteEmployee(Scanner sc, int organizationId) {
        System.out.print("Enter Employee ID to delete: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Are you sure you want to delete this employee? (yes/no): ");
        String confirmation = sc.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        String sql = "DELETE FROM employees "
                + "WHERE employee_id = ? AND organization_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, organizationId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println(
                        "Employee not found in your organization."
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not delete employee: " + e.getMessage()
            );
        }
    }

}
