package RailwayReservation;

public class Manager {
    private int managerId;
    private int userId;
    private String department;

 
    public Manager(int managerId, int userId, String department) {
        this.managerId = managerId;
        this.userId = userId;
        this.department = department;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

  
    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", userId=" + userId +
                ", department='" + department + '\'' +
                '}';
    }
}

