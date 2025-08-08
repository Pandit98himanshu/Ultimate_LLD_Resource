package ticket;

import parkingspot.ParkingSpot;
import vehicles.Vehicle;

import java.time.Duration;
import java.time.LocalTime;

public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private LocalTime entryTime;
    private LocalTime exitTime;

    public Ticket(int ticketId, Vehicle vehicle, ParkingSpot spot, LocalTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
        this.exitTime = null;
    }

    public long calculateParkingDuration() {
        return Duration.between(this.entryTime, this.exitTime).toHours();
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public LocalTime getExitTime() {
        return this.exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }
}
