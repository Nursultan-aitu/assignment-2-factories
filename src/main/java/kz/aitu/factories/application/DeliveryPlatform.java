package kz.aitu.factories.application;

import kz.aitu.factories.dispatch.DispatchCreator;
import kz.aitu.factories.dispatch.EcoCreator;
import kz.aitu.factories.dispatch.PriorityCreator;
import kz.aitu.factories.dispatch.StandardCreator;
import kz.aitu.factories.factory.SystemFactory;
import kz.aitu.factories.family.NetworkFamily;
import kz.aitu.factories.product.Drone;
import kz.aitu.factories.product.PaymentGateway;
import kz.aitu.factories.product.RoutePlanner;

/** Coordinates compatible products from one network family. */
public class DeliveryPlatform<F extends NetworkFamily> {
    private final Drone<F> drone;
    private final RoutePlanner<F> routePlanner;
    private final PaymentGateway<F> paymentGateway;

    private DeliveryPlatform(Drone<F> drone, RoutePlanner<F> routePlanner,
                             PaymentGateway<F> paymentGateway) {
        this.drone = drone;
        this.routePlanner = routePlanner;
        this.paymentGateway = paymentGateway;
    }

    public static <F extends NetworkFamily> DeliveryPlatform<F> from(SystemFactory<F> factory) {
        return new DeliveryPlatform<>(factory.createDrone(), factory.createRoutePlanner(),
                factory.createPaymentGateway());
    }

    /** Business operation: route, validate capacity, dispatch, pay and confirm. */
    public String fulfil(DeliveryOrder order) {
        String dispatch = creatorFor(order.mode()).scheduleDelivery(
                drone, routePlanner, order.weightKg(), order.distanceKm());
        return dispatch + " | " + paymentGateway.charge(calculatePrice(order))
                + " | Order " + order.id() + " confirmed";
    }

    /** Business operation: get a price through the family's payment product. */
    public String quote(DeliveryOrder order) {
        String route = routePlanner.planRoute(order.distanceKm());
        return route + " | " + paymentGateway.charge(calculatePrice(order));
    }

    /** Business operation: preview a route through the family's route product. */
    public String routePreview(DeliveryOrder order) {
        validateDroneCapacity(order);
        return routePlanner.planRoute(order.distanceKm()) + " | "
                + drone.identifier() + " approved for " + order.weightKg() + " kg";
    }

    /** One-click workflow that executes all three required business operations. */
    public String processDelivery(DeliveryOrder order) {
        String preparation = routePreview(order);
        String quotation = quote(order);
        String completion = fulfil(order);
        return "PREPARATION\n" + preparation
                + "\n\nQUOTE\n" + quotation
                + "\n\nDELIVERY\n" + completion;
    }

    public String droneIdentifier() {
        return drone.identifier();
    }

    public double maximumWeightKg() {
        return drone.maxWeightKg();
    }

    public double baseRatePerKm() {
        return paymentGateway.ratePerKm();
    }

    public double modePriceMultiplier(DeliveryMode mode) {
        return creatorFor(mode).priceMultiplier();
    }

    private void validateDroneCapacity(DeliveryOrder order) {
        if (order.weightKg() > drone.maxWeightKg()) {
            throw new IllegalArgumentException("Order exceeds drone capacity");
        }
    }

    public double calculatePrice(DeliveryOrder order) {
        double basePrice = paymentGateway.calculateBasePrice(order.distanceKm());
        double adjustedPrice = basePrice * creatorFor(order.mode()).priceMultiplier();
        return Math.round(adjustedPrice * 100.0) / 100.0;
    }

    private DispatchCreator<F> creatorFor(DeliveryMode mode) {
        return switch (mode) {
            case PRIORITY -> new PriorityCreator<>();
            case STANDARD -> new StandardCreator<>();
            case ECO -> new EcoCreator<>();
        };
    }
}
