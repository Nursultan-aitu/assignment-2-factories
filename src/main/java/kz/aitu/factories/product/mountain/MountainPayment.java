package kz.aitu.factories.product.mountain;

import kz.aitu.factories.family.Mountain;
import kz.aitu.factories.product.PaymentGateway;

public class MountainPayment implements PaymentGateway<Mountain> {
    public String charge(double amount) { return "SummitPay charged " + amount + " KZT"; }
}
