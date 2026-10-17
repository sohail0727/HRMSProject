
import java.util.Scanner;

public class Project {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n===== HRMS PROJECT =====");
            System.out.println("1. Create New Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    OrganizationService.registerOrganization(sc);
                    break;

                case 2:
                  LoginService.login(sc);
                    break;

                   case 3:
                    SalaryService.showSalaryMenu(sc);
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }

        } while (choice != 3);

        sc.close();
    }
}
