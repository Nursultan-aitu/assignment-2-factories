package kz.aitu.factories.product.campus;

import kz.aitu.factories.family.Campus;
import kz.aitu.factories.product.PaymentGateway;

public class CampusPayment implements PaymentGateway<Campus> {
    public String charge(double amount) { return "CampusPass charged " + amount + " KZT"; }
}
