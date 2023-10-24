public class Car {
    public Car(int newCarId, String newBrand, String newModelName, int newMaxVelocity, int newPower, int newOwnerId) {
        carId = newCarId;
        brand = newBrand;
        modelName = newModelName;
        maxVelocity = newMaxVelocity;
        power = newPower;
        ownerId = newOwnerId;
    }
    public int getOwnerId() {
        return ownerId;
    }

    public String getBrand() {
        return brand;
    }
    public int getCarId() {
        return carId;
    }
    public final int getVelocity() {
        return maxVelocity;
    }
    public final int getPower() {
        return power;
    }
    private final int carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final int ownerId;
}

public interface Vehicle {
    int getCarId();
    String getBrand();
    int getMaxVelocity();
    int getPower();
    int getOwnerId();
}
public class SuperCar implements Vehicle {
}
/**
 * Upgrades any type Vehicle to another type of Vehicle
 * E.g. Car -&gt; SuperCar
 */
public interface VehicleUpgrader {
    Object upgrade(Object vehicle);
}