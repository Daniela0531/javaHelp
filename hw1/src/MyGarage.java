import java.util.*;

public class MyGarage implements Garage {
    HashMap<String, HashMap<Integer, Car>> brands;
    HashMap<Integer, Car> cars;
    HashMap<Integer, Owner> ownerIdForOwner;
    HashMap<Owner, HashMap<Integer, Car>> carsForEachOwner;
    TreeSet<Car> velocityCars;
    TreeSet<Car> powerCars;

    public MyGarage(){
        brands = new HashMap<>();
        cars = new HashMap<>();
        ownerIdForOwner = new HashMap<>();
        carsForEachOwner = new HashMap<>();
        velocityCars = new TreeSet<>(Comparator.comparing(Car::getVelocity));
        powerCars = new TreeSet<>(Comparator.comparing(Car::getPower));
    }
    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return carsForEachOwner.keySet();
    }

    /**
     * Complexity should be less than O(n)
     */
    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        int numberOfCarsToReturn = Math.min(3, velocityCars.size());
        if (numberOfCarsToReturn > 0) {
            return topCarsByMaxVelocity(numberOfCarsToReturn);
        }
        return null;
    }

    private Collection<Car> topCarsByMaxVelocity(int numberOfCars) {
        ArrayList<Car> topCarsByMaxVelocity = new ArrayList<>();
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
    public Collection<Car> allCarsOfBrand(String brand) {
        if (brands.containsKey(brand)) {
            return brands.get(brand).values();
        }
        return null;
    }
    /**
     * Complexity should be less than O(n)
     */
    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        Car myCar = new Car(0, "", "", 0, power, 0);
        return powerCars.tailSet(myCar, false);
    }

    /**
     * Complexity should be O(1)
     */
    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
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
        for (Car car : brands.get(brand).values()) {
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
     */
    @Override
//    если car есть в cars, значит она, её бренд и так далее
//    точно присутствует в других структурах
//    так как предположительно addCar работает корректно
    public Car removeCar(int carId) {
        if (cars.containsKey(carId)) {
            Car car = cars.remove(carId);
            removeCarFromBrands(car);
            velocityCars.remove(car);
            powerCars.remove(car);
            removeCarFromCarsForEachOwner(car);
            removeCarFromOwnerIdForOwner(car);
            return car;
        }
        return null;
    }

    private void removeCarFromBrands(Car car) {
        brands.get(car.getBrand()).remove(car.getCarId());
        if (brands.get(car.getBrand()).isEmpty()) {
            brands.remove(car.getBrand());
        }
    }
    private void removeCarFromCarsForEachOwner(Car car) {
        int ownerId = car.getOwnerId();
        Owner owner = ownerIdForOwner.get(ownerId);
        carsForEachOwner.get(owner).remove(car.getCarId());
        if (carsForEachOwner.get(owner).isEmpty()) {
            carsForEachOwner.remove(owner);
        }
    }
    private void removeCarFromOwnerIdForOwner(Car car) {
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
    public void addNewCar(Car car, Owner owner) {
        cars.put(car.getCarId(), car);
        addNewCarToBrands(car);
        velocityCars.add(car);
        powerCars.add(car);
        addNewCarToOwnerIdForOwner(car, owner);
        addNewCarToCarsForEachOwner(car, owner);
    }

    private void addNewCarToBrands(Car car) {
        if (brands.containsKey(car.getBrand())) {
            brands.get(car.getBrand()).put(car.getCarId(), car);
        } else {
            brands.put(car.getBrand(), new HashMap<Integer, Car>());
            brands.get(car.getBrand()).put(car.getCarId(), car);
        }
    }

    private void addNewCarToOwnerIdForOwner(Car car, Owner owner) {
        if (!ownerIdForOwner.containsKey(car.getOwnerId())) {
            ownerIdForOwner.put(car.getOwnerId(), owner);
        }
    }

    private void addNewCarToCarsForEachOwner(Car car, Owner owner) {
        if (carsForEachOwner.containsKey(owner)) {
            carsForEachOwner.get(owner).put(car.getCarId(), car);
        } else {
            carsForEachOwner.put(owner, new HashMap<Integer, Car>());
            carsForEachOwner.get(owner).put(car.getCarId(), car);
        }
    }
}
