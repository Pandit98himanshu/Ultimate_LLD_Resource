public class ParkingSpot {
    private int spotId;
    private VehicleType type;
    private boolean isOccupied;

    ParkingSpot(int spotId, VehicleType type) {
        this.spotId = spotId;
        this.type = type;
        this.isOccupied = false; // Default to not occupied
    }

    public int getSpotId() {
        return spotId;
    }

    public VehicleType getType() {
        return type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public int hashCode() {
        return getSpotId();
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotId=" + spotId +
                ", type=" + type +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
