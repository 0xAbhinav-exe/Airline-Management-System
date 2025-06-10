import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;

public class Home extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    public Home() {
        setTitle("Galgotias Airways");
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
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 205, 255), w, h, new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        // HEADER (modern, glassmorphic, logo + text)
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                // Glass effect
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

        // Centered header content
        headerPanel.add(Box.createHorizontalGlue());
        JPanel headerContent = new JPanel();
        headerContent.setOpaque(false);
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.X_AXIS));
        // Logo with drop shadow
        try {
            ImageIcon logoIcon = new ImageIcon("Icons/galgotiasairways.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0,0,0,40));
                    g2.fillOval(8, 8, getWidth()-16, getHeight()-16);
                    super.paintComponent(g);
                    g2.dispose();
                }
            };
            logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            headerContent.add(logoLabel);
            headerContent.add(Box.createHorizontalStrut(32));
        } catch (Exception e) {
            System.out.println("Could not load logo: " + e.getMessage());
        }
        // Heading and subheading
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        JLabel heading = new JLabel("GALGOTIAS AIRWAYS");
        heading.setFont(new Font("Montserrat", Font.BOLD, 38));
        heading.setForeground(TEXT_COLOR);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel subheading = new JLabel("Welcome to Your Flight Management System");
        subheading.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subheading.setForeground(new Color(80, 100, 120));
        subheading.setAlignmentX(Component.LEFT_ALIGNMENT);
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subheading);
        headerContent.add(headingPanel);
        headerPanel.add(headerContent);
        headerPanel.add(Box.createHorizontalGlue());

        // GLASSMORPHIC MAIN CARD
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
        mainCard.setMaximumSize(new Dimension(1100, 900));
        mainCard.setPreferredSize(new Dimension(900, 800));
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(mainCard);
        backgroundPanel.add(Box.createVerticalGlue());

        // WELCOME SECTION (glass, bold, modern)
        JLabel welcomeTitle = new JLabel("Welcome to Galgotias Airways");
        welcomeTitle.setFont(new Font("Montserrat", Font.BOLD, 36));
        welcomeTitle.setForeground(TEXT_COLOR);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcomeText = new JLabel("<html><div style='text-align:center; max-width:800px;'>Your trusted partner for safe and comfortable air travel. We offer a wide range of services to make your journey memorable and enjoyable. Experience the best in aviation with us.</div></html>");
        welcomeText.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        welcomeText.setForeground(new Color(120, 144, 156));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainCard.add(welcomeTitle);
        mainCard.add(Box.createVerticalStrut(16));
        mainCard.add(welcomeText);
        mainCard.add(Box.createVerticalStrut(18));

        // AIRWAYS ELEMENT (modern, centered)
        JPanel airwaysPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int y = getHeight() / 2;
                // Draw curved path
                g2d.setColor(new Color(41, 128, 185, 120));
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{16, 16}, 0));
                g2d.drawArc(40, y-18, w-80, 36, 0, 180);
                // Draw airplane icon (simple triangle)
                int[] xPoints = {w-60, w-40, w-50};
                int[] yPoints = {y-10, y, y+10};
                g2d.setColor(new Color(231, 76, 60));
                g2d.fillPolygon(xPoints, yPoints, 3);
            }
        };
        airwaysPanel.setPreferredSize(new Dimension(0, 40));
        airwaysPanel.setOpaque(false);
        mainCard.add(airwaysPanel);
        mainCard.add(Box.createVerticalStrut(18));

        // GRID PANEL (glassmorphic/neumorphic cards)
        JPanel gridPanel = new JPanel();
        gridPanel.setOpaque(false);
        gridPanel.setLayout(new GridLayout(1, 2, 40, 0));
        gridPanel.setMaximumSize(new Dimension(1000, 400));
        gridPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainCard.add(gridPanel);
        mainCard.add(Box.createVerticalGlue());

        String[][] sections = {
            {"Flight Management", "View Flight Details", "Add Customer", "Book Flight"},
            {"Journey Information", "View Journey Details", "Cancel Ticket", "Boarding Pass"}
        };
        for (int i = 0; i < sections.length; i++) {
            JPanel sectionPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Glassmorphic/neumorphic effect
                    g2d.setColor(new Color(255,255,255,200));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
                    g2d.setColor(new Color(0,0,0,18));
                    g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, CORNER_RADIUS, CORNER_RADIUS);
                }
            };
            sectionPanel.setOpaque(false);
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
            sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel titleLabel = new JLabel(sections[i][0]);
            titleLabel.setFont(new Font("Montserrat", Font.BOLD, 26));
            titleLabel.setForeground(PRIMARY_COLOR);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sectionPanel.add(titleLabel);
            sectionPanel.add(Box.createVerticalStrut(18));
            for (int j = 1; j < sections[i].length; j++) {
                JButton button = createModernButton(sections[i][j]);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                sectionPanel.add(button);
                sectionPanel.add(Box.createVerticalStrut(14));
            }
            gridPanel.add(sectionPanel);
        }

        // SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        setJMenuBar(createMenuBar());
        add(scrollPane, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Glassmorphic/neumorphic pill
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(260, 45));
        button.setPreferredSize(new Dimension(260, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Animated hover effect
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
        button.addActionListener(e -> {
            switch (text) {
                case "View Flight Details": new FlightDetails(); break;
                case "Add Customer": new AddCustomer(); break;
                case "Book Flight": new BookFlight(); break;
                case "View Journey Details": new JourneyDetails(); break;
                case "Cancel Ticket": new CancelFlight(); break;
                case "Boarding Pass": new BoardingPass(); break;
            }
            setVisible(false);
        });
        return button;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(PRIMARY_COLOR);
        menubar.setPreferredSize(new Dimension(1200, 40));
        JMenu details = createStyledMenu("Details");
        menubar.add(details);
        JMenuItem[] menuItems = {
            createStyledMenuItem("Flight Details"),
            createStyledMenuItem("Add Customer Details"),
            createStyledMenuItem("Book Flight"),
            createStyledMenuItem("Journey Details"),
            createStyledMenuItem("Cancel Ticket")
        };
        for (JMenuItem item : menuItems) details.add(item);
        menuItems[0].addActionListener(e -> { new FlightDetails(); setVisible(false); });
        menuItems[1].addActionListener(e -> { new AddCustomer(); setVisible(false); });
        menuItems[2].addActionListener(e -> { new BookFlight(); setVisible(false); });
        menuItems[3].addActionListener(e -> { new JourneyDetails(); setVisible(false); });
        menuItems[4].addActionListener(e -> { new CancelFlight(); setVisible(false); });
        JMenu ticket = createStyledMenu("Ticket");
        menubar.add(ticket);
        JMenuItem boardingPass = createStyledMenuItem("Boarding Pass");
        ticket.add(boardingPass);
        boardingPass.addActionListener(e -> { new BoardingPass(); setVisible(false); });
        return menubar;
    }

    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return menu;
    }

    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return item;
    }
}
