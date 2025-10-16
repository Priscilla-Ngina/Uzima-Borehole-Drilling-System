import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AdminPanel extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;

    public AdminPanel() {
        setTitle("Uzima Borehole Report");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === MENU BAR ===
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem menuItemGoBack = new JMenuItem("Back");
        JMenuItem menuItemSignOut = new JMenuItem("Logout");

        menu.add(menuItemGoBack);
        menu.add(menuItemSignOut);

        menuItemGoBack.addActionListener(e -> {
            new UserLogin().setVisible(true);
            dispose();
        });

        menuItemSignOut.addActionListener(e -> {
            new UserLogin().setVisible(true);
            dispose();
        });

        // === MAIN PANEL ===
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x0DBFAE));
        add(mainPanel);

        // === SIDEBAR ===
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(0x5DEADE));
        sidebar.setPreferredSize(new Dimension(180, getHeight()));
        mainPanel.add(sidebar, BorderLayout.WEST);

        // === CONTENT AREA ===
        contentPanel = new JPanel(new CardLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add all views (Cards)
        contentPanel.add(createClientsPanel(), "Clients");
        contentPanel.add(createPlumbingPanel(), "Plumbing");
        contentPanel.add(createTaxPanel(), "Tax");
        contentPanel.add(createFeesPanel(), "Fees");
        contentPanel.add(createRevenuePanel(), "Revenue");

        // === SIDEBAR LABELS ===
        addSidebarLabel(sidebar, "Clients");
        addSidebarLabel(sidebar, "Plumbing");
        addSidebarLabel(sidebar, "Tax");
        addSidebarLabel(sidebar, "Fees");
        addSidebarLabel(sidebar, "Revenue");

        setVisible(true);
    }

    // Helper: Add clickable label to sidebar
    private void addSidebarLabel(JPanel sidebar, String name) {
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(0x5DEADE));
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(0x4CD3C2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(0x5DEADE));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, name);
            }
        });

        sidebar.add(label);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // === PANELS ===

    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Client FullName", "Amount Paid"}, 0);
        table.setModel(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Object[] row : UzimaDatabase.getAllClients()) {
            model.addRow(row);
        }
        return panel;
    }

    private JPanel createPlumbingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Client FullName", "Plumbing Charges"}, 0);
        table.setModel(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Object[] row : UzimaDatabase.getAllPlumbing()) {
            model.addRow(row);
        }

        JLabel totalLabel = new JLabel("Total Plumbing Charges: " + UzimaDatabase.getTotalPlumbingCharges(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(totalLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTaxPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Client FullName", "Tax"}, 0);
        table.setModel(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Object[] row : UzimaDatabase.getAllTaxes()) {
            model.addRow(row);
        }
        return panel;
    }

    private JPanel createFeesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Client FullName", "Survey Fee", "Local Authority Fee"}, 0);
        table.setModel(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Object[] row : UzimaDatabase.getAllFees()) {
            model.addRow(row);
        }

        JLabel totalLabel = new JLabel("Total Fees (Survey + Local Authority): " + UzimaDatabase.getTotalFees(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(totalLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Client FullName", "Tax Amount"}, 0);
        table.setModel(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Object[] row : UzimaDatabase.getAllRevenue()) {
            model.addRow(row);
        }
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminPanel();
            }
        });
    }
}
