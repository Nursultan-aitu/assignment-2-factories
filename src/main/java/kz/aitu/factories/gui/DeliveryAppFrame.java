package kz.aitu.factories.gui;

import kz.aitu.factories.application.DeliveryMode;
import kz.aitu.factories.application.DeliveryOrder;
import kz.aitu.factories.application.DeliveryPlatform;
import kz.aitu.factories.factory.SystemFactory;
import kz.aitu.factories.selection.FactorySelector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

/** A small graphical client: the user selects the product family at runtime. */
public class DeliveryAppFrame extends JFrame {
    private final JComboBox<String> familyBox = new JComboBox<>(new String[]{"METRO", "CAMPUS", "COASTAL"});
    private final JComboBox<DeliveryMode> modeBox = new JComboBox<>(DeliveryMode.values());
    private final JTextField orderIdField = new JTextField("ORD-17", 15);
    private final JTextField weightField = new JTextField("2.0", 15);
    private final JTextField distanceField = new JTextField("5.0", 15);
    private final JTextArea resultArea = new JTextArea(8, 42);

    public DeliveryAppFrame() {
        setTitle("Drone Delivery Factory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContent());
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Drone Delivery Factory System");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        addRow(form, 0, "Network family", familyBox);
        addRow(form, 1, "Delivery mode", modeBox);
        addRow(form, 2, "Order ID", orderIdField);
        addRow(form, 3, "Weight (kg)", weightField);
        addRow(form, 4, "Distance (km)", distanceField);

        JButton fulfilButton = new JButton("Fulfil delivery");
        fulfilButton.addActionListener(event -> fulfilOrder());
        JButton quoteButton = new JButton("Get quote");
        quoteButton.addActionListener(event -> showQuote());
        JButton routeButton = new JButton("Preview route");
        routeButton.addActionListener(event -> showRoute());
        JPanel buttons = new JPanel(new GridLayout(1, 3, 8, 0));
        buttons.add(fulfilButton);
        buttons.add(quoteButton);
        buttons.add(routeButton);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(form, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        root.add(new JScrollPane(resultArea), BorderLayout.SOUTH);
        return root;
    }

    private void addRow(JPanel panel, int row, String label, java.awt.Component component) {
        GridBagConstraints left = new GridBagConstraints();
        left.gridx = 0;
        left.gridy = row;
        left.anchor = GridBagConstraints.WEST;
        left.insets = new Insets(4, 0, 4, 12);
        panel.add(new JLabel(label + ":"), left);

        GridBagConstraints right = new GridBagConstraints();
        right.gridx = 1;
        right.gridy = row;
        right.weightx = 1;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.insets = new Insets(4, 0, 4, 0);
        panel.add(component, right);
    }

    private void fulfilOrder() {
        execute("Delivery confirmed", platform -> platform.fulfil(readOrder()));
    }

    private void showQuote() {
        execute("Quote", platform -> platform.quote(readOrder()));
    }

    private void showRoute() {
        execute("Route preview", platform -> platform.routePreview(readOrder()));
    }

    private void execute(String heading, PlatformOperation operation) {
        try {
            SystemFactory<?> factory = FactorySelector.select((String) familyBox.getSelectedItem());
            DeliveryPlatform<?> platform = DeliveryPlatform.from(factory);
            resultArea.setText(heading + "\n\n" + operation.apply(platform));
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DeliveryOrder readOrder() {
        return new DeliveryOrder(orderIdField.getText().trim(),
                Double.parseDouble(weightField.getText().trim()),
                Double.parseDouble(distanceField.getText().trim()),
                (DeliveryMode) modeBox.getSelectedItem());
    }

    @FunctionalInterface
    private interface PlatformOperation {
        String apply(DeliveryPlatform<?> platform);
    }
}
