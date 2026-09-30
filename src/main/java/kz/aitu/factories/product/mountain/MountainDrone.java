package kz.aitu.factories.product.mountain;

import kz.aitu.factories.family.Mountain;
import kz.aitu.factories.product.Drone;

public class MountainDrone implements Drone<Mountain> {
    public String identifier() { return "H-DRONE"; }
    public double maxWeightKg() { return 2.0; }
    public String fly(double distanceKm) { return "Mountain drone performs high-altitude flight for " + distanceKm + " km"; }
}
