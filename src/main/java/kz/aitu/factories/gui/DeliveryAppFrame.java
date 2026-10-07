package kz.aitu.factories.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import kz.aitu.factories.application.DeliveryMode;
import kz.aitu.factories.application.DeliveryOrder;
import kz.aitu.factories.application.DeliveryPlatform;
import kz.aitu.factories.factory.SystemFactory;
import kz.aitu.factories.selection.FactorySelector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Map;

/** Graphical client that selects an Abstract Factory at runtime. */
public class DeliveryAppFrame extends JFrame {
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color SUCCESS = new Color(22, 163, 74);
    private static final Color ERROR = new Color(220, 38, 38);
    private static final Map<String, Color> FAMILY_COLORS = Map.of(
            "METRO", new Color(37, 99, 235),
            "CAMPUS", new Color(22, 163, 74),
            "COASTAL", new Color(8, 145, 178),
            "MOUNTAIN", new Color(124, 58, 237));

    private final JComboBox<String> familyBox =
            new JComboBox<>(new String[]{"METRO", "CAMPUS", "COASTAL", "MOUNTAIN"});
    private final JComboBox<DeliveryMode> modeBox = new JComboBox<>(DeliveryMode.values());
    private final JTextField orderIdField = new JTextField("ORD-17");
    private final JTextField weightField = new JTextField("2.0");
    private final JTextField distanceField = new JTextField("5.0");

    private final JLabel errorLabel = new JLabel(" ");
    private final JLabel capacityHint = new JLabel();
    private final JLabel statusValue = new JLabel("Ready", SwingConstants.CENTER);
    private final JLabel networkValue = new JLabel("-");
    private final JLabel modeValue = new JLabel("-");
    private final JLabel droneValue = new JLabel("-");
    private final JLabel capacityValue = new JLabel("-");
    private final JLabel distanceValue = new JLabel("-");
    private final JLabel priceValue = new JLabel("-");
    private final JTextArea detailsArea = new JTextArea();
    private final JPanel familyAccent = new JPanel();
    private final JButton themeButton = new JButton("Dark mode");

    private boolean darkMode;

