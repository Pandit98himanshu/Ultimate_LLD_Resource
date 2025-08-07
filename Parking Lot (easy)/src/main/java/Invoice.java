public class Invoice {
    private Ticket ticket;
    private int amount;

    Invoice(Ticket ticket, int amount) {
        this.ticket = ticket;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "ticket=" + ticket +
                ", amount=" + amount +
                '}';
    }
}
