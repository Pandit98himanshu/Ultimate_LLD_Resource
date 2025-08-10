package parkingspot;

import exceptions.SpotOccupiedException;
import vehicles.Vehicle;
import vehicles.VehicleSize;

public class RegularParkingSpot implements ParkingSpot {
    private ParkingSpotStatus status;
    private int spotNumber;
    private Vehicle vehicle;

    public RegularParkingSpot(int spotNumber) {
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
        return VehicleSize.MEDIUM;
    }
    /*
        Medium-sized vehicles like Car can park in reversed way,
        because they can make reverse in tight spot and
        less probability of accidents while unparking
     */
    @Override
    public ParkingWay getParkingWay() {
        return ParkingWay.REVERSED;
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
