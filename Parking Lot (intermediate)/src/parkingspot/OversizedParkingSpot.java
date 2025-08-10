package parkingspot;

import exceptions.SpotOccupiedException;
import vehicles.Vehicle;
import vehicles.VehicleSize;

public class OversizedParkingSpot implements ParkingSpot {
    private ParkingSpotStatus status;
    private int spotNumber;
    private Vehicle vehicle;

    public OversizedParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        status = ParkingSpotStatus.EMPTY;
    }
    @Override
    public int getSpotNumber() {
        return this.spotNumber;
    }
    @Override
    public ParkingSpotStatus getStatus() {
        return this.status;
    }
    @Override
    public VehicleSize getSize() {
        return VehicleSize.LARGE;
    }
    /*
        Oversized vehicles like trucks can park in parallel way to save space
     */
    @Override
    public ParkingWay getParkingWay() {
        return ParkingWay.PARALLEL;
    }
    @Override
    public void occupy(Vehicle vehicle) {
        if (this.vehicle != null) {
            this.vehicle = vehicle;
            this.status = ParkingSpotStatus.OCCUPIED;
        } else
            throw new SpotOccupiedException("Spot " + getSpotNumber() + " is occupied by vehicle " + this.vehicle.getLicensePlate());
    }
    @Override
    public void vacate() {
        this.vehicle = null;
        this.status = ParkingSpotStatus.EMPTY;
    }
    @Override
    public void setToMaintenance() {
        if (this.status == ParkingSpotStatus.OCCUPIED) {
            throw new SpotOccupiedException("Spot " + getSpotNumber() + " is occupied by vehicle " + this.vehicle.getLicensePlate());
        }
        this.status = ParkingSpotStatus.MAINTENANCE;
    }
}
