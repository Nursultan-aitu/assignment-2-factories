package kz.aitu.factories.dispatch;

import kz.aitu.factories.family.NetworkFamily;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.RoutePlanner;

public class EcoDispatch<F extends NetworkFamily> implements DispatchStrategy<F> {
    @Override
    public String dispatch(Drone<F> drone, RoutePlanner<F> routePlanner, double distanceKm) {
        return "ECO: batch-friendly " + routePlanner.planRoute(distanceKm) + "; " + drone.fly(distanceKm);
    }

    @Override
    public double priceMultiplier() { return 0.85; }
}
