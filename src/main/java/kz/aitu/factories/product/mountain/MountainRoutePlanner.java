package kz.aitu.factories.product.mountain;

import kz.aitu.factories.family.Mountain;
import kz.aitu.factories.product.RoutePlanner;

public class MountainRoutePlanner implements RoutePlanner<Mountain> {
    public String planRoute(double distanceKm) { return "Mountain ridge-safe route planned for " + distanceKm + " km"; }
}
