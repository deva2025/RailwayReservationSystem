package RailwayReservation;

public class Seat {
    private int seatId;
    private String seatNumber;
    private String classType; // 'Sleeper', '1st Class', '2nd Class', '3rd Class', 'Economy', 'Seating'
    private String availabilityStatus; // 'Available', 'Booked'
    private int coachId;


    public Seat(int seatId, String seatNumber, String classType, String availabilityStatus, int coachId) {
        this.seatId = seatId;
        this.setSeatNumber(seatNumber);
        this.setClassType(classType);
        this.setAvailabilityStatus(availabilityStatus);
        this.setCoachId(coachId);
    }

    // Getters and Setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getCoachId() {
		return coachId;
	}

	public void setCoachId(int coachId) {
		this.coachId = coachId;
	}

    // (Add other getters and setters similarly)
}

