import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class AddCustomer extends JFrame implements ActionListener {
    
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;
    
    // Input validation patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern AADHAR_PATTERN = Pattern.compile("^\\d{12}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,50}$");
    
    private JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    private JRadioButton rbmale, rbfemale;
    private JButton back, save;
    private JLabel statusLabel;
    
    public AddCustomer() {
        setTitle("Add Customer");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // HEADER SECTION (similar to Home.java's header)
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255,255,255,180)); // Glassmorphic effect
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(850, 140));
        headerPanel.setPreferredSize(new Dimension(850, 140));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Content within the header (logo and text)
        JPanel headerContent = new JPanel();
        headerContent.setOpaque(false);
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.Y_AXIS));

        JLabel mainHeading = new JLabel("Add Customer Details");
        mainHeading.setFont(new Font("Montserrat", Font.BOLD, 32));
        mainHeading.setForeground(TEXT_COLOR);
        mainHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subHeading = new JLabel("<html><div style='text-align:center;'>Ensure all required fields are accurately filled.</div></html>");
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subHeading.setForeground(new Color(80, 100, 120));
        subHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerContent.add(mainHeading);
        headerContent.add(Box.createVerticalStrut(12));
        headerContent.add(subHeading);

        headerPanel.add(Box.createHorizontalGlue()); // Center header content
        headerPanel.add(headerContent);
        headerPanel.add(Box.createHorizontalGlue());

        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(headerPanel);
        backgroundPanel.add(Box.createVerticalStrut(20));

        // GLASSMORPHIC CARD (Main form card)
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
        cardPanel.setMaximumSize(new Dimension(800, 500));
        cardPanel.setPreferredSize(new Dimension(800, 500));
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        backgroundPanel.add(cardPanel);
        backgroundPanel.add(Box.createVerticalGlue());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields - starting from gridy 0 now
        int row = 0;
        JLabel lblname = new JLabel("Name");
        lblname.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lblname, gbc);
        tfname = new JTextField();
        tfname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(tfname, gbc);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lblnationality, gbc);
        tfnationality = new JTextField();
        tfnationality.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(tfnationality, gbc);

        JLabel lblaadhar = new JLabel("Aadhar Number");
        lblaadhar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lblaadhar, gbc);
        tfaadhar = new JTextField();
        tfaadhar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(tfaadhar, gbc);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lbladdress, gbc);
        tfaddress = new JTextField();
        tfaddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(tfaddress, gbc);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lblgender, gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setOpaque(false);
        rbmale = new JRadioButton("Male");
        rbmale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbmale.setBackground(new Color(255,255,255,0));
        rbmale.setOpaque(false);
        rbmale.setFocusPainted(false);
        rbmale.setBorderPainted(false);
        rbfemale = new JRadioButton("Female");
        rbfemale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbfemale.setBackground(new Color(255,255,255,0));
        rbfemale.setOpaque(false);
        rbfemale.setFocusPainted(false);
        rbfemale.setBorderPainted(false);
        ButtonGroup gendergroup = new ButtonGroup();
        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);
        genderPanel.add(rbmale);
        genderPanel.add(rbfemale);
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(genderPanel, gbc);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        cardPanel.add(lblphone, gbc);
        tfphone = new JTextField();
        tfphone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row++;
        cardPanel.add(tfphone, gbc);

        // Add status label for feedback
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(ACCENT_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(statusLabel);
        backgroundPanel.add(Box.createVerticalStrut(10));

        // Add input constraints
        tfphone.setDocument(new JTextFieldLimit(10)); // Limit phone to 10 digits
        tfaadhar.setDocument(new JTextFieldLimit(12)); // Limit Aadhar to 12 digits
        
        // Add input listeners for real-time validation
        addInputListeners();
        
        // Styled Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        save = new JButton("SAVE") {
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
        save.setFont(new Font("Montserrat", Font.BOLD, 16));
        save.setForeground(Color.WHITE);
        save.setBackground(PRIMARY_COLOR);
        save.setPreferredSize(new Dimension(120, 40));
        save.setFocusPainted(false);
        save.setBorderPainted(false);
        save.setContentAreaFilled(false);
        save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        save.addActionListener(this);
        buttonPanel.add(save);

        back = new JButton("BACK") {
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
        back.setFont(new Font("Montserrat", Font.BOLD, 16));
        back.setForeground(Color.WHITE);
        back.setBackground(SECONDARY_COLOR);
        back.setPreferredSize(new Dimension(120, 40));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);
        buttonPanel.add(back);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(buttonPanel, gbc);

        setVisible(true);
    }

    private void addInputListeners() {
        tfname.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateName(); }
            public void removeUpdate(DocumentEvent e) { validateName(); }
            public void insertUpdate(DocumentEvent e) { validateName(); }
        });

        tfphone.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validatePhone(); }
            public void removeUpdate(DocumentEvent e) { validatePhone(); }
            public void insertUpdate(DocumentEvent e) { validatePhone(); }
        });

        tfaadhar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateAadhar(); }
            public void removeUpdate(DocumentEvent e) { validateAadhar(); }
            public void insertUpdate(DocumentEvent e) { validateAadhar(); }
        });
    }

    private boolean validateName() {
        String name = tfname.getText().trim();
        if (!NAME_PATTERN.matcher(name).matches()) {
            statusLabel.setText("Name should contain only letters and spaces (2-50 characters)");
            return false;
        }
        statusLabel.setText("");
        return true;
    }

    private boolean validatePhone() {
        String phone = tfphone.getText().trim();
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            statusLabel.setText("Phone number should be 10 digits");
            return false;
        }
        statusLabel.setText("");
        return true;
    }

    private boolean validateAadhar() {
        String aadhar = tfaadhar.getText().trim();
        if (!AADHAR_PATTERN.matcher(aadhar).matches()) {
            statusLabel.setText("Aadhar number should be 12 digits");
            return false;
        }
        statusLabel.setText("");
        return true;
    }

    private boolean validateAllFields() {
        if (tfname.getText().trim().isEmpty() ||
            tfnationality.getText().trim().isEmpty() ||
            tfphone.getText().trim().isEmpty() ||
            tfaddress.getText().trim().isEmpty() ||
            tfaadhar.getText().trim().isEmpty() ||
            (!rbmale.isSelected() && !rbfemale.isSelected())) {
            statusLabel.setText("All fields are required");
            return false;
        }
        return validateName() && validatePhone() && validateAadhar();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            new Home();
            dispose();
            return;
        }

        if (!validateAllFields()) {
            return;
        }

        String name = tfname.getText().trim();
        String nationality = tfnationality.getText().trim();
        String phone = tfphone.getText().trim();
        String address = tfaddress.getText().trim();
        String aadhar = tfaadhar.getText().trim();
        String gender = rbmale.isSelected() ? "Male" : "Female";

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO passenger (name, nationality, phone, address, aadhar, gender) VALUES (?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, nationality);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, aadhar);
            pstmt.setString(6, gender);
            
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, 
                "Customer Details Added Successfully", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            new Home();
            dispose();
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Custom DocumentFilter to limit input length
    private class JTextFieldLimit extends javax.swing.text.PlainDocument {
        private int limit;
        
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) 
            throws javax.swing.text.BadLocationException {
            if (str == null) return;
            
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new AddCustomer());
    }
}
