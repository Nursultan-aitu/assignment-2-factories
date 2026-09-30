package kz.aitu.factories.product.coastal;

import kz.aitu.factories.family.Coastal;
import kz.aitu.factories.product.RoutePlanner;

public class CoastalRoutePlanner implements RoutePlanner<Coastal> {
    public String planRoute(double distanceKm) { return "Coastal wind-aware route planned for " + distanceKm + " km"; }
}
