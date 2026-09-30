package kz.aitu.factories.product.coastal;

import kz.aitu.factories.family.Coastal;
import kz.aitu.factories.product.PaymentGateway;

public class CoastalPayment implements PaymentGateway<Coastal> {
    public String charge(double amount) { return "HarborPay charged " + amount + " KZT"; }
}
