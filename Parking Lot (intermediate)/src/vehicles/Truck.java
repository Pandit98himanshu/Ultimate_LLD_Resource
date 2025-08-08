package vehicles;

public class Truck implements Vehicle {
    private String licensePlate;

    Truck(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    @Override
    public String getLicensePlate() {
        return this.licensePlate;
    }
    @Override
    public VehicleSize getSize() {
        return VehicleSize.LARGE;
    }
}
