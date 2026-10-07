package kz.aitu.factories.product.mountain;

import kz.aitu.factories.family.Mountain;
import kz.aitu.factories.product.PaymentGateway;

public class MountainPayment implements PaymentGateway<Mountain> {
    public double ratePerKm() { return 1800.0; }
    public String gatewayName() { return "SummitPay"; }
}
