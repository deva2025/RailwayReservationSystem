package RailwayReservation;

import java.sql.*;
import java.util.Scanner;

public class AdminDAO {
    private Connection getConnection() throws SQLException {
        // Your database connection setup here
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayReservationManagement", "root", "root");
    }

    public void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Admin Menu:");
        System.out.println("1. Add Train");
        System.out.println("2. Delete Train");
        System.out.println("3. View Audit Log");
        System.out.println("4. View Passenger Details");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1:
                addTrain();
                break;
            case 2:
                deleteTrain();
                break;
            case 3:
                viewAuditLog();
                break;
            case 4:
                viewPassengerDetails();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addTrain() {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter train number: ");
        int trainNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter train name: ");
        String trainName = scanner.nextLine();

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Train (train_number, train_name) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, trainNumber);
            pstmt.setString(2, trainName);
            pstmt.executeUpdate();
            System.out.println("Train added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTrain() {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter train number to delete: ");
        int trainNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try (Connection conn = getConnection()) {
            // Check if the train exists
            String checkSql = "SELECT COUNT(*) FROM Train WHERE train_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, trainNumber);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                System.out.println("Train number not found.");
                return;
            }

            // Delete the train
            String deleteSql = "DELETE FROM Train WHERE train_number = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, trainNumber);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Train deleted successfully.");
            } else {
                System.out.println("Error deleting the train.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAuditLog() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM AuditLog";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Log ID: " + rs.getInt("log_id"));
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Action: " + rs.getString("action"));
                System.out.println("Timestamp: " + rs.getTimestamp("timestamp"));
                System.out.println("Details: " + rs.getString("details"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewPassengerDetails() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Passenger";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Passenger ID: " + rs.getInt("passenger_id"));
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("Nationality: " + rs.getString("nationality"));
                System.out.println("Phone Number: " + rs.getString("phone_number"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Age" + rs.getInt("age"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
