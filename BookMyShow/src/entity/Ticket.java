package entity;

import java.time.LocalTime;
import java.util.List;

public class Ticket {
    User user;
    City city;
    Show show;
    LocalTime bookingTime;
    List<String> seats;

    public Ticket(User user, City city, Show show, List<String> seats) {
        this.user = user;
        this.city = city;
        this.show = show;
        bookingTime = null;
        this.seats = seats;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void cancel() {
        this.show.getHall().cancelBookedSeats(this.show, this.seats);
    }
}
