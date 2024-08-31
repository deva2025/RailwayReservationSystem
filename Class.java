package RailwayReservation;

import java.math.BigDecimal;

public class Class {
    private int classId;
    private int trainId;
    public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}

	private String className; // 'Sleeper', '1st Class', '2nd Class', '3rd Class', 'Economy', 'Seating'
    private BigDecimal fare;


    public Class(int classId, int trainId, String className, BigDecimal fare) {
        this.classId = classId;
        this.trainId = trainId;
        this.className = className;
        this.fare = fare;
    }

    // Getters and Setters
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    // (Add other getters and setters similarly)
}
