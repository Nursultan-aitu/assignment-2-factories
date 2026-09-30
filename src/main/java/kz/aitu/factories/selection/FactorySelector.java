package kz.aitu.factories.selection;

import kz.aitu.factories.factory.CampusFactory;
import kz.aitu.factories.factory.CoastalFactory;
import kz.aitu.factories.factory.MetroFactory;
import kz.aitu.factories.factory.SystemFactory;

import java.util.Locale;

/** Selects a product family from an external value such as a command-line argument. */
public final class FactorySelector {
    private FactorySelector() {
    }

    public static SystemFactory<?> select(String familyName) {
        return switch (familyName.trim().toUpperCase(Locale.ROOT)) {
            case "METRO" -> new MetroFactory();
            case "CAMPUS" -> new CampusFactory();
            case "COASTAL" -> new CoastalFactory();
            default -> throw new IllegalArgumentException("Unknown network: " + familyName);
        };
    }
}
