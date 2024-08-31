package RailwayReservation;


public class Train {
    private String trainNumber;
    private String trainName;

 

    public Train(String trainNumber, String trainName) {
      
        this.trainNumber = trainNumber;
        this.trainName = trainName;

    }
   

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    
   

    // Additional methods can be added as needed

    @Override
    public String toString() {
        return "Train{" +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
             
                '}';
    }

	
}

