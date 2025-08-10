import exceptions.InvalidTicketException;
import fares.FareCalculator;
import fares.FareStrategy;
import parkingspot.ParkingSpot;
import ticket.Ticket;
import vehicles.Vehicle;
import vehicles.VehicleSize;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ParkingLotService {
    private int ticketIdCounter;
    private final ParkingManager manager;
    private final FareCalculator fareCalculator;

    public ParkingLotService(Map<VehicleSize, List<ParkingSpot>> availableParkingSpots, List<FareStrategy> fareStrategies) {
        this.ticketIdCounter = 100;
        this.manager = new ParkingManager(availableParkingSpots);
        this.fareCalculator = new FareCalculator(fareStrategies);
    }

    private int generateTicketId() {
        return ticketIdCounter++;
    }

    public Ticket enterVehicle(Vehicle vehicle) {
        // assign a parking spot for the vehicle
        // and create a ticket when it enters
        ParkingSpot spot = manager.parkVehicle(vehicle);
        LocalTime entryTime = LocalTime.now();
        return new Ticket(generateTicketId(), vehicle, spot, entryTime);
    }

    public double leaveVehicle(Ticket ticket) {
        // unpark the vehicle from the parking spot
        // and calculate fare for the stay period
        if (ticket == null || ticket.getExitTime() != null) {
            throw new InvalidTicketException("The ticket is invalid.");
        }
        ticket.setExitTime(LocalTime.now());
        manager.unparkVehicle(ticket.getVehicle());
        return fareCalculator.calculateFare(ticket);
    }
}
