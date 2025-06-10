import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class BoardingPass extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    private ArrayList<Cloud> clouds;
    private Timer cloudTimer;

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

    JTextField tfid;
    JButton submit, back;

    public BoardingPass() {
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

        setTitle("Boarding Pass");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setPreferredSize(new Dimension(0, 100));
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        backgroundPanel.add(Box.createVerticalStrut(18));
        backgroundPanel.add(headerPanel);

        // Header content
        JLabel heading = new JLabel("BOARDING PASS");
        heading.setFont(new Font("Montserrat", Font.BOLD, 36));
        heading.setForeground(TEXT_COLOR);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(heading);
        headerPanel.add(Box.createHorizontalGlue());

        // MAIN CARD
        JPanel mainCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
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
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("Enter Booking ID:");
        idLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        idLabel.setForeground(TEXT_COLOR);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(idLabel);
        inputPanel.add(Box.createVerticalStrut(20));

        tfid = new JTextField();
        tfid.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tfid.setMaximumSize(new Dimension(300, 40));
        tfid.setPreferredSize(new Dimension(300, 40));
        tfid.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(tfid);
        inputPanel.add(Box.createVerticalStrut(30));

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        submit = createModernButton("Submit");
        back = createModernButton("Back");

        buttonPanel.add(submit);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(back);

        inputPanel.add(buttonPanel);
        mainCard.add(inputPanel);

        // Add action listeners
        submit.addActionListener(e -> {
            try {
                Conn c = new Conn();
                // First check if table exists
                String checkTableQuery = "SHOW TABLES LIKE 'customer'";
                ResultSet tableCheck = c.s.executeQuery(checkTableQuery);
                
                if (!tableCheck.next()) {
                    // Table doesn't exist, create it
                    String createTableQuery = "CREATE TABLE customer (" +
                        "id VARCHAR(20) PRIMARY KEY, " +
                        "name VARCHAR(100), " +
                        "aadhar VARCHAR(12), " +
                        "gender VARCHAR(10), " +
                        "nationality VARCHAR(50), " +
                        "address VARCHAR(200), " +
                        "phone VARCHAR(15), " +
                        "email VARCHAR(100), " +
                        "source VARCHAR(50), " +
                        "destination VARCHAR(50), " +
                        "date DATE, " +
                        "price DOUBLE, " +
                        "pnr VARCHAR(20), " +
                        "ticket_number VARCHAR(20), " +
                        "seat_number VARCHAR(10))";
                    c.s.executeUpdate(createTableQuery);
                    JOptionPane.showMessageDialog(null, "No bookings found. Please book a flight first.");
                    return;
                }

                // Now proceed with the original query
                String query = "SELECT * FROM customer WHERE id = '" + tfid.getText() + "'";
                ResultSet rs = c.s.executeQuery(query);
                
                if (rs.next()) {
                    showBoardingPass(rs);
                } else {
                    JOptionPane.showMessageDialog(null, "No booking found with this ID");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> {
            new Home();
            setVisible(false);
        });

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
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 17f));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 16f));
            }
        });
        
        return button;
    }

    private void showBoardingPass(ResultSet rs) throws SQLException {
        JFrame boardingPass = new JFrame("Boarding Pass");
        boardingPass.setSize(800, 900);
        boardingPass.setLocationRelativeTo(null);

        JPanel passPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 205, 255), w, h, new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
        passPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Boarding Pass Card
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Airline Logo and Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel airlineLabel = new JLabel("✈️ AIRLINE");
        airlineLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
        airlineLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(airlineLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(new JLabel("BOARDING PASS"));
        cardPanel.add(headerPanel);
        cardPanel.add(Box.createVerticalStrut(20));

        // Add decorative line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        cardPanel.add(separator);
        cardPanel.add(Box.createVerticalStrut(20));

        // Flight Details Section
        JPanel flightDetailsPanel = new JPanel();
        flightDetailsPanel.setOpaque(false);
        flightDetailsPanel.setLayout(new BoxLayout(flightDetailsPanel, BoxLayout.X_AXIS));
        flightDetailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Departure
        JPanel departurePanel = createFlightInfoPanel("DEPARTURE", rs.getString("source"), "10:00 AM");
        // Flight Info
        JPanel flightInfoPanel = createFlightInfoPanel("FLIGHT", "FL-" + rs.getString("id").substring(0, 4), "Economy");
        // Arrival
        JPanel arrivalPanel = createFlightInfoPanel("ARRIVAL", rs.getString("destination"), "12:00 PM");

        flightDetailsPanel.add(departurePanel);
        flightDetailsPanel.add(Box.createHorizontalStrut(20));
        flightDetailsPanel.add(flightInfoPanel);
        flightDetailsPanel.add(Box.createHorizontalStrut(20));
        flightDetailsPanel.add(arrivalPanel);

        cardPanel.add(flightDetailsPanel);
        cardPanel.add(Box.createVerticalStrut(30));

        // Add decorative line
        cardPanel.add(separator);
        cardPanel.add(Box.createVerticalStrut(20));

        // Passenger Details Section
        JPanel passengerSection = new JPanel();
        passengerSection.setOpaque(false);
        passengerSection.setLayout(new BoxLayout(passengerSection, BoxLayout.Y_AXIS));
        passengerSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passengerHeader = new JLabel("PASSENGER INFORMATION");
        passengerHeader.setFont(new Font("Montserrat", Font.BOLD, 18));
        passengerHeader.setForeground(PRIMARY_COLOR);
        passengerHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        passengerSection.add(passengerHeader);
        passengerSection.add(Box.createVerticalStrut(15));

        // Passenger Details
        addDetail(passengerSection, "Name", rs.getString("name"));
        addDetail(passengerSection, "Booking ID", rs.getString("id"));
        addDetail(passengerSection, "Date", rs.getString("date"));
        addDetail(passengerSection, "Seat", "12A");
        addDetail(passengerSection, "Gate", "G" + (int)(Math.random() * 20 + 1));
        addDetail(passengerSection, "Status", "CONFIRMED");

        cardPanel.add(passengerSection);
        cardPanel.add(Box.createVerticalStrut(30));

        // Add decorative line
        cardPanel.add(separator);
        cardPanel.add(Box.createVerticalStrut(20));

        // Important Information
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoHeader = new JLabel("IMPORTANT INFORMATION");
        infoHeader.setFont(new Font("Montserrat", Font.BOLD, 16));
        infoHeader.setForeground(ACCENT_COLOR);
        infoHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(infoHeader);
        infoPanel.add(Box.createVerticalStrut(10));

        JLabel infoText = new JLabel("<html><center>Please arrive at the airport at least 2 hours before departure.<br>Have your ID and boarding pass ready for security check.</center></html>");
        infoText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoText.setForeground(TEXT_COLOR);
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(infoText);

        cardPanel.add(infoPanel);
        cardPanel.add(Box.createVerticalStrut(30));

        // Action Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton print = createModernButton("Print Pass");
        JButton email = createModernButton("Email Pass");
        email.setBackground(new Color(46, 204, 113));

        print.addActionListener(ev -> {
            JOptionPane.showMessageDialog(null, "Boarding Pass Printed");
            boardingPass.dispose();
        });

        email.addActionListener(ev -> {
            JOptionPane.showMessageDialog(null, "Boarding Pass sent to your email");
        });

        buttonPanel.add(print);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(email);

        cardPanel.add(buttonPanel);

        passPanel.add(cardPanel);
        boardingPass.add(passPanel);
        boardingPass.setVisible(true);
    }

    private JPanel createFlightInfoPanel(String title, String mainInfo, String subInfo) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        titleLabel.setForeground(new Color(100, 100, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mainLabel = new JLabel(mainInfo);
        mainLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        mainLabel.setForeground(TEXT_COLOR);
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel(subInfo);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(100, 100, 100));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(mainLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subLabel);

        return panel;
    }

    private void addDetail(JPanel panel, String label, String value) {
        JPanel detailPanel = new JPanel();
        detailPanel.setOpaque(false);
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelComp = new JLabel(label + ": ");
        labelComp.setFont(new Font("Montserrat", Font.BOLD, 18));
        labelComp.setForeground(TEXT_COLOR);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        valueComp.setForeground(new Color(80, 100, 120));

        detailPanel.add(labelComp);
        detailPanel.add(valueComp);
        panel.add(detailPanel);
        panel.add(Box.createVerticalStrut(15));
    }
}
