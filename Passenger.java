package RailwayReservation;

import java.sql.Date;

public class Passenger extends User {
    private String firstName;
    private String lastName;
    private int age;
    private String nationality;

    public Passenger(int userId, String username, String email, String passwordHash, String phoneNumber, String firstName, String lastName, int age, String nationality) {
        super(userId, username, email, passwordHash, phoneNumber, "Passenger");
        this.firstName = firstName;
        this.lastName = lastName;
        this.age=age;
        this.nationality = nationality;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(Date dateOfBirth) { this.age=age; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

   
}


