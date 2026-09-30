package kz.aitu.factories.factory;

import kz.aitu.factories.family.Mountain;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;
import kz.aitu.factories.product.mountain.MountainDrone;
import kz.aitu.factories.product.mountain.MountainPayment;
import kz.aitu.factories.product.mountain.MountainRoutePlanner;

/** Fourth family added without changing DeliveryPlatform or dispatch business logic. */
public class MountainFactory implements SystemFactory<Mountain> {
    public Drone<Mountain> createDrone() { return new MountainDrone(); }
    public RoutePlanner<Mountain> createRoutePlanner() { return new MountainRoutePlanner(); }
    public PaymentGateway<Mountain> createPaymentGateway() { return new MountainPayment(); }
}
