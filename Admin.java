package RailwayReservation;

import java.time.LocalDate;

public class Admin {
    private int adminId;
    private int userId;
    private LocalDate dateOfJoining;

    public Admin(int adminId, int userId, LocalDate dateOfJoining) {
        this.adminId = adminId;
        this.userId = userId;
        this.dateOfJoining = dateOfJoining;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userId=" + userId +
                ", dateOfJoining=" + dateOfJoining +
                '}';
    }
}
