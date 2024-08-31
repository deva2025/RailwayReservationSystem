package RailwayReservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PassengerDAO {

    // Method to search trains by name
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

	// Method to search for trains by source, destination, and date
	  private void searchTrainsBySourceDestinationDate(String source, String destination, String date) {
	      try (Connection connection = DatabaseConnection.getConnection()) {
	          // Step 1: Find Route IDs based on source and destination
	          String routeSql = "SELECT route_id FROM Route " +
	                            "WHERE source_station_id = (SELECT station_id FROM Station WHERE station_name = ?) " +
	                            "AND destination_station_id = (SELECT station_id FROM Station WHERE station_name = ?)";
	          PreparedStatement routeStmt = connection.prepareStatement(routeSql);
	          routeStmt.setString(1, source);
	          routeStmt.setString(2, destination);
	          ResultSet routeRs = routeStmt.executeQuery();

	          List<Integer> routeIds = new ArrayList<>();
	          while (routeRs.next()) {
	              routeIds.add(routeRs.getInt("route_id"));
	          }

	          if (routeIds.isEmpty()) {
	              System.out.println("No route found for the given source and destination.");
	              return;
	          }

	          // Step 2: Find Train IDs from TrainRoute table based on route IDs
	          String trainRouteSql = "SELECT train_id FROM TrainRoute WHERE route_id IN (" +
	                                 String.join(", ", Collections.nCopies(routeIds.size(), "?")) + ")";
	          PreparedStatement trainRouteStmt = connection.prepareStatement(trainRouteSql);
	          for (int i = 0; i < routeIds.size(); i++) {
	              trainRouteStmt.setInt(i + 1, routeIds.get(i));
	          }
	          ResultSet trainRouteRs = trainRouteStmt.executeQuery();

	          List<Integer> trainIds = new ArrayList<>();
	          while (trainRouteRs.next()) {
	              trainIds.add(trainRouteRs.getInt("train_id"));
	          }

	          if (trainIds.isEmpty()) {
	              System.out.println("No trains found for the given route.");
	              return;
	          }

	          // Step 3: Find Schedule IDs from TrainSchedule table based on train IDs and date
	          String trainScheduleSql = "SELECT DISTINCT ts.train_id " +
	                                    "FROM TrainSchedule ts " +
	                                    "JOIN Schedule s ON ts.schedule_id = s.schedule_id " +
	                                    "WHERE ts.train_id IN (" +
	                                    String.join(", ", Collections.nCopies(trainIds.size(), "?")) + ") " +
	                                    "AND s.date = ?";
	          PreparedStatement trainScheduleStmt = connection.prepareStatement(trainScheduleSql);
	          for (int i = 0; i < trainIds.size(); i++) {
	              trainScheduleStmt.setInt(i + 1, trainIds.get(i));
	          }
	          trainScheduleStmt.setString(trainIds.size() + 1, date);
	          ResultSet trainScheduleRs = trainScheduleStmt.executeQuery();

	          List<Integer> availableTrainIds = new ArrayList<>();
	          while (trainScheduleRs.next()) {
	              availableTrainIds.add(trainScheduleRs.getInt("train_id"));
	          }

	          if (availableTrainIds.isEmpty()) {
	              System.out.println("No trains are available for the given criteria.");
	              return;
	          }

	          // Step 4: Retrieve Train Details
	          String trainDetailsSql = "SELECT id, name FROM Train WHERE id IN (" +
	                                   String.join(", ", Collections.nCopies(availableTrainIds.size(), "?")) + ")";
	          PreparedStatement trainDetailsStmt = connection.prepareStatement(trainDetailsSql);
	          for (int i = 0; i < availableTrainIds.size(); i++) {
	              trainDetailsStmt.setInt(i + 1, availableTrainIds.get(i));
	          }
	          ResultSet trainDetailsRs = trainDetailsStmt.executeQuery();

	          System.out.println("Available trains:");
	          while (trainDetailsRs.next()) {
	              System.out.println("Train ID: " + trainDetailsRs.getInt("id") + ", Name: " + trainDetailsRs.getString("name"));
	          }
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }
	  }


    
    
   

	// Method to check PNR status
    private void checkPnrStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter PNR number: ");
        String pnrNumber = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Booking WHERE pnr_number = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pnrNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("PNR Number: " + rs.getString("pnr_number"));
                System.out.println("Train ID: " + rs.getInt("train_id"));
                System.out.println("Schedule ID: " + rs.getInt("schedule_id"));
                System.out.println("Seat ID: " + rs.getString("seat_id"));
                System.out.println("Status: " + rs.getString("booking_status"));
                System.out.println("Booking Date: " + rs.getTimestamp("booking_date"));
            } else {
                System.out.println("Invalid PNR Number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to show the passenger menu
    public void showPassengerMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Passenger Menu:");
        System.out.println("1. Search Trains");
        System.out.println("2. Book Ticket");
        System.out.println("3. Cancel Ticket");
        System.out.println("4. Check PNR Status");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1:
                searchTrainsMenu();
                break;
            case 2:
                bookTicket();
                break;
            case 3:
               cancelTicket(DatabaseConnection.getConnection(), scanner);
                break;
            case 4:
                checkPnrStatus();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to display the search trains menu
    private void searchTrainsMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Trains Menu:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Source, Destination, and Date");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1:
            	searchTrainDetailsByName();
                break;
            case 2:
                System.out.print("Enter source: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination: ");
                String destination = scanner.nextLine();
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                searchTrainsBySourceDestinationDate(source, destination, date);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
 
    	   public void bookTicket() throws SQLException {
    	        Scanner scanner = new Scanner(System.in);
    	        
    	        // Prompt user for details
    	       boolean continueBooking = true;

        while (continueBooking) {
            // Step 1: Collect Train ID and Berth Preference (used for user preference, not querying)
            System.out.print("Enter Train ID: ");
            int trainId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Berth Preference (Upper/Lower/Middle): ");
            String berthPreference = scanner.nextLine(); // Taken as preference only

            // Step 2: Check Seat Availability (without filtering by berth preference)
            String seatQuery = "SELECT seat_id, seat_number FROM Seat WHERE train_id = ? AND availability_status = 'Available'";
            try (PreparedStatement seatStmt =DatabaseConnection.getConnection().prepareStatement(seatQuery)) {
                seatStmt.setInt(1, trainId);
                ResultSet seatsResultSet = seatStmt.executeQuery();

                if (!seatsResultSet.next()) {
                    System.out.println("No seats available. Checking RAC and Waiting List...");

                    // Check RAC
                    String racQuery = "SELECT seat_id, seat_number FROM Seat WHERE train_id = ? AND availability_status = 'RAC'";
                    try (PreparedStatement racStmt = DatabaseConnection.getConnection().prepareStatement(racQuery)) {
                        racStmt.setInt(1, trainId);
                        ResultSet racResultSet = racStmt.executeQuery();
                        if (racResultSet.next()) {
                            System.out.println("RAC Seats Available:");
                            do {
                                int seatId = racResultSet.getInt("seat_id");
                                String seatNumber = racResultSet.getString("seat_number");
                                System.out.println("Seat ID: " + seatId + ", Seat Number: " + seatNumber);
                            } while (racResultSet.next());

                            // Choose RAC Seat
                            System.out.print("Choose RAC Seat ID: ");
                            int selectedRacSeatId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            // Proceed with booking for RAC
                            handleBooking(DatabaseConnection.getConnection(), scanner, trainId, selectedRacSeatId);
                        } else {
                            // Check Waiting List
                            String waitingListQuery = "SELECT seat_id, seat_number FROM Seat WHERE train_id = ? AND availability_status = 'Waiting'";
                            try (PreparedStatement waitingStmt = DatabaseConnection.getConnection().prepareStatement(waitingListQuery)) {
                                waitingStmt.setInt(1, trainId);
                                ResultSet waitingResultSet = waitingStmt.executeQuery();
                                if (waitingResultSet.next()) {
                                    System.out.println("Waiting List Seats Available:");
                                    do {
                                        int seatId = waitingResultSet.getInt("seat_id");
                                        String seatNumber = waitingResultSet.getString("seat_number");
                                        System.out.println("Seat ID: " + seatId + ", Seat Number: " + seatNumber);
                                    } while (waitingResultSet.next());

                                    // Choose Waiting List Seat
                                    System.out.print("Choose Waiting List Seat ID: ");
                                    int selectedWaitingSeatId = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    // Proceed with booking for Waiting List
                                    handleBooking( DatabaseConnection.getConnection(), scanner, trainId, selectedWaitingSeatId);
                                } else {
                                    System.out.println("No seats available in RAC or Waiting List.");
                                    // Suggest other trains or dates
                                    suggestAlternativeTrains( DatabaseConnection.getConnection(), trainId);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Available Seats:");
                    do {
                        int seatId = seatsResultSet.getInt("seat_id");
                        String seatNumber = seatsResultSet.getString("seat_number");
                        System.out.println("Seat ID: " + seatId + ", Seat Number: " + seatNumber);
                    } while (seatsResultSet.next());

                    // Step 3: Seat Selection and Additional Services
                    System.out.print("Choose Seat ID: ");
                    int selectedSeatId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Proceed with booking
                    handleBooking(DatabaseConnection.getConnection(), scanner, trainId, selectedSeatId);
                }
            }

            // Ask if the user wants to book another ticket
            System.out.print("Do you want to book another ticket (yes/no)? ");
            String continueResponse = scanner.nextLine();
            continueBooking = continueResponse.equalsIgnoreCase("yes");
        }

    } 
 

    
    private static void handleBooking(Connection connection, Scanner scanner, int trainId, int seatId) throws SQLException {
        double totalAmount = 0.0;

        // Additional Services: Luggage Booking
        System.out.print("Do you want to book luggage (yes/no)? ");
        String luggageBooking = scanner.nextLine();
        if (luggageBooking.equalsIgnoreCase("yes")) {
            System.out.print("Enter luggage weight in kg: ");
            double luggageWeight = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            double luggageCost = calculateLuggageCost(luggageWeight);
            totalAmount += luggageCost;
            System.out.println("Luggage booked. Cost: " + luggageCost);
        }

        // Additional Services: Pet Booking
        System.out.print("Do you want to book a pet (yes/no)? ");
        String petBooking = scanner.nextLine();
        if (petBooking.equalsIgnoreCase("yes")) {
            double petCost = 50.0; // Example fixed cost for pet booking
            totalAmount += petCost;
            System.out.println("Pet booked. Cost: " + petCost);
        }

        // Display Food Details and Book
        String foodQuery = "SELECT food_id, food_name, price FROM Food";
        try (PreparedStatement foodStmt = connection.prepareStatement(foodQuery);
             ResultSet foodResultSet = foodStmt.executeQuery()) {
            System.out.println("Available Food:");
            while (foodResultSet.next()) {
                int foodId = foodResultSet.getInt("food_id");
                String foodName = foodResultSet.getString("food_name");
                double foodPrice = foodResultSet.getDouble("price");
                System.out.println("Food ID: " + foodId + ", Name: " + foodName + ", Price: " + foodPrice);
            }
        }

        // Food Ordering
        System.out.print("Do you want to order food (yes/no)? ");
        String foodOrder = scanner.nextLine();
        if (foodOrder.equalsIgnoreCase("yes")) {
            System.out.print("Enter the Food ID you want to order: ");
            int foodId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            totalAmount += getFoodPrice(connection, foodId);
        }

        // Payment Process
        System.out.print("Enter payment method (Credit/Debit/NetBanking): ");
        String paymentMethod = scanner.nextLine();
        processPayment(paymentMethod, totalAmount);

        // Confirm booking and show details
        System.out.println("Booking confirmed with Seat ID: " + seatId);
        System.out.println("PNR Number: " + generatePNR());
        System.out.println("Total Amount: " + totalAmount);
    }

    private static double calculateLuggageCost(double weight) {
        // Example calculation: $10 per kg
        return weight * 10.0;
    }

    private static double getFoodPrice(Connection connection, int foodId) throws SQLException {
        String foodPriceQuery = "SELECT price FROM Food WHERE food_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(foodPriceQuery)) {
            stmt.setInt(1, foodId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        }
        return 0.0;
    }

    private static void processPayment(String paymentMethod, double amount) {
        // Dummy payment processing logic
        System.out.println("Processing payment of $" + amount + " via " + paymentMethod + "...");
        System.out.println("Payment successful!");
    }

    private static void suggestAlternativeTrains(Connection connection, int trainId) throws SQLException {
        // Logic to suggest alternative trains and dates
        System.out.println("Suggesting alternative trains and dates...");
        String alternativeTrainsQuery = "SELECT train_id, date FROM TrainSchedule WHERE train_id != ? AND date > CURRENT_DATE ORDER BY date LIMIT 3";
        try (PreparedStatement stmt = connection.prepareStatement(alternativeTrainsQuery)) {
            stmt.setInt(1, trainId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int alternativeTrainId = rs.getInt("train_id");
                Date date = rs.getDate("date");
                System.out.println("Train ID: " + alternativeTrainId + ", Date: " + date);
            }
        }
    }

    private static String generatePNR() {
        // Simple PNR generation logic
        return "PNR" + System.currentTimeMillis(); }// Example PNR based 
    private static void cancelTicket(Connection connection, Scanner scanner) {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); 

        try {
            
            connection.setAutoCommit(false);

            
            deleteFromTable(connection, "payment", bookingId);
            deleteFromTable(connection, "foodbooking", bookingId);
            deleteFromTable(connection, "bookinghistory", bookingId);
            deleteFromTable(connection, "passengerseat", bookingId);
            deleteFromTable(connection, "refund", bookingId);
           
            String deleteBookingQuery = "DELETE FROM booking WHERE booking_id = ?";
            try (PreparedStatement deleteBookingStmt = connection.prepareStatement(deleteBookingQuery)) {
                deleteBookingStmt.setInt(1, bookingId);
                deleteBookingStmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Ticket cancelled successfully.");

        } catch (SQLException e) {
            try {
                // Rollback transaction in case of error
                connection.rollback();
                System.out.println("Error occurred while cancelling the ticket. Rolling back changes.");
                e.printStackTrace();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteFromTable(Connection connection, String tableName, int bookingId) throws SQLException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        }
    }


    private static int getSeatIdByBooking(Connection connection, int bookingId) throws SQLException {
        String query = "SELECT seat_id FROM Booking WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("seat_id");
                }
            }
        }
        return -1; // Return -1 if no seat is found, though this case should be handled before calling this function
    }

    private static void updateSeatAvailability(Connection connection, int seatId) throws SQLException {
        String updateSeatQuery = "UPDATE Seat SET availability_status = 'Available' WHERE seat_id = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateSeatQuery)) {
            updateStmt.setInt(1, seatId);
            updateStmt.executeUpdate();
        }
    }


    }