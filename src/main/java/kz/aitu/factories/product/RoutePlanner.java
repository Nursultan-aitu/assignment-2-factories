package kz.aitu.factories.product;

import kz.aitu.factories.family.NetworkFamily;

public interface RoutePlanner<F extends NetworkFamily> {
    String planRoute(double distanceKm);
}
