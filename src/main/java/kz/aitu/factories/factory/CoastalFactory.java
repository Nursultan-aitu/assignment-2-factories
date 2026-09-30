package kz.aitu.factories.factory;

import kz.aitu.factories.family.Coastal;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;
import kz.aitu.factories.product.coastal.CoastalDrone;
import kz.aitu.factories.product.coastal.CoastalPayment;
import kz.aitu.factories.product.coastal.CoastalRoutePlanner;

public class CoastalFactory implements SystemFactory<Coastal> {
    public Drone<Coastal> createDrone() { return new CoastalDrone(); }
    public RoutePlanner<Coastal> createRoutePlanner() { return new CoastalRoutePlanner(); }
    public PaymentGateway<Coastal> createPaymentGateway() { return new CoastalPayment(); }
}
