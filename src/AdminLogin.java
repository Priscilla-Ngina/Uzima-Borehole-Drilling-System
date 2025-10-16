import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminLogin extends JFrame {
    public AdminLogin() {
        setTitle("Uzima Borehole Drilling System");
        setSize(500,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem menuItemAdmin = new JMenuItem("User");
        menu.add(menuItemAdmin);

        menuItemAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserLogin().setVisible(true);
                AdminLogin.this.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        JLabel uniqueKeyLabel = new JLabel("Unique Key:");
        uniqueKeyLabel.setBounds(10, 120, 200, 25);
        uniqueKeyLabel.setForeground(Color.BLACK);
        panel.add(uniqueKeyLabel);
        JTextField uniqueKeyField = new JTextField(15);
        uniqueKeyField.setBounds(190, 120, 200, 25);
        uniqueKeyField.setForeground(Color.BLACK);
        uniqueKeyField.setBackground(new Color(0x47E1E8));
        panel.add(uniqueKeyField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 160, 150, 25);
        passwordLabel.setForeground(Color.BLACK);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBounds(190, 160, 200, 25);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(new Color(0x47E1E8));
        panel.add(passwordField);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(230, 200, 100, 30);
        btnLogin.setBackground(new Color(0x47E1E8));
        panel.add(btnLogin);

        JLabel registerLabel = new JLabel("No account? Sign up");
        registerLabel.setBounds(210, 260, 230, 25);
        registerLabel.setForeground(Color.BLACK);
        panel.add(registerLabel);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminRegister().setVisible(true);
                AdminLogin.this.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><u>No account? Sign up</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("No account? Sign up");
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUniqueKey = uniqueKeyField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (enteredUniqueKey.isEmpty() || enteredPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                } else {
                    boolean isValidAdmin= UzimaDatabase.checkAdminCredentials(enteredUniqueKey, enteredPassword);
                    if (isValidAdmin) {
                        JOptionPane.showMessageDialog(AdminLogin.this, "Admin Login successful!");
                        AdminPanel adminPanel = new AdminPanel();
                        adminPanel.setVisible(true);
                        AdminLogin.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(AdminLogin.this, "Incorrect unique key or password.");
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminLogin();
            }
        });
    }
}
