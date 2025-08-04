import java.time.LocalTime;

public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalTime arrivalTime;

    Ticket(int ticketId, Vehicle vehicle, ParkingSpot parkingSpot, LocalTime arrivalTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.arrivalTime = arrivalTime; // Set the arrival time to the current time
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", vehicle=" + vehicle +
                ", parkingSpot=" + parkingSpot +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
