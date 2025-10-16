import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {
    public Client() {
        setTitle("Uzima Borehole - Client");
        setSize(650, 650);
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
            new ClientDashboard().setVisible(true);
            Client.this.dispose();
        });

        menuItemSignOut.addActionListener(e -> {
            new UserLogin().setVisible(true);
            Client.this.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        JLabel titleLabel = new JLabel("Client Details");
        titleLabel.setBounds(250, 20, 300, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        // Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(180, 80, 150, 25);
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
        JTextField nameField = new JTextField(30);
        nameField.setBounds(180, 110, 300, 25);
        nameField.setBackground(new Color(0x47E1E8));
        panel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(180, 140, 150, 25);
        phoneLabel.setForeground(Color.WHITE);
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField(20);
        phoneField.setBounds(180, 170, 300, 25);
        phoneField.setBackground(new Color(0x47E1E8));
        panel.add(phoneField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(180, 200, 150, 25);
        addressLabel.setForeground(Color.WHITE);
        panel.add(addressLabel);
        JTextField addressField = new JTextField(20);
        addressField.setBounds(180, 230, 300, 25);
        addressField.setBackground(new Color(0x47E1E8));
        panel.add(addressField);

        JLabel locationLabel = new JLabel("Borehole Location:");
        locationLabel.setBounds(180, 260, 150, 25);
        locationLabel.setForeground(Color.WHITE);
        panel.add(locationLabel);
        JTextField locationField = new JTextField(30);
        locationField.setBounds(180, 290, 300, 25);
        locationField.setBackground(new Color(0x47E1E8));
        panel.add(locationField);

        JLabel clientCategoryLabel = new JLabel("Client Category:");
        clientCategoryLabel.setBounds(180, 320, 150, 25);
        clientCategoryLabel.setForeground(Color.WHITE);
        panel.add(clientCategoryLabel);
        String[] clientCategoryOptions = {"Industrial","Commercial", "Domestic"};
        JComboBox<String> clientCategoryCombo = new JComboBox<>(clientCategoryOptions);
        clientCategoryCombo.setBounds(180, 350, 300, 25);
        clientCategoryCombo.setForeground(Color.BLACK);
        clientCategoryCombo.setBackground(Color.WHITE);
        panel.add(clientCategoryCombo);

        JLabel surveyFeesLabel = new JLabel("Survey Fees:");
        surveyFeesLabel.setBounds(180, 380, 150, 25);
        surveyFeesLabel.setForeground(Color.WHITE);
        panel.add(surveyFeesLabel);
        JTextField surveyFeesField = new JTextField(30);
        surveyFeesField.setBounds(180, 410, 300, 25);
        surveyFeesField.setForeground(Color.BLACK);
        surveyFeesField.setBackground(Color.WHITE);
        panel.add(surveyFeesField);

        JLabel localAuthorityFeesLabel = new JLabel("Local Authority Fees:");
        localAuthorityFeesLabel.setBounds(180, 440, 150, 25);
        localAuthorityFeesLabel.setForeground(Color.WHITE);
        panel.add(localAuthorityFeesLabel);
        JTextField localAuthorityFeesField = new JTextField(30);
        localAuthorityFeesField.setBounds(180, 470, 300, 25);
        localAuthorityFeesField.setForeground(Color.BLACK);
        localAuthorityFeesField.setBackground(Color.WHITE);
        panel.add(localAuthorityFeesField);

        JButton btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(230, 520, 150, 35);
        btnSubmit.setBackground(new Color(0x47E1E8));
        panel.add(btnSubmit);

        clientCategoryCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClientCategory = clientCategoryCombo.getSelectedItem().toString();

                if(selectedClientCategory.equals("Industrial")){
                    surveyFeesField.setText("KES 20,000");
                    localAuthorityFeesField.setText("KES 50,000");
                }else if(selectedClientCategory.equals("Commercial")){
                    surveyFeesField.setText("KES 15,000");
                    localAuthorityFeesField.setText("KES 30,000");
                } else if(selectedClientCategory.equals("Domestic")){
                    surveyFeesField.setText("KES 7,000");
                    localAuthorityFeesField.setText("KES 10,000");
                }
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                String boreholeLocation = locationField.getText().trim();
                String clientCategory = clientCategoryCombo.getSelectedItem().toString();
                String surveyFee = surveyFeesField.getText().trim();
                String localAuthorityFee = localAuthorityFeesField.getText().trim();

                // Phone validation
                String validPhone = "^(?:\\+254(7|1)\\d{7}|0(7|1)\\d{7})$";

                if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || boreholeLocation.isEmpty() || clientCategory.isEmpty() || surveyFee.isEmpty() || localAuthorityFee.isEmpty()) {
                    JOptionPane.showMessageDialog(Client.this, "Please fill all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }else {
                    if (!phone.matches(validPhone)) {
                        JOptionPane.showMessageDialog(Client.this, "Please enter a valid Kenyan phone number (e.g. 0712345678 or +254712345678).", "Validation", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try {
                        // Check if the username or email already exists
                        if (UzimaDatabase.clientExists(name, phone)) {
                            JOptionPane.showMessageDialog(Client.this, "Client or phone already exists. Please use a different one.");
                        } else {
                            // Insert the user into the database
                            UzimaDatabase.insertClient(name, phone, address, boreholeLocation,clientCategory, surveyFee, localAuthorityFee);
                            JOptionPane.showMessageDialog(Client.this, "Client Details successful!");

                            // Back to Dashboard
                            ClientDashboard clientDashBoard = new ClientDashboard();
                            clientDashBoard.setVisible(true);
                            Client.this.dispose();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Client.this, "An error occurred while submitting.");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }
}
