package kz.aitu.factories.factory;

import kz.aitu.factories.family.Metro;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;
import kz.aitu.factories.product.metro.MetroDrone;
import kz.aitu.factories.product.metro.MetroPayment;
import kz.aitu.factories.product.metro.MetroRoutePlanner;

public class MetroFactory implements SystemFactory<Metro> {
    public Drone<Metro> createDrone() { return new MetroDrone(); }
    public RoutePlanner<Metro> createRoutePlanner() { return new MetroRoutePlanner(); }
    public PaymentGateway<Metro> createPaymentGateway() { return new MetroPayment(); }
}
