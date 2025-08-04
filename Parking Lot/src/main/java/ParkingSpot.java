import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ParkingSpot {
    private int spotId;
    private VehicleType type;
    private boolean isOccupied;

    @Override
    public int hashCode() {
        return getSpotId();
    }
}
