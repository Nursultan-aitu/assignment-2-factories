package kz.aitu.factories.product.campus;

import kz.aitu.factories.family.Campus;
import kz.aitu.factories.product.PaymentGateway;

public class CampusPayment implements PaymentGateway<Campus> {
    public double calculateBasePrice(double distanceKm) { return distanceKm * 700.0; }
    public String gatewayName() { return "CampusPass"; }
}
