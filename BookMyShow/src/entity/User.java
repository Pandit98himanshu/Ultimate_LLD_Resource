package entity;

public class User {
    String name;
    Ticket ticket;

    public User(String name) {
        this.name = name;
        this.ticket = null;
    }

    public void assignTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
