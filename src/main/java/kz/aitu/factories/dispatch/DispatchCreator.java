package kz.aitu.factories.dispatch;

import kz.aitu.factories.family.NetworkFamily;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.RoutePlanner;

/**
 * Creator role in Factory Method. scheduleDelivery is meaningful business logic;
 * subclasses only choose the dispatch strategy to use.
 */
public abstract class DispatchCreator<F extends NetworkFamily> {
    protected abstract DispatchStrategy<F> createStrategy();

    public final String scheduleDelivery(Drone<F> drone, RoutePlanner<F> routePlanner,
                                         double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }
        if (weightKg > drone.maxWeightKg()) {
            throw new IllegalArgumentException("Order exceeds drone capacity");
        }
        return createStrategy().dispatch(drone, routePlanner, distanceKm);
    }
}
