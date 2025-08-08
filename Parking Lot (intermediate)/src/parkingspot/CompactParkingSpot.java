package parkingspot;

import exceptions.SpotOccupiedException;
import vehicles.Vehicle;
import vehicles.VehicleSize;

public class CompactParkingSpot implements ParkingSpot {
    private ParkingSpotStatus status;
    private int spotNumber;
    private Vehicle vehicle;

    CompactParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
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
        return VehicleSize.SMALL;
    }
    /*
        Motorbikes can park in angled way for ease
     */
    @Override
    public ParkingWay getParkingWay() {
        return ParkingWay.ANGLED;
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
