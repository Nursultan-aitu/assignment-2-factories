package kz.aitu.factories;

import kz.aitu.factories.application.DeliveryMode;
import kz.aitu.factories.application.DeliveryOrder;
import kz.aitu.factories.application.DeliveryPlatform;
import kz.aitu.factories.factory.CampusFactory;
import kz.aitu.factories.factory.CoastalFactory;
import kz.aitu.factories.factory.MetroFactory;
import kz.aitu.factories.factory.MountainFactory;
import kz.aitu.factories.factory.SystemFactory;
import kz.aitu.factories.product.campus.CampusDrone;
import kz.aitu.factories.product.campus.CampusPayment;
import kz.aitu.factories.product.campus.CampusRoutePlanner;
import kz.aitu.factories.product.coastal.CoastalDrone;
import kz.aitu.factories.product.coastal.CoastalPayment;
import kz.aitu.factories.product.coastal.CoastalRoutePlanner;
import kz.aitu.factories.product.metro.MetroDrone;
import kz.aitu.factories.product.metro.MetroPayment;
import kz.aitu.factories.product.metro.MetroRoutePlanner;
import kz.aitu.factories.product.mountain.MountainDrone;
import kz.aitu.factories.product.mountain.MountainPayment;
import kz.aitu.factories.product.mountain.MountainRoutePlanner;
import kz.aitu.factories.selection.FactorySelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryFactoryTest {
    private static DeliveryOrder order(DeliveryMode mode) {
        return new DeliveryOrder("ORD-1", 1.0, 5.0, mode);
    }

    @Test
    void metroFactoryCreatesOnlyMetroProducts() {
        MetroFactory factory = new MetroFactory();
        assertInstanceOf(MetroDrone.class, factory.createDrone());
        assertInstanceOf(MetroRoutePlanner.class, factory.createRoutePlanner());
        assertInstanceOf(MetroPayment.class, factory.createPaymentGateway());
    }

    @Test
    void campusFactoryCreatesOnlyCampusProducts() {
        CampusFactory factory = new CampusFactory();
        assertInstanceOf(CampusDrone.class, factory.createDrone());
        assertInstanceOf(CampusRoutePlanner.class, factory.createRoutePlanner());
        assertInstanceOf(CampusPayment.class, factory.createPaymentGateway());
    }

    @Test
    void coastalFactoryCreatesOnlyCoastalProducts() {
        CoastalFactory factory = new CoastalFactory();
        assertInstanceOf(CoastalDrone.class, factory.createDrone());
        assertInstanceOf(CoastalRoutePlanner.class, factory.createRoutePlanner());
        assertInstanceOf(CoastalPayment.class, factory.createPaymentGateway());
    }

    @Test
    void mountainFactoryCreatesOnlyMountainProducts() {
        MountainFactory factory = new MountainFactory();
        assertInstanceOf(MountainDrone.class, factory.createDrone());
        assertInstanceOf(MountainRoutePlanner.class, factory.createRoutePlanner());
        assertInstanceOf(MountainPayment.class, factory.createPaymentGateway());
    }

    @Test
    void selectorChoosesMetroFactoryAtRuntime() {
        assertInstanceOf(MetroFactory.class, FactorySelector.select("metro"));
    }

    @Test
    void selectorChoosesMountainFactoryAtRuntime() {
        assertInstanceOf(MountainFactory.class, FactorySelector.select("MOUNTAIN"));
    }

    @Test
    void priorityFulfilmentUsesPriorityStrategy() {
        String result = DeliveryPlatform.from(new MetroFactory()).fulfil(order(DeliveryMode.PRIORITY));
        assertTrue(result.contains("PRIORITY"));
    }

    @Test
    void standardFulfilmentUsesStandardStrategy() {
        String result = DeliveryPlatform.from(new CampusFactory()).fulfil(order(DeliveryMode.STANDARD));
        assertTrue(result.contains("STANDARD"));
    }

    @Test
    void ecoFulfilmentUsesEcoStrategy() {
        String result = DeliveryPlatform.from(new CoastalFactory()).fulfil(order(DeliveryMode.ECO));
        assertTrue(result.contains("batch-friendly"));
    }

    @Test
    void routePreviewUsesTheFamilyRoutePlanner() {
        String result = DeliveryPlatform.from(new CoastalFactory()).routePreview(order(DeliveryMode.STANDARD));
        assertTrue(result.contains("wind-aware"));
        assertTrue(result.contains("S-DRONE"));
    }

    @Test
    void quoteUsesTheFamilyPaymentGateway() {
        String result = DeliveryPlatform.from(new MetroFactory()).quote(order(DeliveryMode.STANDARD));
        assertTrue(result.contains("MetroWallet"));
        assertTrue(result.contains("Metro air route"));
    }

    @Test
    void heavyMountainOrderIsRejected() {
        DeliveryOrder heavyOrder = new DeliveryOrder("HEAVY", 3.0, 2.0, DeliveryMode.STANDARD);
        assertThrows(IllegalArgumentException.class,
                () -> DeliveryPlatform.from(new MountainFactory()).fulfil(heavyOrder));
    }

    @Test
    void unknownFamilyIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> FactorySelector.select("DESERT"));
    }

    @Test
    void invalidOrderWeightIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DeliveryOrder("BAD", 0.0, 2.0, DeliveryMode.ECO));
    }

    @Test
    void clientCanWorkThroughAbstractFactoryType() {
        SystemFactory<?> factory = FactorySelector.select("CAMPUS");
        DeliveryPlatform<?> platform = DeliveryPlatform.from(factory);
        assertTrue(platform.fulfil(order(DeliveryMode.STANDARD)).contains("CampusPass"));
    }

    @Test
    void newMountainFamilySupportsFullBusinessProcess() {
        String result = DeliveryPlatform.from(new MountainFactory()).fulfil(order(DeliveryMode.PRIORITY));
        assertTrue(result.contains("SummitPay"));
        assertTrue(result.contains("high-altitude"));
    }

    @Test
    void metroUsesItsOwnStandardTariff() {
        assertEquals(5000.0,
                DeliveryPlatform.from(new MetroFactory()).calculatePrice(order(DeliveryMode.STANDARD)));
    }

    @Test
    void campusUsesItsOwnStandardTariff() {
        assertEquals(3500.0,
                DeliveryPlatform.from(new CampusFactory()).calculatePrice(order(DeliveryMode.STANDARD)));
    }

    @Test
    void coastalUsesItsOwnStandardTariff() {
        assertEquals(6500.0,
                DeliveryPlatform.from(new CoastalFactory()).calculatePrice(order(DeliveryMode.STANDARD)));
    }

    @Test
    void mountainUsesItsOwnStandardTariff() {
        assertEquals(9000.0,
                DeliveryPlatform.from(new MountainFactory()).calculatePrice(order(DeliveryMode.STANDARD)));
    }

    @Test
    void priorityModeAddsFortyPercent() {
        assertEquals(7000.0,
                DeliveryPlatform.from(new MetroFactory()).calculatePrice(order(DeliveryMode.PRIORITY)));
    }

    @Test
    void ecoModeAppliesFifteenPercentDiscount() {
        assertEquals(4250.0,
                DeliveryPlatform.from(new MetroFactory()).calculatePrice(order(DeliveryMode.ECO)));
    }

    @Test
    void oneClickProcessExecutesThreeBusinessOperations() {
        String result = DeliveryPlatform.from(new CampusFactory())
                .processDelivery(order(DeliveryMode.STANDARD));
        assertTrue(result.contains("PREPARATION"));
        assertTrue(result.contains("QUOTE"));
        assertTrue(result.contains("DELIVERY"));
        assertTrue(result.contains("C-DRONE"));
        assertTrue(result.contains("CampusPass"));
    }
}
