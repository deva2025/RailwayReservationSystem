package RailwayReservation;

import java.math.BigDecimal;

public class Route {
    private int routeId;
    private int sourceStationId;
    private int destinationStationId;
    private BigDecimal distance;


    public int getSourceStationId() {
		return sourceStationId;
	}

	public void setSourceStationId(int sourceStationId) {
		this.sourceStationId = sourceStationId;
	}

	public int getDestinationStationId() {
		return destinationStationId;
	}

	public void setDestinationStationId(int destinationStationId) {
		this.destinationStationId = destinationStationId;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public Route(int routeId, int sourceStationId, int destinationStationId, BigDecimal distance) {
        this.routeId = routeId;
        this.sourceStationId = sourceStationId;
        this.destinationStationId = destinationStationId;
        this.distance = distance;
    }

    // Getters and Setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    // (Add other getters and setters similarly)
}

