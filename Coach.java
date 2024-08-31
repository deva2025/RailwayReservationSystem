package RailwayReservation;

public class Coach {
    private int coachId;
    private int trainId;
    private String coachNumber;
    private String classType; // 'Sleeper', '1st Class', '2nd Class', '3rd Class', 'Economy', 'Seating'
    private int seatCapacity;

    // Constructors, Getters, and Setters
    public Coach() {}

    public Coach(int coachId, int trainId, String coachNumber, String classType, int seatCapacity) {
        this.coachId = coachId;
        this.setTrainId(trainId);
        this.setCoachNumber(coachNumber);
        this.setClassType(classType);
        this.setSeatCapacity(seatCapacity);
    }

    // Getters and Setters
    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public String getCoachNumber() {
		return coachNumber;
	}

	public void setCoachNumber(String coachNumber) {
		this.coachNumber = coachNumber;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public int getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}

    // (Add other getters and setters similarly)
}

