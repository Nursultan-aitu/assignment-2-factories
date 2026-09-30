package kz.aitu.factories;

import kz.aitu.factories.gui.DeliveryAppFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeliveryAppFrame().setVisible(true));
    }
}
