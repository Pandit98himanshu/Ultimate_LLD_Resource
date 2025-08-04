import lombok.Getter;

@Getter
public enum VehicleType {
    BIKE(50),
    CAR(100),
    TRUCK(200);

    private final int hourlyCharges;
    private VehicleType(int hourlyCharges) {
        this.hourlyCharges = hourlyCharges;
    }

}