import exceptions.ParkingFullException;
import parkingspot.ParkingSpot;
import parkingspot.ParkingSpotStatus;
import vehicles.Vehicle;
import vehicles.VehicleSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingManager {
    private final Map<VehicleSize, List<ParkingSpot>> availableParkingSpots;
    private final Map<Vehicle, ParkingSpot> vehicleSpotAllocation;

    public ParkingManager(Map<VehicleSize, List<ParkingSpot>> availableParkingSpots) {
        this.availableParkingSpots = availableParkingSpots;
        vehicleSpotAllocation = new HashMap<>();
    }

    private ParkingSpot findSpotForVehicle(Vehicle vehicle) {
        // start searching from the smallest parking spot that can fit the vehicle
        for (VehicleSize size : VehicleSize.values()) {
            if (size.ordinal() >= vehicle.getSize().ordinal()) {
                for (ParkingSpot spot : availableParkingSpots.get(size)) {
                    if (spot.getStatus() == ParkingSpotStatus.EMPTY)
                        return spot;
                }
            }
        }
        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findSpotForVehicle(vehicle);
        if (spot == null) {
            throw new ParkingFullException("Parking is full !!!");
        }
        try {
            spot.occupy(vehicle);   // set the spot status as occupied
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
        vehicleSpotAllocation.put(vehicle, spot);   // map the spot with vehicle
        availableParkingSpots.get(spot.getSize()).remove(spot); // remove from available parking map
        return spot;
    }

    public void unparkVehicle(Vehicle vehicle) {
        ParkingSpot spot = vehicleSpotAllocation.remove(vehicle);
        if (spot != null) {
            spot.vacate();
            availableParkingSpots.get(spot.getSize()).add(spot);
        }
    }
}