    public DeliveryAppFrame() {
        setTitle("Drone Delivery Factory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 580));
        setContentPane(createContent());
        bindEvents();
        updateFamilyInformation();
        pack();
        setSize(980, 620);
        setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel root = new JPanel(new BorderLayout(0, 22));
        root.setBorder(BorderFactory.createEmptyBorder(24, 28, 26, 28));
        root.add(createHeader(), BorderLayout.NORTH);

        JPanel columns = new JPanel(new GridLayout(1, 2, 20, 0));
        columns.add(createInputCard());
        columns.add(createSummaryCard());
        root.add(columns, BorderLayout.CENTER);
        return root;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Drone Delivery System");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        JLabel subtitle = new JLabel("Configure a compatible delivery network and dispatch strategy");
        subtitle.setForeground(UIManager.getColor("Label.disabledForeground"));
        text.add(title);
        text.add(Box.createVerticalStrut(5));
        text.add(subtitle);

        themeButton.setFocusable(false);
        themeButton.addActionListener(event -> toggleTheme());
        header.add(text, BorderLayout.CENTER);
        header.add(themeButton, BorderLayout.EAST);
        return header;
    }

    private JPanel createInputCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 18));

        familyAccent.setPreferredSize(new Dimension(7, 30));
        JPanel titleWrap = new JPanel(new BorderLayout());
        titleWrap.add(familyAccent, BorderLayout.WEST);
        titleWrap.add(sectionTitle("Delivery details"), BorderLayout.CENTER);
        card.add(titleWrap, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        addRow(form, 0, "Network family", familyBox, null);
        addRow(form, 1, "Delivery mode", modeBox, null);
        addRow(form, 2, "Order ID", orderIdField, null);
        addRow(form, 3, "Weight", weightField, "kg");
        addRow(form, 4, "Distance", distanceField, "km");

        capacityHint.setFont(capacityHint.getFont().deriveFont(12f));
        capacityHint.setForeground(UIManager.getColor("Label.disabledForeground"));
        GridBagConstraints hint = constraints(1, 5);
        hint.gridwidth = 2;
        hint.anchor = GridBagConstraints.WEST;
        hint.insets = new Insets(0, 0, 6, 0);
        form.add(capacityHint, hint);
        card.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        errorLabel.setForeground(ERROR);
        errorLabel.setFont(errorLabel.getFont().deriveFont(12f));
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton createButton = new JButton("Create delivery");
        createButton.setForeground(Color.WHITE);
        createButton.setBackground(PRIMARY);
        createButton.setFont(createButton.getFont().deriveFont(Font.BOLD));
        createButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        createButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        createButton.addActionListener(event -> fulfilOrder());

        JButton quoteButton = new JButton("Get quote");
        quoteButton.addActionListener(event -> showQuote());
        JButton routeButton = new JButton("Preview route");
        routeButton.addActionListener(event -> showRoute());
        JPanel secondary = new JPanel(new GridLayout(1, 2, 10, 0));
        secondary.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondary.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        secondary.add(quoteButton);
        secondary.add(routeButton);

        actions.add(errorLabel);
        actions.add(Box.createVerticalStrut(8));
        actions.add(createButton);
        actions.add(Box.createVerticalStrut(10));
        actions.add(secondary);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createSummaryCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));

        JPanel heading = new JPanel(new BorderLayout());
        heading.add(sectionTitle("Delivery summary"), BorderLayout.WEST);
        styleStatus("Ready", PRIMARY);
        heading.add(statusValue, BorderLayout.EAST);
        card.add(heading, BorderLayout.NORTH);

        JPanel summary = new JPanel(new GridLayout(6, 2, 10, 11));
        addSummaryRow(summary, "Network", networkValue);
        addSummaryRow(summary, "Mode", modeValue);
        addSummaryRow(summary, "Drone", droneValue);
        addSummaryRow(summary, "Maximum load", capacityValue);
        addSummaryRow(summary, "Distance", distanceValue);
        addSummaryRow(summary, "Price", priceValue);

        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setText("Choose delivery settings and select an operation.");
        detailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBorder(BorderFactory.createTitledBorder("Operation details"));

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.add(summary, BorderLayout.NORTH);
        body.add(detailsScroll, BorderLayout.CENTER);
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 163, 184, 100), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return card;
    }

    private JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));
        label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        return label;
    }

    private void addRow(JPanel panel, int row, String labelText, JComponent component, String suffix) {
        GridBagConstraints label = constraints(0, row);
        label.anchor = GridBagConstraints.WEST;
        label.insets = new Insets(7, 0, 7, 14);
        panel.add(new JLabel(labelText), label);

        GridBagConstraints field = constraints(1, row);
        field.weightx = 1;
        field.fill = GridBagConstraints.HORIZONTAL;
        field.insets = new Insets(7, 0, 7, 8);
        panel.add(component, field);

        if (suffix != null) {
            GridBagConstraints unit = constraints(2, row);
            unit.anchor = GridBagConstraints.WEST;
            unit.insets = new Insets(7, 0, 7, 0);
            JLabel unitLabel = new JLabel(suffix);
            unitLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
            panel.add(unitLabel, unit);
        }
    }

    private GridBagConstraints constraints(int column, int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        return constraints;
    }

    private void addSummaryRow(JPanel panel, String labelText, JLabel value) {
        JLabel label = new JLabel(labelText);
        label.setForeground(UIManager.getColor("Label.disabledForeground"));
        value.setFont(value.getFont().deriveFont(Font.BOLD));
        panel.add(label);
        panel.add(value);
    }

    private void bindEvents() {
        familyBox.addActionListener(event -> updateFamilyInformation());
        modeBox.addActionListener(event -> modeValue.setText(String.valueOf(modeBox.getSelectedItem())));
    }

    private void fulfilOrder() {
        execute("Confirmed", SUCCESS, DeliveryPlatform::fulfil);
    }

    private void showQuote() {
        execute("Quote ready", PRIMARY, DeliveryPlatform::quote);
    }

    private void showRoute() {
        execute("Route ready", PRIMARY, DeliveryPlatform::routePreview);
    }

    private void execute(String status, Color statusColor, PlatformOperation operation) {
        resetValidation();
        try {
            DeliveryOrder order = readOrder();
            DeliveryPlatform<?> platform = selectedPlatform();
            String result = operation.apply(platform, order);
            updateSummary(order, platform, status, statusColor, result);
        } catch (NumberFormatException exception) {
            showInputError("Weight and distance must be numbers.", null);
        } catch (IllegalArgumentException exception) {
            JTextField field = exception.getMessage().toLowerCase().contains("capacity") ? weightField : null;
            showInputError(exception.getMessage(), field);
        }
    }

    private DeliveryOrder readOrder() {
        if (orderIdField.getText().isBlank()) {
            throw invalidInput("Order ID must not be blank.", orderIdField);
        }
        double weight = Double.parseDouble(weightField.getText().trim());
        double distance = Double.parseDouble(distanceField.getText().trim());
        if (weight <= 0) {
            throw invalidInput("Weight must be greater than zero.", weightField);
        }
        if (distance <= 0) {
            throw invalidInput("Distance must be greater than zero.", distanceField);
        }
        return new DeliveryOrder(orderIdField.getText().trim(), weight, distance,
                (DeliveryMode) modeBox.getSelectedItem());
    }

    private IllegalArgumentException invalidInput(String message, JTextField field) {
        field.setBorder(BorderFactory.createLineBorder(ERROR, 2, true));
        field.requestFocusInWindow();
        return new IllegalArgumentException(message);
    }

    private DeliveryPlatform<?> selectedPlatform() {
        SystemFactory<?> factory = FactorySelector.select(String.valueOf(familyBox.getSelectedItem()));
        return DeliveryPlatform.from(factory);
    }

    private void updateSummary(DeliveryOrder order, DeliveryPlatform<?> platform, String status,
                               Color statusColor, String details) {
        networkValue.setText(String.valueOf(familyBox.getSelectedItem()));
        modeValue.setText(order.mode().name());
        droneValue.setText(platform.droneIdentifier());
        capacityValue.setText(platform.maximumWeightKg() + " kg");
        distanceValue.setText(order.distanceKm() + " km");
        priceValue.setText((order.distanceKm() * 1000.0) + " KZT");
        detailsArea.setText(details);
        detailsArea.setCaretPosition(0);
        styleStatus(status, statusColor);
    }

    private void updateFamilyInformation() {
        String family = String.valueOf(familyBox.getSelectedItem());
        familyAccent.setBackground(FAMILY_COLORS.getOrDefault(family, PRIMARY));
        DeliveryPlatform<?> platform = selectedPlatform();
        capacityHint.setText("Selected drone capacity: " + platform.maximumWeightKg() + " kg");
        networkValue.setText(family);
        modeValue.setText(String.valueOf(modeBox.getSelectedItem()));
        droneValue.setText(platform.droneIdentifier());
        capacityValue.setText(platform.maximumWeightKg() + " kg");
    }

    private void styleStatus(String text, Color color) {
        statusValue.setText(text);
        statusValue.setForeground(Color.WHITE);
        statusValue.setBackground(color);
        statusValue.setOpaque(true);
        statusValue.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }

    private void resetValidation() {
        errorLabel.setText(" ");
        Border normalBorder = UIManager.getBorder("TextField.border");
        orderIdField.setBorder(normalBorder);
        weightField.setBorder(normalBorder);
        distanceField.setBorder(normalBorder);
    }

    private void showInputError(String message, JTextField field) {
        errorLabel.setText(message);
        styleStatus("Input error", ERROR);
        if (field != null) {
            field.setBorder(BorderFactory.createLineBorder(ERROR, 2, true));
            field.requestFocusInWindow();
        }
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        if (darkMode) {
            FlatDarkLaf.setup();
            themeButton.setText("Light mode");
        } else {
            FlatLightLaf.setup();
            themeButton.setText("Dark mode");
        }
        SwingUtilities.updateComponentTreeUI(this);
        updateFamilyInformation();
    }

    @FunctionalInterface
    private interface PlatformOperation {
        String apply(DeliveryPlatform<?> platform, DeliveryOrder order);
    }
}
