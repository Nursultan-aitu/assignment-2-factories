# Assignment 2 Report

## Domain and problem

The domain is a drone delivery service operating in four networks: Metro, Campus, Coastal and Mountain. Each network has different flight constraints, route rules and payment systems. A delivery needs a drone, a route planner and a payment gateway that belong to the same network.

## Part A: direct creation problem

`legacy/LegacyDeliveryService` preserves the initial implementation. It uses a large `if/else` and directly creates classes such as `MetroDrone`, `MetroRoutePlanner` and `MetroPayment`.

This approach has four concrete problems:

1. The client depends on every concrete class.
2. Creation rules are duplicated for every network.
3. Adding a network requires editing old client code.
4. A client could accidentally combine products from different networks.

## Factory Method

`DispatchCreator<F>` is the Creator. Its `scheduleDelivery` method validates inputs and capacity, then delegates creation of a dispatch strategy to `createStrategy`. `PriorityCreator`, `StandardCreator` and `EcoCreator` are Concrete Creators. Their products are `PriorityDispatch`, `StandardDispatch` and `EcoDispatch`.

The Factory Method is justified because dispatch selection varies but the validation and scheduling flow is shared.

Each dispatch strategy also contributes a price multiplier: Priority adds 40%, Standard keeps the base price, and Eco applies a 15% discount.

## Abstract Factory

`SystemFactory<F>` is the Abstract Factory. Its three creation methods produce a `Drone<F>`, `RoutePlanner<F>` and `PaymentGateway<F>`. `MetroFactory`, `CampusFactory`, `CoastalFactory` and `MountainFactory` are Concrete Factories.

The generic marker type `F extends NetworkFamily` enforces family consistency. `DeliveryPlatform<F>` accepts only products of the same generic family. For example, Java rejects a normal attempt to combine a Metro drone with a Campus payment gateway.

Concrete payment products have meaningful pricing behavior. Metro charges 1000 KZT/km, Campus 700 KZT/km, Coastal 1300 KZT/km and Mountain 1800 KZT/km. `DeliveryPlatform` combines the selected family's base tariff with the selected Factory Method strategy multiplier.

## Runtime selection and UI

`FactorySelector` selects the concrete factory from an external network name. The Swing interface exposes this selection as a combo box, so the client does not hard-code a concrete factory throughout its business logic.

## Extension result

The fourth family Mountain adds a drone, route planner, payment gateway and `MountainFactory`. The generic platform and Factory Method algorithm required no changes. This demonstrates the Open/Closed Principle for the business layer.

## Testing

The project includes 22 JUnit tests. They test all original factories, concrete product creation, compatibility through generic abstractions, runtime selection, three business operations, family tariffs, delivery-mode multipliers, negative scenarios and the Mountain family.

## UML traceability

`docs/assignment2-uml.puml` corresponds directly to source code: `SystemFactory` and its implementations represent Abstract Factory; `DispatchCreator` and its implementations represent Factory Method; `DeliveryAppFrame` is the client; and `DeliveryPlatform` coordinates compatible products.
