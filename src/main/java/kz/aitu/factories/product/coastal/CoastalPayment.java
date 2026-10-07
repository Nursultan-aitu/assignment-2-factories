package kz.aitu.factories.product.coastal;

import kz.aitu.factories.family.Coastal;
import kz.aitu.factories.product.PaymentGateway;

public class CoastalPayment implements PaymentGateway<Coastal> {
    public double ratePerKm() { return 1300.0; }
    public String gatewayName() { return "HarborPay"; }
}
