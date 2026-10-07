package kz.aitu.factories.product;

import kz.aitu.factories.family.NetworkFamily;

import java.util.Locale;

public interface PaymentGateway<F extends NetworkFamily> {
    double calculateBasePrice(double distanceKm);

    String gatewayName();

    default String charge(double amount) {
        return gatewayName() + " charged "
                + String.format(Locale.ROOT, "%.2f", amount) + " KZT";
    }
}
