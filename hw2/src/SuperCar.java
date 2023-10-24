public class SuperCar implements Vehicle {
    private final int carId;
    private final String brand;
    private final int maxVelocity;
    private final int power;
    private final int ownerId;
    public SuperCar(int newCarId, String newBrand, int newMaxVelocity, int newPower, int newOwnerId) {
        carId = newCarId;
        brand = newBrand;
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
}
