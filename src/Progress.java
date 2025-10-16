import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Progress extends JFrame {
    private Timer timer;
    private double angle = 0;
    private final Register registerPage;

    public Progress(Register registerPage) {
        this.registerPage = registerPage;
        setupFrame();
        showProgressBarScreen();
    }

    private void setupFrame() {
        setTitle("Uzima Borehole Drilling System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void showProgressBarScreen() {
        JPanel progressPanel = new JPanel() {
            Image backgroundImage;
            {
                URL imageUrl = getClass().getResource("/Resource/Progress.jpeg");
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (backgroundImage != null) {
                    g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setColor(new Color(230, 240, 255));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                g2.setColor(Color.WHITE);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2 + 30;
                int radius = 60;
                int numDots = 12;

                for (int i = 0; i < numDots; i++) {
                    double currentAngle = angle + (i * 2 * Math.PI / numDots);
                    int x = (int) (centerX + Math.cos(currentAngle) * radius);
                    int y = (int) (centerY + Math.sin(currentAngle) * radius);
                    int size = (int) (8 + 4 * Math.sin(currentAngle + angle));
                    g2.fillOval(x - size / 2, y - size / 2, size, size);
                }

                g2.dispose();
            }
        };

        progressPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Uzima Borehole Drilling System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0x003366));
        progressPanel.add(titleLabel, BorderLayout.CENTER);

        setContentPane(progressPanel);
        setVisible(true);

        startRotatingAnimation();
    }

    private void startRotatingAnimation() {
        timer = new Timer(70, e -> {
            angle += Math.PI / 30;
            repaint();
        });
        timer.start();

        new Timer(3500, e -> {
            timer.stop();
            showRegisterPage();
        }).start();
    }

    private void showRegisterPage() {
        SwingUtilities.invokeLater(() -> {
            registerPage.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Register register = new Register();
            new Progress(register);
        });
    }
}
