public class Car implements Vehicle {
    public Car(int newCarId, String newBrand, String newModelName, int newMaxVelocity, int newPower, int newOwnerId) {
        carId = newCarId;
        brand = newBrand;
        modelName = newModelName;
        maxVelocity = newMaxVelocity;
        power = newPower;
        ownerId = newOwnerId;
    }
    @Override
    public int getOwnerId() {
        return ownerId;
    }
    @Override
    public String getBrand() {
        return brand;
    }
    @Override
    public int getCarId() {
        return carId;
    }
    @Override
    public final int getMaxVelocity() {
        return maxVelocity;
    }
    @Override
    public final int getPower() {
        return power;
    }
    public final String getModelName() {
        return modelName;
    }
    private final int carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final int ownerId;
}

