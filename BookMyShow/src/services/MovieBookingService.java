package services;

import entity.CinemaHall;
import entity.City;
import entity.Movie;
import entity.User;

public class MovieBookingService {
    public void bookMovie(Movie movie, User user, City city, CinemaHall hall, String[] seats) {
        // block seats for 5 minutes
        // if payment done, book the seats
        // else release blocked seats
    }
}
