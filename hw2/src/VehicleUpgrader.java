/**
 * Upgrades any type Vehicle to another type of Vehicle
 * E.g. Car -&gt; SuperCar
 */
public interface VehicleUpgrader<T, U> {
    T upgrade(U vehicle);
}