public class ParkingFullException extends RuntimeException {
    public ParkingFullException(String vehicleType) {
        super("Parking is full for " + vehicleType + "!!!");
    }
}
