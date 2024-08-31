package RailwayReservation;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
    private int scheduleId;
    private int trainId;
    public Time getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDaysOfOperation() {
		return daysOfOperation;
	}

	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	private Time departureTime;
    private Time arrivalTime;
    private String daysOfOperation;
    private Date travelDate;

    // Constructors, Getters, and Setters
    public Schedule() {}

    public Schedule(int scheduleId, int trainId, Time departureTime, Time arrivalTime, String daysOfOperation, Date travelDate) {
        this.scheduleId = scheduleId;
        this.setTrainId(trainId);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.daysOfOperation = daysOfOperation;
        this.travelDate = travelDate;
    }

    // Getters and Setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

    // (Add other getters and setters similarly)
}

