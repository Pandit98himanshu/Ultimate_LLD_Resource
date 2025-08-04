public class ParkingFullException extends RuntimeException {
    public ParkingFullException() {
        super("Parking is full!!!");
    }
}
