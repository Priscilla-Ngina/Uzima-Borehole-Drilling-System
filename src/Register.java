import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Instant;

public class Register extends JFrame {
    private JComboBox<String> roleSelector;
    private JTextField usernameField, emailField, uniqueKeyField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton btnRegister;
    private JLabel loginLabel;

    public Register() {
        setTitle("Uzima Registration");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        // Title
        JLabel titleLabel = new JLabel("Register to Uzima Borehole Drilling System");
        titleLabel.setBounds(120, 20, 400, 25);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel);

        // Role Selector
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 60, 100, 25);
        roleLabel.setForeground(Color.BLACK);
        panel.add(roleLabel);

        roleSelector = new JComboBox<>(new String[]{"User", "Admin"});
        roleSelector.setBounds(180, 60, 200, 25);
        roleSelector.setBackground(new Color(0x47E1E8));
        panel.add(roleSelector);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 100, 150, 25);
        panel.add(usernameLabel);
        usernameField = new JTextField(15);
        usernameField.setBounds(180, 100, 200, 25);
        usernameField.setBackground(new Color(0x47E1E8));
        panel.add(usernameField);

        // Email (for user only)
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 140, 150, 25);
        panel.add(emailLabel);
        emailField = new JTextField(15);
        emailField.setBounds(180, 140, 200, 25);
        emailField.setBackground(new Color(0x47E1E8));
        panel.add(emailField);

        // Unique Key (for admin only)
        JLabel uniqueKeyLabel = new JLabel("Unique Key:");
        uniqueKeyLabel.setBounds(20, 140, 150, 25);
        uniqueKeyLabel.setVisible(false);
        panel.add(uniqueKeyLabel);

        uniqueKeyField = new JTextField(15);
        uniqueKeyField.setBounds(180, 140, 200, 25);
        uniqueKeyField.setBackground(new Color(0x47E1E8));
        uniqueKeyField.setEditable(false);
        uniqueKeyField.setVisible(false);
        panel.add(uniqueKeyField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 180, 150, 25);
        panel.add(passwordLabel);
        passwordField = new JPasswordField(15);
        passwordField.setBounds(180, 180, 200, 25);
        passwordField.setBackground(new Color(0x47E1E8));
        panel.add(passwordField);

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(20, 220, 150, 25);
        panel.add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBounds(180, 220, 200, 25);
        confirmPasswordField.setBackground(new Color(0x47E1E8));
        panel.add(confirmPasswordField);

        // Register button
        btnRegister = new JButton("REGISTER");
        btnRegister.setBounds(220, 260, 110, 25);
        btnRegister.setBackground(new Color(0x47E1E8));
        panel.add(btnRegister);

        // Login link
        loginLabel = new JLabel("Already have an account? Sign In");
        loginLabel.setBounds(170, 300, 250, 25);
        panel.add(loginLabel);

        // Add listeners
        roleSelector.addActionListener(e -> toggleRoleFields(emailLabel, uniqueKeyLabel));

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Login().setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setText("<html><u>Already have an account? Sign In</u></html>");
                loginLabel.setForeground(new Color(0x00FBF3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setText("Already have an account? Sign In");
                loginLabel.setForeground(Color.BLACK);
            }
        });


        btnRegister.addActionListener(e -> handleRegistration());

        // Initialize view
        toggleRoleFields(emailLabel, uniqueKeyLabel);
        setVisible(true);
    }

    private void toggleRoleFields(JLabel emailLabel, JLabel uniqueKeyLabel) {
        boolean isAdmin = roleSelector.getSelectedItem().equals("Admin");

        emailLabel.setVisible(!isAdmin);
        emailField.setVisible(!isAdmin);

        uniqueKeyLabel.setVisible(isAdmin);
        uniqueKeyField.setVisible(isAdmin);

        if (isAdmin) {
            String generatedKey = "" + Instant.now().getEpochSecond();
            uniqueKeyField.setText(generatedKey);
//            JOptionPane.showMessageDialog(this, "Your Admin Unique Key: " + generatedKey);
        }
    }

    private void handleRegistration() {
        String role = (String) roleSelector.getSelectedItem();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (role.equals("Admin")) {
            String uniqueKey = uniqueKeyField.getText();
            if (username.isEmpty() || uniqueKey.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }
            try {
                UzimaDatabase.insertAdmin(username, uniqueKey, password);
                JOptionPane.showMessageDialog(this, "Admin Registration Successful!");
                new AdminLogin().setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering admin.");
            }

        } else {
            String email = emailField.getText().trim();
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                return;
            }
            if (!email.matches("^[\\w-\\.]+@gmail\\.com$")) {
                JOptionPane.showMessageDialog(this, "Enter a valid Gmail address.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }
            try {
                if (UzimaDatabase.userExists(username, email)) {
                    JOptionPane.showMessageDialog(this, "Username or email already exists.");
                } else {
                    UzimaDatabase.insertUser(username, email, password);
                    JOptionPane.showMessageDialog(this, "User Registration Successful!");
                    new UserLogin().setVisible(true);
                    dispose();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering user.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Register();
            }
        });    }
}
