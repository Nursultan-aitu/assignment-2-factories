# Drone Delivery Factory System

Java 21 Maven project for Assignment 2: Factory Method and Abstract Factory.

The application models drone delivery networks. A selected network supplies a compatible drone, route planner and payment gateway. The Swing UI lets a user choose the network family, delivery mode, order ID, weight and distance.

## Run

1. Open the project in IntelliJ IDEA with JDK 21.
2. Reload Maven dependencies.
3. Run `Main` to open the Swing application.
4. In the Maven tool window, run `Lifecycle -> test` to run the JUnit test suite.

## Product families

| Family | Drone | Route planner | Payment gateway |
|---|---|---|---|
| Metro | MetroDrone | MetroRoutePlanner | MetroPayment |
| Campus | CampusDrone | CampusRoutePlanner | CampusPayment |
| Coastal | CoastalDrone | CoastalRoutePlanner | CoastalPayment |
| Mountain | MountainDrone | MountainRoutePlanner | MountainPayment |

## Patterns

### Factory Method

`DispatchCreator<F>` contains the delivery scheduling algorithm: it validates weight and distance, checks drone capacity, then calls the Factory Method `createStrategy()`. `PriorityCreator`, `StandardCreator` and `EcoCreator` choose their corresponding `DispatchStrategy` products.

This is not a static factory: the base creator contains meaningful reusable business logic and subclasses vary one creation step inside that algorithm.

### Abstract Factory

`SystemFactory<F>` creates three related products: `Drone<F>`, `RoutePlanner<F>` and `PaymentGateway<F>`. Metro, Campus, Coastal and Mountain factories create their own compatible product family.

`DeliveryPlatform<F>` receives products with the same type parameter `F`. Thus a `Drone<Metro>` cannot be passed together with a `PaymentGateway<Campus>` in normal Java code. Compatibility is supported by the architecture rather than a late `if` check.

## Runtime family selection

`FactorySelector` converts a runtime network name into a concrete factory. The Swing client passes the user's selected family to this selector, then works through `SystemFactory<?>` and `DeliveryPlatform<?>`.

## Business operations

`DeliveryPlatform` provides:

- `fulfil` — plans a route, selects dispatch behavior, checks capacity, flies and charges payment;
- `quote` — calculates a family-specific payment result;
- `routePreview` — returns the selected family route.

## Fourth family extension

Mountain was added by introducing `Mountain`, three Mountain products and `MountainFactory`. Existing business classes `DeliveryPlatform` and `DispatchCreator` were not changed. Only `NetworkFamily`, `FactorySelector` and the UI family list required updates.

## Tests

`DeliveryFactoryTest` contains 16 automated tests covering factory products, runtime selection, business operations, Factory Method behavior, negative scenarios, generic abstraction use and the Mountain extension.

## UML

The PlantUML source is [docs/assignment2-uml.puml](docs/assignment2-uml.puml). It marks the Factory Method and Abstract Factory parts and shows all key relationships.
