package kz.aitu.factories.product.metro;

import kz.aitu.factories.family.Metro;
import kz.aitu.factories.product.RoutePlanner;

public class MetroRoutePlanner implements RoutePlanner<Metro> {
    public String planRoute(double distanceKm) { return "Metro air route planned for " + distanceKm + " km"; }
}
