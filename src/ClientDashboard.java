// ClientDashboard.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientDashboard extends JFrame {
    public ClientDashboard() {
        setTitle("Uzima - Client Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create menu
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        // Create menu items
        JMenuItem menuItemGoBack = new JMenuItem("Back");
        JMenuItem menuItemSignOut = new JMenuItem("Logout");

        // Add menu items to menu
        menu.add(menuItemGoBack);
        menu.add(menuItemSignOut);

        menuItemGoBack.addActionListener(e -> {
            new Login().setVisible(true);
            ClientDashboard.this.dispose();
        });

        menuItemSignOut.addActionListener(e -> {
            new Login().setVisible(true);
            ClientDashboard.this.dispose();
        });

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x0DBFAE));
        add(mainPanel);

//        JLabel titleLabel = new JLabel("ClientDashBoard");
//        titleLabel.setBounds(10, 20, 300, 30);
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        mainPanel.add(titleLabel);

        // Sidebar panel for labels
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(0x5DEADE));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Create labels for each "button" with spacing
        sidebar.add(createSidebarLabel("Client"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(createSidebarLabel("Services"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add listeners to labels
        sidebar.getComponent(0).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client clients = new Client();
                clients.setVisible(true);
                ClientDashboard.this.dispose();
            }
        });

        sidebar.getComponent(2).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Services services = new Services();
                services.setVisible(true);
                ClientDashboard.this.dispose();
            }
        });
        setVisible(true);
    }

    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 40));
        label.setOpaque(true);
        label.setBackground(new Color(0x5DEADE));
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private abstract static class LabelMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            ((JLabel) e.getSource()).setBackground(new Color(0x5DEADE));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((JLabel) e.getSource()).setBackground(new Color(0x5DEADE));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientDashboard();
            }
        });
    }
}
