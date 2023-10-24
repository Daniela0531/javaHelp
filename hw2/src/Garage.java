import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;


public interface Garage<T extends Vehicle> {
    Collection<Owner> allCarsUniqueOwners();

    /**
     * Complexity should be less than O(n)
     */
    Collection<T> topThreeCarsByMaxVelocity();

    /**
     * Complexity should be O(1)
     */
    Collection<T> allCarsOfBrand(String brand);

    /**
     * Complexity should be less than O(n)
     */
    Collection<T> carsWithPowerMoreThan(int power);

    /**
     * Complexity should be O(1)
     */
    Collection<T> allCarsOfOwner(Owner owner);

    /**
     * @return mean value of owner age that has cars with given brand
     */
    int meanOwnersAgeOfCarBrand(String brand);

    /**
     * @return mean value of cars for all owners
     */
    int meanCarNumberForEachOwner();

    /**
     * Complexity should be less than O(n)
     * @return removed car
     */
    T removeCar(int carId);

    /**
     * Complexity should be less than O(n)
     */
    void addNewCar(T car, Owner owner);
    void addCars(List<T> cars, Owner owner);
    Object removeCar(Object car);
    void removeAll(List<T> cars);
    <V extends Vehicle>List<V> upgradeAllVehiclesWith(VehicleUpgrader<V, T> upgrader);
    List<T> filterCars(Predicate<T> predicate);
}