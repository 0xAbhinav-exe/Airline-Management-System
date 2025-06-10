import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;

public class JourneyDetails extends JFrame implements ActionListener {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 245);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    // Core components
    JTable table;
    JTextField pnr;
    JButton show;
    JButton back;
    
    public JourneyDetails() {
        setTitle("Journey Details - Galgotias Airways");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Glassmorphic background with clouds
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                
                // Sky gradient
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 205, 255), w, h, new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                
                // Draw clouds
                g2d.setColor(new Color(255, 255, 255, 180));
                drawCloud(g2d, w * 0.2, h * 0.1, 60);
                drawCloud(g2d, w * 0.7, h * 0.15, 80);
                drawCloud(g2d, w * 0.4, h * 0.2, 70);
                drawCloud(g2d, w * 0.8, h * 0.25, 50);
                
                // Draw airplane path
                g2d.setColor(new Color(41, 128, 185, 120));
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{16, 16}, 0));
                g2d.drawArc(w/4, h/4, w/2, h/2, 0, 180);
                
                // Draw small airplane
                int[] xPoints = {w-100, w-80, w-90};
                int[] yPoints = {h/2-10, h/2, h/2+10};
                g2d.setColor(PRIMARY_COLOR);
                g2d.fillPolygon(xPoints, yPoints, 3);
            }
            
            private void drawCloud(Graphics2D g2d, double x, double y, int size) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (int i = 0; i < 3; i++) {
                    g2d.fillOval((int)x + i*size/2, (int)y, size, size/2);
                }
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        // Title Panel
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,120));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel titleLabel = new JLabel("Journey Details");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Enter your PNR number to view journey details");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(80, 100, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(titlePanel);

        // PNR Details Section
        JPanel pnrPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        pnrPanel.setOpaque(false);
        pnrPanel.setLayout(new BoxLayout(pnrPanel, BoxLayout.X_AXIS));
        pnrPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        pnrPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        pnrPanel.setPreferredSize(new Dimension(0, 80));

        // PNR Label
        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Montserrat", Font.BOLD, 20));
        lblpnr.setForeground(TEXT_COLOR);
        pnrPanel.add(lblpnr);
        pnrPanel.add(Box.createHorizontalStrut(20));

        // PNR TextField
        pnr = new JTextField();
        pnr.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnr.setMaximumSize(new Dimension(200, 40));
        pnr.setPreferredSize(new Dimension(200, 40));
        pnr.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        pnrPanel.add(pnr);
        pnrPanel.add(Box.createHorizontalStrut(20));

        // Show Button
        show = createModernButton("Show Details");
        show.addActionListener(this);
        pnrPanel.add(show);
        pnrPanel.add(Box.createHorizontalStrut(20));

        // Back Button
        back = createModernButton("← Back");
        back.setBackground(PRIMARY_COLOR);
        back.addActionListener(this);
        pnrPanel.add(back);
        pnrPanel.add(Box.createHorizontalGlue());

        backgroundPanel.add(pnrPanel);
        backgroundPanel.add(Box.createVerticalStrut(20));

        // Table Panel
        JPanel tablePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Table
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Montserrat", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBorder(BorderFactory.createEmptyBorder());
        jsp.setOpaque(false);
        jsp.getViewport().setOpaque(false);
        tablePanel.add(jsp, BorderLayout.CENTER);

        backgroundPanel.add(tablePanel);
        backgroundPanel.add(Box.createVerticalGlue());

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(backgroundPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

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
        button.setMaximumSize(new Dimension(200, 40));
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            new Home().setVisible(true);
            this.dispose();
            return;
        }
        
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from reservation where PNR = '"+pnr.getText()+"'");
            
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
