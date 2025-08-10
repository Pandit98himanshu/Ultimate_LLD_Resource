package entity;

import java.util.List;

public class City {
    String name;
    List<CinemaHall> cinemaHalls;

    public City(String name, List<CinemaHall> cinemaHalls) {
        this.name = name;
        this.cinemaHalls = cinemaHalls;
    }
}
