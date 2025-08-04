import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Invoice {
    private Ticket ticket;
    private int amount;
}
