import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Invoice {
    private Ticket ticket;
    private double amount;
}
