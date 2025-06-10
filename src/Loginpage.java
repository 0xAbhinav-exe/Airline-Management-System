import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Loginpage extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);

    JTextField tfusername;
    JPasswordField tfpassword;
    JButton submitButton, resetButton, closeButton;
    JLabel usernameLabel, passwordLabel, titleLabel, logoLabel;
    JPanel mainPanel, formPanel, buttonPanel, leftPanel, rightPanel;
    private BufferedImage backgroundImage;

    public Loginpage() {
        // Set up the frame
        setTitle("Galgotias Airways - Login");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with split layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Create left panel for logo and branding
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, w, h, TEXT_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Add logo
        try {
            ImageIcon logoIcon = new ImageIcon("Icons/galgotiasairways.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
            leftPanel.add(logoLabel);
        } catch (Exception e) {
            System.out.println("Could not load logo: " + e.getMessage());
        }

        // Add welcome text
        JLabel welcomeLabel = new JLabel("Welcome to");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(welcomeLabel);

        JLabel airlineLabel = new JLabel("Galgotias Airways");
        airlineLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        airlineLabel.setForeground(Color.WHITE);
        airlineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(airlineLabel);

        // Create right panel for login form
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        // Create form panel
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Add title
        titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(titleLabel, gbc);

        // Username components
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(TEXT_COLOR);
        tfusername = new JTextField(20);
        tfusername.setPreferredSize(new Dimension(300, 40));
        tfusername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfusername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(usernameLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(tfusername, gbc);

        // Password components
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);
        tfpassword = new JPasswordField(20);
        tfpassword.setPreferredSize(new Dimension(300, 40));
        tfpassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfpassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(passwordLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(tfpassword, gbc);

        // Buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        submitButton = createStyledButton("Login", PRIMARY_COLOR);
        resetButton = createStyledButton("Reset", SECONDARY_COLOR);
        closeButton = createStyledButton("Close", ACCENT_COLOR);

        submitButton.addActionListener(e -> handleEvent("Submit"));
        resetButton.addActionListener(e -> handleEvent("Reset"));
        closeButton.addActionListener(e -> handleEvent("Close"));

        // Add enter key listener for login
        tfpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleEvent("Submit");
                }
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(closeButton);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(buttonPanel, gbc);

        // Add form panel to right panel
        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    // Core Feature: Authentication Logic
    public void authenticateUser(String username, String password) {
        try {
            System.out.println("Attempting to connect to database...");
            Conn c = new Conn();
            System.out.println("Database connection successful");
            
            String query = "select * from login where username = '" + username + "' and password = '" + password + "'";
            System.out.println("Executing query: " + query);
            
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                System.out.println("Login successful, opening Home page...");
                new Home(); // Integration with Home module
                setVisible(false); // Closing Login window
            } else {
                System.out.println("Invalid credentials");
                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Event Handling: Button actions
    public void handleEvent(String action) {
        if ("Submit".equals(action)) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            authenticateUser(username, password);
        } else if ("Close".equals(action)) {
            System.exit(0);
        } else if ("Reset".equals(action)) {
            tfusername.setText("");
            tfpassword.setText("");
            tfusername.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Loginpage();
    }
}
