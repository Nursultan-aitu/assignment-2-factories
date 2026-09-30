package kz.aitu.factories.application;

public record DeliveryOrder(String id, double weightKg, double distanceKm, DeliveryMode mode) {
    public DeliveryOrder {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id must not be blank");
        }
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }
    }
}
