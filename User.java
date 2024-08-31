package RailwayReservation;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private String userType;

    public User(int userId, String username, String email, String passwordHash, String phoneNumber, String userType) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.setPasswordHash(passwordHash);
        this.setPhoneNumber(phoneNumber);
        this.userType = userType;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
