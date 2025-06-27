package EmployeeDbApp;

import dao.EmployeeDAO;
import model.Employee;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add\n2. View\n3. Update\n4. Delete\n5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = sc.nextDouble();
                    dao.addEmployee(new Employee(0, name, email, salary));
                }
                case 2 -> dao.viewEmployees();
                case 3 -> {
                    System.out.print("Enter ID to update: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("New name: ");
                    String name = sc.nextLine();
                    System.out.print("New email: ");
                    String email = sc.nextLine();
                    System.out.print("New salary: ");
                    double salary = sc.nextDouble();
                    dao.updateEmployee(new Employee(id, name, email, salary));
                }
                case 4 -> {
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteEmployee(id);
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
