package kz.aitu.factories.product.campus;

import kz.aitu.factories.family.Campus;
import kz.aitu.factories.product.RoutePlanner;

public class CampusRoutePlanner implements RoutePlanner<Campus> {
    public String planRoute(double distanceKm) { return "Campus pedestrian-safe route planned for " + distanceKm + " km"; }
}
