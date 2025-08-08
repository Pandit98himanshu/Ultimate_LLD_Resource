package fares;

public enum Rates {
    SMALL_VEHICLE_HOURLY_RATE(1.0),
    SMALL_VEHICLE_DAILY_RATE(30.0),
    MEDIUM_VEHICLE_HOURLY_RATE(3.0),
    MEDIUM_VEHICLE_DAILY_RATE(100.0),
    LARGE_VEHICLE_HOURLY_RATE(5.0),
    LARGE_VEHICLE_DAILY_RATE(150.0),
    PEAK_HOURS_MULTIPLIER(1.2); // 20% higher rates at peak hours

    private final double fare;
    Rates(double fare) {
        this.fare = fare;
    }

    public double getFare() {
        return this.fare;
    }
}
