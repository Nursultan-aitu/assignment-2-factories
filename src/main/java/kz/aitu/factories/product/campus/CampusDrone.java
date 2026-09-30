package kz.aitu.factories.product.campus;

import kz.aitu.factories.family.Campus;
import kz.aitu.factories.product.Drone;

public class CampusDrone implements Drone<Campus> {
    public String identifier() { return "C-DRONE"; }
    public double maxWeightKg() { return 3.0; }
    public String fly(double distanceKm) { return "Campus drone uses quiet flight path for " + distanceKm + " km"; }
}
