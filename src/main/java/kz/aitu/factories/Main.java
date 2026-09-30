package kz.aitu.factories;

import kz.aitu.factories.application.DeliveryMode;
import kz.aitu.factories.application.DeliveryOrder;
import kz.aitu.factories.application.DeliveryPlatform;
import kz.aitu.factories.factory.SystemFactory;
import kz.aitu.factories.selection.FactorySelector;

public class Main {
    public static void main(String[] args) {
        String chosenFamily = args.length == 0 ? "METRO" : args[0];
        SystemFactory<?> factory = FactorySelector.select(chosenFamily);
        DeliveryPlatform<?> platform = DeliveryPlatform.from(factory);

        DeliveryOrder order = new DeliveryOrder("ORD-17", 2.0, 5.0, DeliveryMode.PRIORITY);
        System.out.println(platform.fulfil(order));
        System.out.println(platform.quote(order));
        System.out.println(platform.routePreview(order));
    }
}
