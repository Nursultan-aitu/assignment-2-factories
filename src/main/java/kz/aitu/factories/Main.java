package kz.aitu.factories;

import kz.aitu.factories.legacy.LegacyDeliveryService;

public class Main {
    public static void main(String[] args) {
        LegacyDeliveryService service = new LegacyDeliveryService();
        System.out.println(service.deliver("METRO", 2.0, 5.0));
        System.out.println(service.deliver("CAMPUS", 1.0, 2.0));
        System.out.println(service.deliver("COASTAL", 3.0, 7.0));
    }
}
