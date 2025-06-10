import java.sql.*;
import javax.swing.JOptionPane;

public class Conn {
    
    public Connection c;
    public Statement s;
    
    public Conn() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airlinemanagementsystem1",
                "root",
                "akiraded"
            );
            
            // Create statement
            s = c.createStatement();
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database driver not found. Please check your installation.");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Could not connect to database. Please check your database settings.");
        }
    }
    
    public void close() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    // Method to get a prepared statement
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return c.prepareStatement(sql);
    }
}
