package parkingspot;

import vehicles.Vehicle;
import vehicles.VehicleSize;

public interface ParkingSpot {
    int getSpotNumber();
    ParkingSpotStatus getStatus();
    VehicleSize getSize();
    ParkingWay getParkingWay();
    void occupy(Vehicle vehicle);
    void vacate();
    void setToMaintenance();
}
