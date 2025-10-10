// ClientDashboard.java
import javax.swing.*;

public class ClientDashboard extends JFrame {
    public ClientDashboard() {
        setTitle("Uzima - Dashboard");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Welcome to the Uzima Dashboard (placeholder)");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
