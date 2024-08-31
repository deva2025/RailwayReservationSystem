package RailwayReservation;

public class Station {
    private int stationId;
    private String stationName;
    private String location;

    // Constructors, Getters, and Setters
    public Station() {}

    public Station(int stationId, String stationName, String location) {
        this.stationId = stationId;
        this.setStationName(stationName);
        this.setLocation(location);
    }

    // Getters and Setters
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    // (Add other getters and setters similarly)
}
