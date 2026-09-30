package kz.aitu.factories.product.metro;

import kz.aitu.factories.family.Metro;
import kz.aitu.factories.product.PaymentGateway;

public class MetroPayment implements PaymentGateway<Metro> {
    public String charge(double amount) { return "MetroWallet charged " + amount + " KZT"; }
}
