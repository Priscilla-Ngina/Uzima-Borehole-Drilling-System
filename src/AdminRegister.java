import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;

public class AdminRegister extends JFrame {
    public AdminRegister() {
        setTitle("Uzima Admin Registration");
        setSize(550,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        JLabel adminLabel = new JLabel("Admin:");
        adminLabel.setBounds(10, 20, 150, 25);
        adminLabel.setForeground(Color.BLACK);
        panel.add(adminLabel);
        JTextField adminField = new JTextField(15);
        adminField.setBounds(180, 20, 200, 25);
        adminField.setForeground(Color.BLACK);
        adminField.setBackground(new Color(0x47E1E8));
        panel.add(adminField);

        JLabel uniqueKeyLabel = new JLabel("Unique Key:");
        uniqueKeyLabel.setBounds(10, 60, 200, 25);
        uniqueKeyLabel.setForeground(Color.BLACK);
        panel.add(uniqueKeyLabel);
        JTextField uniqueKeyField = new JTextField(15);
        uniqueKeyField.setBounds(180, 60, 200, 25);
        uniqueKeyField.setForeground(Color.BLACK);
        uniqueKeyField.setEnabled(false);
        uniqueKeyField.setBackground(new Color(0x47E1E8));
        panel.add(uniqueKeyField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 100, 150, 25);
        passwordLabel.setForeground(Color.BLACK);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBounds(180, 100, 200, 25);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(new Color(0x47E1E8));
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 140, 150, 25);
        confirmPasswordLabel.setForeground(Color.BLACK);
        panel.add(confirmPasswordLabel);
        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBounds(180, 140, 200, 25);
        confirmPasswordField.setForeground(Color.BLACK);
        confirmPasswordField.setBackground(new Color(0x47E1E8));
        panel.add(confirmPasswordField);

        JButton btnRegister = new JButton("REGISTER");
        btnRegister.setBounds(225, 190, 100, 25);
        btnRegister.setBackground(new Color(0x47E1E8));
        panel.add(btnRegister);

        JLabel loginLabel = new JLabel("Already have an account?");
        loginLabel.setBounds(190, 250, 230, 25);
        loginLabel.setForeground(Color.BLACK);
        panel.add(loginLabel);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminLogin().setVisible(true);
                AdminRegister.this.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setText("<html><u>Already have an account?</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setText("Already have an account?");
            }
        });

        // Authentication Number
        String generatedUniqueKey = "" + Instant.now().getEpochSecond();
        uniqueKeyField.setText(generatedUniqueKey);
        JOptionPane.showMessageDialog(AdminRegister.this,"Authentication Number: " + generatedUniqueKey);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admin = adminField.getText();
                String uniqueKey = uniqueKeyField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());


                if (uniqueKey.isEmpty() || admin.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminRegister.this, "Please fill all the fields.");
                } else {
                    if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(AdminRegister.this, "Counter-check the passwords.");
                        return;
                    }
                    try {
                        UzimaDatabase.insertAdmin(admin, uniqueKey, password);
                        JOptionPane.showMessageDialog(AdminRegister.this, "Admin Registration successful!");
                        AdminLogin loginAdmin = new AdminLogin();
                        loginAdmin.setVisible(true);
                        AdminRegister.this.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AdminRegister.this, "An error occurred while registering.");
                    }
                }
            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminRegister();
            }
        });
    }
}
