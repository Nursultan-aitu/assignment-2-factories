package kz.aitu.factories.legacy;

/**
 * Part A snapshot: creation and business logic are intentionally coupled.
 * It will be refactored in later commits.
 */
public class LegacyDeliveryService {
    public String deliver(String network, double weightKg, double distanceKm) {
        if (network.equalsIgnoreCase("METRO")) {
            MetroDrone drone = new MetroDrone();
            MetroRoutePlanner planner = new MetroRoutePlanner();
            MetroPayment payment = new MetroPayment();
            return planner.plan(distanceKm) + " | " + drone.fly(distanceKm)
                    + " | " + payment.charge(distanceKm);
        } else if (network.equalsIgnoreCase("CAMPUS")) {
            CampusDrone drone = new CampusDrone();
            CampusRoutePlanner planner = new CampusRoutePlanner();
            CampusPayment payment = new CampusPayment();
            return planner.plan(distanceKm) + " | " + drone.fly(distanceKm)
                    + " | " + payment.charge(distanceKm);
        } else if (network.equalsIgnoreCase("COASTAL")) {
            CoastalDrone drone = new CoastalDrone();
            CoastalRoutePlanner planner = new CoastalRoutePlanner();
            CoastalPayment payment = new CoastalPayment();
            return planner.plan(distanceKm) + " | " + drone.fly(distanceKm)
                    + " | " + payment.charge(distanceKm);
        }
        throw new IllegalArgumentException("Unknown network: " + network);
    }
}

class MetroDrone { String fly(double km) { return "Metro drone flies through city corridors: " + km + " km"; } }
class MetroRoutePlanner { String plan(double km) { return "Metro route planned for " + km + " km"; } }
class MetroPayment { String charge(double km) { return "MetroWallet charged " + km * 1.8; } }

class CampusDrone { String fly(double km) { return "Campus drone uses quiet flight path: " + km + " km"; } }
class CampusRoutePlanner { String plan(double km) { return "Campus safe route planned for " + km + " km"; } }
class CampusPayment { String charge(double km) { return "CampusPass charged " + km * 1.2; } }

class CoastalDrone { String fly(double km) { return "Coastal drone handles sea wind: " + km + " km"; } }
class CoastalRoutePlanner { String plan(double km) { return "Coastal wind-aware route planned for " + km + " km"; } }
class CoastalPayment { String charge(double km) { return "HarborPay charged " + km * 2.1; } }
