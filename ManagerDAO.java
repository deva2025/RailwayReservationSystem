package RailwayReservation;

import java.sql.*;
import java.util.Scanner;

public class ManagerDAO {
   

    public void showManagerMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Manager Menu:");
        System.out.println("1. Update Schedule");
        System.out.println("2. Search Train Details by Name");
        System.out.println("3. Update Delay");
        System.out.println("4. Update Base Fare");
        System.out.println("5. Update Promotions");
        System.out.println("6. View Passenger Details");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1:
                updateSchedule();
                break;
            case 2:
                searchTrainDetailsByName();
                break;
            case 3:
                updateDelay();
                break;
            case 4:
                updateBaseFare();
                break;
            case 5:
                updatePromotions();
                break;
            case 6:
                viewPassengerDetails();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void updateSchedule() {
        Scanner scanner = new Scanner(System.in);

        // Input schedule ID to update
        System.out.print("Enter schedule ID to update: ");
        int scheduleId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Input new values
        System.out.print("Enter new departure time (HH:mm): ");
        String departureTime = scanner.nextLine();

        System.out.print("Enter new arrival time (HH:mm): ");
        String arrivalTime = scanner.nextLine();

        System.out.print("Enter new days of operation (comma-separated, e.g., Monday,Wednesday): ");
        String daysOfOperation = scanner.nextLine();

        System.out.print("Enter new travel date (YYYY-MM-DD): ");
        String travelDate = scanner.nextLine();

        // SQL query to update the schedule
        String updateQuery = "UPDATE Schedule SET departure_time = ?, arrival_time = ?, days_of_operation = ?, travel_date = ? WHERE schedule_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set parameters
            preparedStatement.setString(1, departureTime);
            preparedStatement.setString(2, arrivalTime);
            preparedStatement.setString(3, daysOfOperation);
            preparedStatement.setDate(4, Date.valueOf(travelDate));
            preparedStatement.setInt(5, scheduleId);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule updated successfully.");
            } else {
                System.out.println("No schedule found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void searchTrainDetailsByName() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the train name to search:");
        String trainName = scanner.nextLine();
        
        // SQL to search for train details
        String query = "SELECT * FROM Train WHERE train_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, trainName);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int trainId = rs.getInt("train_id");
                String name = rs.getString("train_name");
                String number = rs.getString("train_number");
                
                System.out.println("Train ID: " + trainId);
                System.out.println("Train Name: " + name);
                System.out.println("Train Number: " + number);
            } else {
                System.out.println("No train found with the name: " + trainName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateDelay() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the train ID to update delay:");
        int trainId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter the delay reason:");
        String delayReason = scanner.nextLine();
        
        System.out.println("Enter the delay duration (HH:MM:SS):");
        String delayDuration = scanner.nextLine();
        
        System.out.println("Enter your user ID (for updating):");
        int updatedBy = scanner.nextInt();
        
        // SQL to insert a new delay record
        String query = "INSERT INTO Delay (train_id, delay_reason, delay_duration, updated_by) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, trainId);
            pstmt.setString(2, delayReason);
            pstmt.setString(3, delayDuration);
            pstmt.setInt(4, updatedBy);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Delay updated successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateBaseFare() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the fare ID to update:");
        int fareId = scanner.nextInt();
        
        System.out.println("Enter the new base fare:");
        double baseFare = scanner.nextDouble();
        
        // SQL to update base fare
        String query = "UPDATE Fare SET base_fare = ? WHERE fare_id = ?";
        
        try (Connection conn =DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDouble(1, baseFare);
            pstmt.setInt(2, fareId);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Base fare updated successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePromotions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the promotion ID to update:");
        int promotionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter the new fare ID for the promotion:");
        int fareId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter the new discount percentage (e.g., 10 for 10%):");
        double discountPercentage = scanner.nextDouble();

        System.out.println("Enter the new start date (YYYY-MM-DD):");
        String startDate = scanner.next();

        System.out.println("Enter the new end date (YYYY-MM-DD):");
        String endDate = scanner.next();

        // SQL to update the promotion
        String query = "UPDATE Promotions SET fare_id = ?, discount_percentage = ?, start_date = ?, end_date = ? WHERE promotion_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, fareId);
            pstmt.setDouble(2, discountPercentage);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setInt(5, promotionId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Promotion updated successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void viewPassengerDetails() {
        try (Connection conn =DatabaseConnection.getConnection()) {
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
