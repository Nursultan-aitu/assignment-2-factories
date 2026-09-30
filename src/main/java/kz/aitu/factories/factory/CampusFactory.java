package kz.aitu.factories.factory;

import kz.aitu.factories.family.Campus;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;
import kz.aitu.factories.product.campus.CampusDrone;
import kz.aitu.factories.product.campus.CampusPayment;
import kz.aitu.factories.product.campus.CampusRoutePlanner;

public class CampusFactory implements SystemFactory<Campus> {
    public Drone<Campus> createDrone() { return new CampusDrone(); }
    public RoutePlanner<Campus> createRoutePlanner() { return new CampusRoutePlanner(); }
    public PaymentGateway<Campus> createPaymentGateway() { return new CampusPayment(); }
}
