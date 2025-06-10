import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class FlightDetails extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    public FlightDetails() {
        setTitle("Flight Details");
        setSize(1100, 700);
        setLocationRelativeTo(null);
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

        // HEADER SECTION
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(900, 140));
        headerPanel.setPreferredSize(new Dimension(900, 140));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Header content
        JPanel headerContent = new JPanel();
        headerContent.setOpaque(false);
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.Y_AXIS));

        JLabel mainHeading = new JLabel("Flight Details");
        mainHeading.setFont(new Font("Montserrat", Font.BOLD, 32));
        mainHeading.setForeground(TEXT_COLOR);
        mainHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subHeading = new JLabel("<html><div style='text-align:center;'>View all available flights and their details</div></html>");
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subHeading.setForeground(new Color(80, 100, 120));
        subHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerContent.add(mainHeading);
        headerContent.add(Box.createVerticalStrut(12));
        headerContent.add(subHeading);

        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(headerContent);
        headerPanel.add(Box.createHorizontalGlue());

        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(headerPanel);
        backgroundPanel.add(Box.createVerticalStrut(20));

        // GLASSMORPHIC CARD
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
                g2d.setColor(new Color(0,0,0,18));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setMaximumSize(new Dimension(900, 500));
        cardPanel.setPreferredSize(new Dimension(900, 500));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        backgroundPanel.add(cardPanel);
        backgroundPanel.add(Box.createVerticalGlue());

        // TABLE MODEL & TABLE
        String[] columnNames = {"Flight Code", "Flight Name", "Source", "Destination"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? new Color(245, 249, 255) : Color.WHITE);
                else
                    c.setBackground(new Color(214, 234, 248));
                return c;
            }
        };
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Montserrat", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(214, 234, 248));
        table.setSelectionForeground(TEXT_COLOR);
        table.setBorder(null);

        // Custom table header renderer
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(41, 128, 185, 200));
                label.setForeground(Color.WHITE);
                label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        cardPanel.add(scrollPane);

        // BACK BUTTON
        JButton backButton = new JButton("Back to Home") {
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
        backButton.setFont(new Font("Montserrat", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(PRIMARY_COLOR);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setMaximumSize(new Dimension(200, 45));
        backButton.setPreferredSize(new Dimension(200, 45));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(SECONDARY_COLOR);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(PRIMARY_COLOR);
            }
        });
        backButton.addActionListener(e -> {
            new Home();
            setVisible(false);
        });
        cardPanel.add(Box.createVerticalStrut(18));
        cardPanel.add(backButton);

        // LOAD FLIGHT DATA
        try {
            Conn c = new Conn();
            String query = "SELECT f_code, f_name, source, destination FROM flight";
            ResultSet rs = c.s.executeQuery(query);
            while(rs.next()) {
                try {
                    String[] row = {
                        rs.getString("f_code"),
                        rs.getString("f_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                    };
                    model.addRow(row);
                } catch (SQLException ex) {
                    System.err.println("Error processing row: " + ex.getMessage());
                    continue;
                }
            }
            rs.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading flight details. Please ensure the database is properly set up.\nError: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }

        // Create scroll pane for the main content
        JScrollPane mainScrollPane = new JScrollPane(backgroundPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.setBorder(null);
        mainScrollPane.setOpaque(false);
        mainScrollPane.getViewport().setOpaque(false);
        add(mainScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
} 