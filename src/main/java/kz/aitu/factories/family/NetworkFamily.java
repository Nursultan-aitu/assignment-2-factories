package kz.aitu.factories.family;

/** Marker interface for a compatible delivery network family. */
public sealed interface NetworkFamily permits Metro, Campus, Coastal, Mountain {
    String name();
}
