package kz.aitu.factories.dispatch;

import kz.aitu.factories.family.NetworkFamily;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.RoutePlanner;

/** Product role in the Factory Method pattern. */
public interface DispatchStrategy<F extends NetworkFamily> {
    String dispatch(Drone<F> drone, RoutePlanner<F> routePlanner, double distanceKm);

    double priceMultiplier();
}
