package kz.aitu.factories.factory;

import kz.aitu.factories.family.NetworkFamily;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;

/** Abstract Factory: creates a compatible set of three delivery products. */
public interface SystemFactory<F extends NetworkFamily> {
    Drone<F> createDrone();
    RoutePlanner<F> createRoutePlanner();
    PaymentGateway<F> createPaymentGateway();
}
