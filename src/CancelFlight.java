import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class CancelFlight extends JFrame implements ActionListener {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;
    private ArrayList<Cloud> clouds;
    private Timer cloudTimer;

    // UI Components
    private JTextField tfid;
    private JButton submit, back;

    // Cloud class for decoration
    private class Cloud {
        int x, y;
        int speed;
        int size;
        Color color;

        Cloud(int x, int y, int speed, int size) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = size;
            this.color = new Color(255, 255, 255, 180);
        }

        void move() {
            x += speed;
            if (x > getWidth()) {
                x = -size;
            }
        }

        void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillOval(x, y, size, size/2);
            g2d.fillOval(x + size/4, y - size/4, size/2, size/2);
            g2d.fillOval(x + size/2, y, size/2, size/2);
        }
    }

    public CancelFlight() {
        setTitle("Cancel Flight - Galgotias Airways");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize clouds
        clouds = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            clouds.add(new Cloud(
                rand.nextInt(800),
                rand.nextInt(200),
                rand.nextInt(2) + 1,
                rand.nextInt(100) + 100
            ));
        }

        // Cloud animation timer
        cloudTimer = new Timer(50, e -> repaint());
        cloudTimer.start();

        // GLASSMORPHIC BACKGROUND
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                
                // Draw gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 205, 255), w, h, new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Draw clouds
                for (Cloud cloud : clouds) {
                    cloud.draw(g2d);
                    cloud.move();
                }

                // Draw decorative elements
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillOval(w - 200, h - 200, 300, 300);
                g2d.fillOval(-100, h - 150, 250, 250);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        // HEADER
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                g2d.setColor(new Color(255,255,255,120));
                g2d.fillRoundRect(0, 0, w, h, CORNER_RADIUS, CORNER_RADIUS);
                g2d.setColor(new Color(0,0,0,30));
                g2d.fillRect(0, h-8, w, 8);
            }
        };
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        headerPanel.setPreferredSize(new Dimension(0, 140));
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        backgroundPanel.add(Box.createVerticalStrut(18));
        backgroundPanel.add(headerPanel);

        // Header content
        JLabel titleLabel = new JLabel("Cancel Flight");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 38));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalGlue());

        // MAIN CARD
        JPanel mainCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Enhanced glass effect
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
                
                // Add subtle shadow
                g2d.setColor(new Color(0,0,0,20));
                g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        mainCard.setOpaque(false);
        mainCard.setMaximumSize(new Dimension(600, 400));
        mainCard.setPreferredSize(new Dimension(500, 300));
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(mainCard);
        backgroundPanel.add(Box.createVerticalGlue());

        // ID Input Section
        JPanel inputPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Add decorative circle
                g2d.setColor(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 30));
                g2d.fillOval(-50, -50, 200, 200);
            }
        };
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);

        JLabel idLabel = new JLabel("Enter Booking ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        idLabel.setForeground(TEXT_COLOR);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(idLabel);
        inputPanel.add(Box.createVerticalStrut(20));

        tfid = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (!hasFocus() && getText().isEmpty()) {
                    g2d.setColor(new Color(150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    g2d.drawString("Enter your booking ID here...", 10, getHeight() - 10);
                }
            }
        };
        tfid.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tfid.setMaximumSize(new Dimension(300, 40));
        tfid.setPreferredSize(new Dimension(300, 40));
        tfid.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfid.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        inputPanel.add(tfid);
        inputPanel.add(Box.createVerticalStrut(30));

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        submit = createModernButton("Submit");
        back = createModernButton("Back");
        submit.addActionListener(this);
        back.addActionListener(this);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(submit);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(back);
        buttonPanel.add(Box.createHorizontalGlue());

        inputPanel.add(buttonPanel);
        mainCard.add(inputPanel);

        // SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setFont(new Font("Montserrat", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setMaximumSize(new Dimension(150, 45));
        button.setPreferredSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 17f));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 16f));
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            try {
                Conn c = new Conn();
                String query = "DELETE FROM customer WHERE id = '" + tfid.getText() + "'";
                int rowsAffected = c.s.executeUpdate(query);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Flight Cancelled Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "No booking found with this ID");
                }
                new Home();
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            new Home();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new CancelFlight();
    }
}
