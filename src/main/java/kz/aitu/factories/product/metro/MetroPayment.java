package kz.aitu.factories.product.metro;

import kz.aitu.factories.family.Metro;
import kz.aitu.factories.product.PaymentGateway;

public class MetroPayment implements PaymentGateway<Metro> {
    public double calculateBasePrice(double distanceKm) { return distanceKm * 1000.0; }
    public String gatewayName() { return "MetroWallet"; }
}
