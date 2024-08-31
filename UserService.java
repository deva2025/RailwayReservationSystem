package RailwayReservation;

import java.sql.*;
import java.util.Scanner;

public class UserService {
    public static void register() {
        try (Scanner scanner = new Scanner(System.in)) {
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

			try (Connection conn = DatabaseConnection.getConnection()) {
			    String query = "INSERT INTO Users (username, email, password_hash, phone_number, user_type) VALUES (?, ?, ?, ?, ?)";
			    PreparedStatement stmt = conn.prepareStatement(query);
			    stmt.setString(1, username);
			    stmt.setString(2, email);
			    stmt.setString(3, password); // Note: Use hashed passwords in real applications
			    stmt.setString(4, phoneNumber);
			    stmt.setString(5, userType);
			    stmt.executeUpdate();
			    System.out.println("Registration successful. You can now login.");
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
    }

    public static User login() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter Username: ");
			String username = scanner.nextLine();
			System.out.print("Enter Password: ");
			String password = scanner.nextLine();

			try (Connection conn =DatabaseConnection.getConnection()) {
			    String query = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
			    PreparedStatement stmt = conn.prepareStatement(query);
			    stmt.setString(1, username);
			    stmt.setString(2, password);
			    ResultSet rs = stmt.executeQuery();

			    if (rs.next()) {
			        return new User(
			            rs.getInt("user_id"),
			            rs.getString("username"),
			            rs.getString("email"),
			            rs.getString("password_hash"),
			            rs.getString("phone_number"),
			            rs.getString("user_type")
			        );
			    } else {
			        System.out.println("Invalid username or password.");
			        return null;
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			    return null;
			}
		}
    }
}

