import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Services extends JFrame {
    public Services() {
        setTitle("Uzima Borehole - Client");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x0DBFAE));
        add(panel);

        JLabel titleLabel = new JLabel("Drilling Services");
        titleLabel.setBounds(250, 20, 300, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        JLabel typeDrillingLabel = new JLabel("Type of Drilling:");
        typeDrillingLabel.setBounds(180, 80, 150, 25);
        typeDrillingLabel.setForeground(Color.WHITE);
        panel.add(typeDrillingLabel);
        String[] clientCategoryOptions = {"Symmetric Drilling","Core-Drilling", "Geo-Technical Drilling"};
        JComboBox<String> typeDrillingCombo = new JComboBox<>(clientCategoryOptions);
        typeDrillingCombo.setBounds(180, 110, 300, 25);
        typeDrillingCombo.setForeground(Color.BLACK);
        typeDrillingCombo.setBackground(Color.WHITE);
        panel.add(typeDrillingCombo);

        JLabel downPaymentLabel = new JLabel("Down Payment Fee:");
        downPaymentLabel.setBounds(180, 140, 150, 25);
        downPaymentLabel.setForeground(Color.WHITE);
        panel.add(downPaymentLabel);
        JTextField downPaymentField = new JTextField(30);
        downPaymentField.setBounds(180, 170, 300, 25);
        downPaymentField.setForeground(Color.BLACK);
        downPaymentField.setBackground(Color.WHITE);
        panel.add(downPaymentField);

        JLabel typePumpLabel = new JLabel("Type of Pump:");
        typePumpLabel.setBounds(180, 200, 150, 25);
        typePumpLabel.setForeground(Color.WHITE);
        panel.add(typePumpLabel);
        String[] typePumpOptions = {"Submersible electric pump","Solar pump", "Hand pump"};
        JComboBox<String> typePumpCombo = new JComboBox<>(typePumpOptions);
        typePumpCombo.setBounds(180, 230, 300, 25);
        typePumpCombo.setForeground(Color.BLACK);
        typePumpCombo.setBackground(Color.WHITE);
        panel.add(typePumpCombo);

        JLabel costLabel = new JLabel("Cost:");
        costLabel.setBounds(180, 260, 150, 25);
        costLabel.setForeground(Color.WHITE);
        panel.add(costLabel);
        JTextField costField = new JTextField(30);
        costField.setBounds(180, 290, 300, 25);
        costField.setForeground(Color.BLACK);
        costField.setBackground(Color.WHITE);
        panel.add(costField);


        typeDrillingCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTypeDrilling = typeDrillingCombo.getSelectedItem().toString();

                if(selectedTypeDrilling.equals("Symmetric Drilling")){
                    downPaymentField.setText("KES 130,000");
                }else if(selectedTypeDrilling.equals("Core-Drilling")){
                    downPaymentField.setText("KES 225,000");
                } else if(selectedTypeDrilling.equals("Geo-Technical Drilling")){
                    downPaymentField.setText("KES 335,000");
                }
            }
        });

        typePumpCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTypePump = typePumpCombo.getSelectedItem().toString();

                if(selectedTypePump.equals("Submersible electric pump")){
                    costField.setText("KES 90,000");
                }else if(selectedTypePump.equals("Solar pump")){
                    costField.setText("KES 65,000");
                } else if(selectedTypePump.equals("Hand pump")){
                    costField.setText("KES 30,000");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Services();
            }
        });
    }
}
