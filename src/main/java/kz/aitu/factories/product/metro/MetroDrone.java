package kz.aitu.factories.product.metro;

import kz.aitu.factories.family.Metro;
import kz.aitu.factories.product.Drone;

public class MetroDrone implements Drone<Metro> {
    public String identifier() { return "M-DRONE"; }
    public double maxWeightKg() { return 5.0; }
    public String fly(double distanceKm) { return "Metro drone flies through city corridors for " + distanceKm + " km"; }
}
