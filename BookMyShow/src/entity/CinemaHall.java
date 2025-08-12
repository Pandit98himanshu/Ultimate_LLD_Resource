package entity;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CinemaHall {
    private String name;
    private int rows, columns;
    private PriorityQueue<Show> movieSchedule;
    private Map<Show, Set<String>> movieBookedSeats;
    private Map<Show, Set<String>> movieHeldSeats; // For 5-minute holds
    private final ReentrantLock seatLock = new ReentrantLock();

    public CinemaHall(String name, int rows, int columns, List<Show> movieSchedule, Map<Show, Set<String>> movieBookedSeats) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.movieSchedule = new PriorityQueue<>((a, b) -> a.getStartTime().compareTo(b.getStartTime()));
        for (Show show : movieSchedule) {
            this.movieSchedule.add(show);
        }
        this.movieBookedSeats = new ConcurrentHashMap<>(movieBookedSeats);
        this.movieHeldSeats = new ConcurrentHashMap<>();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new LinkedList<>();
        movieSchedule.forEach(show -> movies.add(show.getMovie()));
        return movies;
    }

    public List<LocalTime> getSchedule(Movie movie) {
        List<LocalTime> scheduleList = new LinkedList<>();
        for (Show show : movieSchedule) {
            if (show.getMovie().equals(movie)) {
                scheduleList.add(show.getStartTime());
            }
        }
        return scheduleList;
    }

    public String getSeatingStatus(Show show) {
        Set<String> bookedSeats = movieBookedSeats.get(show);
        if (bookedSeats == null)
            return null;
        char[][] seatingStatus = new char[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                String currentSeat = (char)(i + 'A') + "" + j;
                if (bookedSeats.contains(currentSeat)) {
                    seatingStatus[i][j] = 'X';
                } else {
                    seatingStatus[i][j] = 'O';
                }
            }
        }
        return Arrays.deepToString(seatingStatus);
    }

    public boolean holdSeats(Show show, List<String> seats) {
        seatLock.lock();
        try {
            Set<String> bookedSeats = movieBookedSeats.getOrDefault(show, new HashSet<>());
            Set<String> heldSeats = movieHeldSeats.getOrDefault(show, new HashSet<>());
            
            // Check if any seats are already booked or held
            for (String seat : seats) {
                if (bookedSeats.contains(seat) || heldSeats.contains(seat)) {
                    return false; // Seat not available
                }
            }
            
            // Hold the seats
            heldSeats.addAll(seats);
            movieHeldSeats.put(show, heldSeats);
            return true;
        } finally {
            seatLock.unlock();
        }
    }

    public boolean confirmBooking(Show show, List<String> seats) {
        seatLock.lock();
        try {
            Set<String> heldSeats = movieHeldSeats.getOrDefault(show, new HashSet<>());
            Set<String> bookedSeats = movieBookedSeats.getOrDefault(show, new HashSet<>());
            
            // Check if all seats are still held
            for (String seat : seats) {
                if (!heldSeats.contains(seat)) {
                    return false; // Seat no longer held
                }
            }
            
            // Move from held to booked
            heldSeats.removeAll(seats);
            bookedSeats.addAll(seats);
            movieHeldSeats.put(show, heldSeats);
            movieBookedSeats.put(show, bookedSeats);
            return true;
        } finally {
            seatLock.unlock();
        }
    }

    public void releaseHeldSeats(Show show, List<String> seats) {
        seatLock.lock();
        try {
            Set<String> heldSeats = movieHeldSeats.getOrDefault(show, new HashSet<>());
            heldSeats.removeAll(seats);
            movieHeldSeats.put(show, heldSeats);
        } finally {
            seatLock.unlock();
        }
    }

    public void cancelBookedSeats(Show show, List<String> seats) {
        seatLock.lock();
        try {
            Set<String> bookedSeats = movieBookedSeats.getOrDefault(show, new HashSet<>());
            bookedSeats.removeAll(seats);
            movieBookedSeats.put(show, bookedSeats);
        } finally {
            seatLock.unlock();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CinemaHall cinemaHall = (CinemaHall) obj;
        return this.name.equals(cinemaHall.name) && this.rows == cinemaHall.rows && this.columns == cinemaHall.columns;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + rows + columns;
    }
}
