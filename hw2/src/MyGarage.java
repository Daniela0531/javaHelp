import java.util.*;
import java.util.function.Predicate;

public class MyGarage<T extends Vehicle> implements Garage<T> {
    HashMap<String, HashMap<Integer, T>> brands;
    HashMap<Integer, T> cars;
    HashMap<Integer, Owner> ownerIdForOwner;
    HashMap<Owner, HashMap<Integer, T>> carsForEachOwner;
    TreeSet<T> velocityCars;
    TreeMap<Integer, T> powerCars;

    public MyGarage(){
        brands = new HashMap<>();
        cars = new HashMap<>();
        ownerIdForOwner = new HashMap<>();
        carsForEachOwner = new HashMap<>();
        velocityCars = new TreeSet<>(Comparator.comparing(T::getMaxVelocity));
        powerCars = new TreeMap<Integer, T>();
    }
    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return carsForEachOwner.keySet();
    }
    /**
     * Complexity should be less than O(n)
     */
    @Override
    public Collection<T> topThreeCarsByMaxVelocity() {
        int numberOfCarsToReturn = Math.min(3, velocityCars.size());
        if (numberOfCarsToReturn > 0) {
            return topCarsByMaxVelocity(numberOfCarsToReturn);
        }
        return null;
    }
    private Collection<T> topCarsByMaxVelocity(int numberOfCars) {
        ArrayList<T> topCarsByMaxVelocity = new ArrayList<>();
        topCarsByMaxVelocity.add(velocityCars.last());
        --numberOfCars;
        for (int i = 0; i < numberOfCars; ++i) {
            topCarsByMaxVelocity.add(velocityCars.lower(topCarsByMaxVelocity.get(i)));
        }
        return topCarsByMaxVelocity;
    }
    /**
     * Complexity should be O(1)
     */
    @Override
    public Collection<T> allCarsOfBrand(String brand) {
        if (brands.containsKey(brand)) {
            return brands.get(brand).values();
        }
        return null;
    }
    /**
     * Complexity should be less than O(n)
     */
    @Override
    public Collection<T> carsWithPowerMoreThan(int power) {
        return powerCars.tailMap(power).values();
    }
    /**
     * Complexity should be O(1)
     */
    @Override
    public Collection<T> allCarsOfOwner(Owner owner) {
        if (carsForEachOwner.containsKey(owner)) {
            return carsForEachOwner.get(owner).values();
        }
        return null;
    }
    /**
     * @return mean value of owner age that has cars with given brand
     */
    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        if (brands.containsKey(brand)) {
            return meanOwnersAgeOfMyCarBrand(brand);
        }
        return 0;
    }
    private int meanOwnersAgeOfMyCarBrand(String brand) {
        int sum = 0;
        int count = 0;
        HashMap<Integer, Owner> ownersOfBrand = new HashMap<Integer, Owner>(ownerIdForOwner);
        for (T car : brands.get(brand).values()) {
            if (ownersOfBrand.containsKey(car.getOwnerId())) {
                sum += ownersOfBrand.get(car.getOwnerId()).getAge();
                ++count;
                ownersOfBrand.remove(car.getOwnerId());
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
    /**
     * @return mean value of cars for all owners
     */
    @Override
    public int meanCarNumberForEachOwner() {
        if (!carsForEachOwner.isEmpty()) {
            return cars.size()/carsForEachOwner.size();
        }
        return 0;
    }
    /**
     * Complexity should be less than O(n)
     * @return removed car
     * если car есть в cars, значит она, её бренд и так далее
     * точно присутствует в других структурах
     * так как предположительно addCar работает корректно
     */
    @Override
    public T removeCar(int carId) {
        if (cars.containsKey(carId)) {
            T car = cars.remove(carId);
            removeCarFromBrands(car);
            velocityCars.remove(car);
            powerCars.remove(car.getPower(), car);
            removeCarFromCarsForEachOwner(car);
            removeCarFromOwnerIdForOwner(car);
            return car;
        }
        return null;
    }
    private void removeCarFromBrands(T car) {
        brands.get(car.getBrand()).remove(car.getCarId());
        if (brands.get(car.getBrand()).isEmpty()) {
            brands.remove(car.getBrand());
        }
    }
    private void removeCarFromCarsForEachOwner(T car) {
        int ownerId = car.getOwnerId();
        Owner owner = ownerIdForOwner.get(ownerId);
        carsForEachOwner.get(owner).remove(car.getCarId());
        if (carsForEachOwner.get(owner).isEmpty()) {
            carsForEachOwner.remove(owner);
        }
    }
    private void removeCarFromOwnerIdForOwner(T car) {
        int ownerId = car.getOwnerId();
        Owner owner = ownerIdForOwner.get(ownerId);
        if (carsForEachOwner.get(owner).isEmpty()) {
            ownerIdForOwner.remove(ownerId);
        }
    }
    /**
     * Complexity should be less than O(n)
     */
    @Override
    public void addNewCar(T car, Owner owner) {
        if (!cars.containsKey(car.getCarId())) {
            cars.put(car.getCarId(), car);
            addNewCarToBrands(car);
            velocityCars.add(car);
            powerCars.put(car.getPower(), car);
            addNewCarToOwnerIdForOwner(car, owner);
            addNewCarToCarsForEachOwner(car, owner);
        }
    }
    private void addNewCarToBrands(T car) {
        if (brands.containsKey(car.getBrand())) {
            brands.get(car.getBrand()).put(car.getCarId(), car);
        } else {
            brands.put(car.getBrand(), new HashMap<Integer, T>());
            brands.get(car.getBrand()).put(car.getCarId(), car);
        }
    }
    private void addNewCarToOwnerIdForOwner(T car, Owner owner) {
        if (!ownerIdForOwner.containsKey(car.getOwnerId())) {
            ownerIdForOwner.put(car.getOwnerId(), owner);
        }
    }
    private void addNewCarToCarsForEachOwner(T car, Owner owner) {
        if (carsForEachOwner.containsKey(owner)) {
            carsForEachOwner.get(owner).put(car.getCarId(), car);
        } else {
            carsForEachOwner.put(owner, new HashMap<Integer, T>());
            carsForEachOwner.get(owner).put(car.getCarId(), car);
        }
    }
    @Override
    public void addCars(List<T> cars, Owner owner) {
        for (T car : cars) {
            addNewCar(car, owner);
        }
    }
    @Override
    public Object removeCar(Object car) {
        return null;
    }
    @Override
    public void removeAll(List<T> cars) {
        for (T car : cars) {
            removeCar(car.getCarId());
        }
    }
    @Override
    public List<T> filterCars(Predicate<T> predicate) {
        return cars.values().stream().filter(predicate).toList();
    }
    @Override
    public <V extends Vehicle>List<V> upgradeAllVehiclesWith(VehicleUpgrader<V, T> upgrader) {
        ArrayList<V> upgradedVehicles = new ArrayList<>();
        for (T car : cars.values()) {
            upgradedVehicles.add(upgrader.upgrade(car));
        }
        return upgradedVehicles;
    }
}
