package kz.aitu.factories.product.coastal;

import kz.aitu.factories.family.Coastal;
import kz.aitu.factories.product.Drone;

public class CoastalDrone implements Drone<Coastal> {
    public String identifier() { return "S-DRONE"; }
    public double maxWeightKg() { return 4.0; }
    public String fly(double distanceKm) { return "Coastal drone handles sea wind for " + distanceKm + " km"; }
}
