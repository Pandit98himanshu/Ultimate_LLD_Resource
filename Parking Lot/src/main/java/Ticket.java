import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalTime arrivalTime;
}
