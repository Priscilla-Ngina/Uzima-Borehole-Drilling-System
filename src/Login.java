import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JComboBox<String> roleCombo;
    private JLabel usernameLabel, uniqueKeyLabel, passwordLabel;
    private JTextField usernameField, uniqueKeyField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JLabel registerLabel;

    public Login() {
        setTitle("Uzima Borehole Drilling System");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        // Role selector
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(40, 60, 100, 25);
        roleLabel.setForeground(Color.BLACK);
        panel.add(roleLabel);

        roleCombo = new JComboBox<>(new String[]{"User", "Admin"});
        roleCombo.setBounds(160, 60, 200, 25);
        panel.add(roleCombo);

        // Username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 100, 100, 25);
        usernameLabel.setForeground(Color.BLACK);
        panel.add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setBounds(160, 100, 200, 25);
        usernameField.setBackground(new Color(0x47E1E8));
        usernameField.setForeground(Color.BLACK);
        panel.add(usernameField);

        // Unique Key (for admin)
        uniqueKeyLabel = new JLabel("Unique Key:");
        uniqueKeyLabel.setBounds(40, 100, 100, 25);
        uniqueKeyLabel.setForeground(Color.BLACK);
        uniqueKeyLabel.setVisible(false);
        panel.add(uniqueKeyLabel);

        uniqueKeyField = new JTextField(15);
        uniqueKeyField.setBounds(160, 100, 200, 25);
        uniqueKeyField.setBackground(new Color(0x47E1E8));
        uniqueKeyField.setForeground(Color.BLACK);
        uniqueKeyField.setVisible(false);
        panel.add(uniqueKeyField);

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 140, 100, 25);
        passwordLabel.setForeground(Color.BLACK);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(160, 140, 200, 25);
        passwordField.setBackground(new Color(0x47E1E8));
        passwordField.setForeground(Color.BLACK);
        panel.add(passwordField);

        // Login button
        btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(200, 190, 100, 30);
        btnLogin.setBackground(new Color(0x47E1E8));
        panel.add(btnLogin);

        // Register label
        registerLabel = new JLabel("No account? Sign up");
        registerLabel.setBounds(190, 240, 200, 25);
        registerLabel.setForeground(Color.BLACK);
        panel.add(registerLabel);

        // Toggle role fields
        roleCombo.addActionListener(e -> toggleFields());

        // Login action
        btnLogin.addActionListener(e -> handleLogin());

        // Register label click
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register().setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><u>No account? Sign up</u></html>");
                registerLabel.setForeground(new Color(0x00FBF3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("No account? Sign up");
                registerLabel.setForeground(Color.BLACK);
            }
        });

        setVisible(true);
    }

    // Switch visible fields based on role
    private void toggleFields() {
        String selectedRole = (String) roleCombo.getSelectedItem();
        boolean isAdmin = selectedRole.equals("Admin");

        usernameLabel.setVisible(!isAdmin);
        usernameField.setVisible(!isAdmin);
        uniqueKeyLabel.setVisible(isAdmin);
        uniqueKeyField.setVisible(isAdmin);
    }

    // Handle login logic for both roles
    private void handleLogin() {
        String selectedRole = (String) roleCombo.getSelectedItem();
        String password = new String(passwordField.getPassword());

        if (selectedRole.equals("Admin")) {
            String uniqueKey = uniqueKeyField.getText();
            if (uniqueKey.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                return;
            }

            boolean isValidAdmin = UzimaDatabase.checkAdminCredentials(uniqueKey, password);
            if (isValidAdmin) {
                JOptionPane.showMessageDialog(this, "Admin Login successful!");
                new AdminPanel().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect unique key or password.");
            }

        } else { // User
            String username = usernameField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                return;
            }

            boolean isValidUser = UzimaDatabase.checkCredentials(username, password);
            if (isValidUser) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new ClientDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect username or password.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}

