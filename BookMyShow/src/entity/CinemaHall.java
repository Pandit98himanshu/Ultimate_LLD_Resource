package entity;

import java.time.LocalTime;
import java.util.*;

public class CinemaHall {
    String name;
    int rows, columns;
    Map<Movie, List<LocalTime>> movieSchedule;
    Map<Movie, List<List<String>>> movieBookedSeats;

    public CinemaHall(String name, int rows, int columns, Map<Movie, List<LocalTime>> movieSchedule) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.movieSchedule = movieSchedule;
    }

    public Set<Movie> getAllMovies() {
        return movieSchedule.keySet();
    }

    public Map<Movie, List<LocalTime>> getSchedule(Movie movie) {
        return movieSchedule;
    }

    public char[][] getSeatingStatus(Movie movie, LocalTime startTime) {
        List<LocalTime> schedule = movieSchedule.get(movie);
        List<List<String>> bookedSeats = movieBookedSeats.get(movie);
        if (bookedSeats == null)
            bookedSeats = new ArrayList<>();
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals(startTime)) {
                return processAvailableSeats(bookedSeats.get(i));
            }
        }
        return null;
    }

    public void addBookedSeats(Movie movie, LocalTime startTime, List<String> bookedSeat) {
        List<LocalTime> schedule = movieSchedule.get(movie);
        List<List<String>> bookedSeats = movieBookedSeats.get(movie);
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals(startTime)) {
                bookedSeats.get(i).addAll(bookedSeat);
                break;
            }
        }
        movieBookedSeats.put(movie, bookedSeats);
    }

    public void releaseBlockedSeats(Movie movie, LocalTime startTime, List<String> bookedSeat) {
        List<LocalTime> schedule = movieSchedule.get(movie);
        List<List<String>> bookedSeats = movieBookedSeats.get(movie);
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals(startTime)) {
                bookedSeats.get(i).removeAll(bookedSeats);
                break;
            }
        }
        movieBookedSeats.put(movie, bookedSeats);
    }

    private char[][] processAvailableSeats(List<String> bookedSeats) {
        char[][] seatStatus = new char[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                String currentSeat = (char)(i + 'A') + "" + j;
                if (bookedSeats.contains(currentSeat)) {
                    seatStatus[i][j] = 'X';
                } else {
                    seatStatus[i][j] = 'O';
                }
            }
        }
        return seatStatus;
    }
}
