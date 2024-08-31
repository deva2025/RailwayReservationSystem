package RailwayReservation;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        AdminDAO adminDAO = new AdminDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        PassengerDAO passengerDAO = new PassengerDAO();

        while (true) {
            System.out.println("Welcome to the Train Reservation System");
            System.out.println("1. Login");
            System.out.println("2. Register");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                if (choice == 1) {
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    User user = userDAO.loginUser(username, password);
                    if (user != null) {
                        System.out.println("Login successful. Welcome " + user.getUsername());
                        switch (user.getUserType()) {
                        case "Admin":
                            adminDAO.showAdminMenu();
                            break;
                        case "Manager":
                            managerDAO.showManagerMenu();
                            break;
                        case "Passenger":
                            passengerDAO.showPassengerMenu();
                            break;
                            default:
                                System.out.println("Invalid user type.");
                        }
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                } else if (choice == 2) {
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter User Type (Admin/Manager/Passenger): ");
                    String userType = scanner.nextLine();

                    User user = new User(choice, username, email, password, phoneNumber, userType);
                    userDAO.registerUser(user);
                    System.out.println("Registration successful. Please login.");
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Consume invalid input
            }
        }
    }

    
    }

