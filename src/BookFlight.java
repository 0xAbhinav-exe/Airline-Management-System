import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.border.*;
import java.util.Calendar;
import java.awt.event.*;
import javax.swing.event.*;

public class BookFlight extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final int CORNER_RADIUS = 28;

    JTextField tfname, tfaddress, tfphone, tfemail, tfaadhar;
    JComboBox<String> source, destination, gender, nationality;
    JPanel datePickerPanel;
    JButton submit, back;
    JLabel priceLabel;
    double selectedPrice = 0.0;
    private Date selectedDate;

    public BookFlight() {
        setTitle("Book Flight");
        setSize(1000, 800);
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

        JLabel mainHeading = new JLabel("Book Your Flight");
        mainHeading.setFont(new Font("Montserrat", Font.BOLD, 32));
        mainHeading.setForeground(TEXT_COLOR);
        mainHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subHeading = new JLabel("<html><div style='text-align:center;'>Fill in your details to book your flight</div></html>");
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

        // MAIN FORM CARD
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
        cardPanel.setMaximumSize(new Dimension(900, 600));
        cardPanel.setPreferredSize(new Dimension(900, 600));
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        backgroundPanel.add(cardPanel);
        backgroundPanel.add(Box.createVerticalGlue());

        // Create two columns for the form
        JPanel leftColumn = new JPanel();
        leftColumn.setOpaque(false);
        leftColumn.setLayout(new GridBagLayout());
        leftColumn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JPanel rightColumn = new JPanel();
        rightColumn.setOpaque(false);
        rightColumn.setLayout(new GridBagLayout());
        rightColumn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Left Column Fields
        int row = 0;
        
        // Name
        addFormField(leftColumn, "Name", tfname = createStyledTextField(), gbc, row++);
        
        // Aadhar
        addFormField(leftColumn, "Aadhar", tfaadhar = createStyledTextField(), gbc, row++);
        
        // Gender
        String[] genders = {"Select", "Male", "Female", "Other"};
        gender = createStyledComboBox(genders);
        addFormField(leftColumn, "Gender", gender, gbc, row++);
        
        // Nationality
        String[] nationalities = {"Select", "Indian", "Foreign"};
        nationality = createStyledComboBox(nationalities);
        nationality.addActionListener(e -> updatePrice());
        addFormField(leftColumn, "Nationality", nationality, gbc, row++);
        
        // Address
        addFormField(leftColumn, "Address", tfaddress = createStyledTextField(), gbc, row++);

        // Right Column Fields
        row = 0;
        
        // Phone
        addFormField(rightColumn, "Phone", tfphone = createStyledTextField(), gbc, row++);
        
        // Email
        addFormField(rightColumn, "Email", tfemail = createStyledTextField(), gbc, row++);
        
        // Source
        String[] sources = {"Select", "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata"};
        source = createStyledComboBox(sources);
        source.addActionListener(e -> updatePrice());
        addFormField(rightColumn, "Source", source, gbc, row++);
        
        // Destination
        String[] destinations = {"Select", "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata"};
        destination = createStyledComboBox(destinations);
        destination.addActionListener(e -> updatePrice());
        addFormField(rightColumn, "Destination", destination, gbc, row++);
        
        // Date
        datePickerPanel = createDatePickerPanel();
        addFormField(rightColumn, "Date", datePickerPanel, gbc, row++);

        // Add columns to card panel
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        cardPanel.add(leftColumn, gbc);

        gbc.gridx = 1;
        cardPanel.add(rightColumn, gbc);

        // Price Label
        priceLabel = new JLabel("Price: ₹0.00");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(PRIMARY_COLOR);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(priceLabel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        cardPanel.add(buttonPanel, gbc);

        submit = createStyledButton("Book", PRIMARY_COLOR);
        back = createStyledButton("Back", ACCENT_COLOR);

        submit.addActionListener(e -> bookFlight());
        back.addActionListener(e -> {
            new Home();
            setVisible(false);
        });

        buttonPanel.add(submit);
        buttonPanel.add(back);

        // Create scroll pane for the main content
        JScrollPane scrollPane = new JScrollPane(backgroundPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(200, 35));
        field.setMaximumSize(new Dimension(200, 35));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setPreferredSize(new Dimension(200, 35));
        comboBox.setMaximumSize(new Dimension(200, 35));
        return comboBox;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
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
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(150, 45));
        button.setMaximumSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 17f));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setFont(button.getFont().deriveFont(Font.BOLD, 16f));
            }
        });
        
        return button;
    }

    private void updatePrice() {
        try {
            String selectedSource = (String) source.getSelectedItem();
            String selectedDestination = (String) destination.getSelectedItem();
            String selectedNationality = (String) nationality.getSelectedItem();

            // Reset price if any selection is "Select"
            if (selectedSource.equals("Select") || selectedDestination.equals("Select") || selectedNationality.equals("Select")) {
                selectedPrice = 0.0;
                priceLabel.setText("Price: ₹0.00");
                return;
            }

            // Don't show price if source and destination are same
            if (selectedSource.equals(selectedDestination)) {
                selectedPrice = 0.0;
                priceLabel.setText("Source and destination cannot be same");
                return;
            }

            Conn c = new Conn();
            String query = "SELECT price FROM flight_prices WHERE source = '" + selectedSource + 
                          "' AND destination = '" + selectedDestination + 
                          "' AND nationality = '" + selectedNationality + "'";
            
            System.out.println("Executing query: " + query); // Debug print
            
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                selectedPrice = rs.getDouble("price");
                priceLabel.setText(String.format("Price: ₹%.2f", selectedPrice));
                System.out.println("Price found: " + selectedPrice); // Debug print
            } else {
                selectedPrice = 0.0;
                priceLabel.setText("No price available for this route");
                System.out.println("No price found in database"); // Debug print
            }
            
            rs.close();
            c.s.close();
            c.c.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating price: " + e.getMessage());
            selectedPrice = 0.0;
            priceLabel.setText("Error fetching price");
        }
    }

    private void bookFlight() {
        try {
            String name = tfname.getText();
            String aadhar = tfaadhar.getText();
            String gender = (String) this.gender.getSelectedItem();
            String nationality = (String) this.nationality.getSelectedItem();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String source = (String) this.source.getSelectedItem();
            String destination = (String) this.destination.getSelectedItem();

            if (name.isEmpty() || aadhar.isEmpty() || gender.equals("Select") || 
                nationality.equals("Select") || address.isEmpty() || phone.isEmpty() || 
                email.isEmpty() || source.equals("Select") || destination.equals("Select") || 
                selectedDate == null) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            if (source.equals(destination)) {
                JOptionPane.showMessageDialog(null, "Source and destination cannot be same");
                return;
            }

            // Validate price
            if (selectedPrice <= 0) {
                JOptionPane.showMessageDialog(null, "Please select a valid flight route");
                return;
            }

            // Validate Aadhar number
            if (!aadhar.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(null, "Invalid Aadhar number");
                return;
            }

            // Validate phone number
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Invalid phone number");
                return;
            }

            // Validate email
            if (!email.matches("^[A-Za-z0-9+._-]+@(.+)$|^''$")) { // Allow empty string or valid email
                JOptionPane.showMessageDialog(null, "Invalid email address");
                return;
            }

            Conn c = new Conn();
            String id = generatePNR();
            String ticketNumber = generateTicketNumber();
            String seatNumber = generateSeatNumber();

            String query = "INSERT INTO customer (id, name, aadhar, gender, nationality, address, phone, email, source, destination, date, price, pnr, ticket_number, seat_number) " +
                          "VALUES ('" + id + "', '" + name + "', '" + aadhar + "', '" + gender + "', '" + nationality + "', '" + address + "', '" + phone + "', '" + email + "', '" + 
                          source + "', '" + destination + "', '" + new java.sql.Date(selectedDate.getTime()) + "', " + selectedPrice + ", '" + id + "', '" + ticketNumber + "', '" + seatNumber + "')";

            c.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Flight Booked Successfully!\nYour PNR is: " + id);
            new Home();
            setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking flight: " + e.getMessage());
        }
    }

    private String generatePNR() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String generateTicketNumber() {
        return String.format("TKT%06d", new Random().nextInt(1000000));
    }

    private String generateSeatNumber() {
        return String.format("%d%c", new Random().nextInt(30) + 1, (char)('A' + new Random().nextInt(6)));
    }

    private JPanel createDatePickerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);

        JTextField dateField = new JTextField(10);
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateField.setPreferredSize(new Dimension(200, 35));
        dateField.setMaximumSize(new Dimension(200, 35));
        dateField.setEditable(false);

        JButton calendarButton = new JButton("📅");
        calendarButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        calendarButton.setPreferredSize(new Dimension(35, 35));
        calendarButton.setMaximumSize(new Dimension(35, 35));
        calendarButton.setFocusPainted(false);
        calendarButton.setBorderPainted(false);
        calendarButton.setContentAreaFilled(false);
        calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        calendarButton.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Select Date", true);
            dialog.setSize(300, 400);
            dialog.setLocationRelativeTo(this);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Calendar calendar = Calendar.getInstance();
            if (selectedDate != null) {
                calendar.setTime(selectedDate);
            }

            JLabel monthLabel = new JLabel(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
            monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton prevButton = new JButton("←");
            JButton nextButton = new JButton("→");
            prevButton.setFocusPainted(false);
            nextButton.setFocusPainted(false);

            prevButton.addActionListener(ev -> {
                calendar.add(Calendar.MONTH, -1);
                monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
                updateCalendar(calendar, (JPanel) calendarPanel.getComponent(1), monthLabel, dateField, dialog);
            });

            nextButton.addActionListener(ev -> {
                calendar.add(Calendar.MONTH, 1);
                monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
                updateCalendar(calendar, (JPanel) calendarPanel.getComponent(1), monthLabel, dateField, dialog);
            });

            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.add(monthLabel, BorderLayout.CENTER);
            headerPanel.add(buttonPanel, BorderLayout.EAST);

            JPanel gridPanel = new JPanel(new GridLayout(0, 7, 5, 5));
            String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            for (String day : days) {
                JLabel label = new JLabel(day);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                gridPanel.add(label);
            }

            updateCalendar(calendar, gridPanel, monthLabel, dateField, dialog);

            calendarPanel.add(headerPanel, BorderLayout.NORTH);
            calendarPanel.add(gridPanel, BorderLayout.CENTER);

            dialog.add(calendarPanel);
            dialog.setVisible(true);
        });

        panel.add(dateField);
        panel.add(calendarButton);

        return panel;
    }

    private void updateCalendar(Calendar calendar, JPanel gridPanel, JLabel monthLabel, JTextField dateField, JDialog dialog) {
        gridPanel.removeAll();
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            gridPanel.add(label);
        }

        Calendar cal = (Calendar) calendar.clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfWeek; i++) {
            gridPanel.add(new JLabel(""));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int day = i;
            button.addActionListener(e -> {
                calendar.set(Calendar.DAY_OF_MONTH, day);
                selectedDate = calendar.getTime();
                dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedDate));
                dialog.dispose();
            });

            gridPanel.add(button);
        }

        gridPanel.repaint();
    }
}
