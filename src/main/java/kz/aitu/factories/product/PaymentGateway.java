package kz.aitu.factories.product;

import kz.aitu.factories.family.NetworkFamily;

public interface PaymentGateway<F extends NetworkFamily> {
    String charge(double amount);
}
