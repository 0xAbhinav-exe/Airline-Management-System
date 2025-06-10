import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import net.proteanit.sql.DbUtils;

public class flightInfo extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    public flightInfo() {
        setTitle("Flight Information - Galgotias Airways");
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
        JLabel titleLabel = new JLabel("Flight Information");
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
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        mainCard.setOpaque(false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(mainCard);

        // Table
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading flight information");
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(255, 255, 255, 180));
        mainCard.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = createModernButton("Back");
        backButton.addActionListener(e -> {
            new Home();
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(buttonPanel);
        backgroundPanel.add(Box.createVerticalGlue());

        setSize(1000, 600);
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

    public static void main(String[] args) {
        new flightInfo();
    }
}
