package kz.aitu.factories.product;

import kz.aitu.factories.family.NetworkFamily;

public interface Drone<F extends NetworkFamily> {
    String identifier();
    double maxWeightKg();
    String fly(double distanceKm);
}
