package airlinemanagementsystem;

import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;
    
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem1", "root", "abhinav@sql80");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}