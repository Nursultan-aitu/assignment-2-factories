package kz.aitu.factories;

import com.formdev.flatlaf.FlatDarkLaf;
import kz.aitu.factories.gui.DeliveryAppFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        SwingUtilities.invokeLater(() -> new DeliveryAppFrame().setVisible(true));
    }
}
