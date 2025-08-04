import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalTime arrivalTime;
}
