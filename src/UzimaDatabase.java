import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class UzimaDatabase {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/uzima_database";
    private static final String username = "root";
    private static final String password = "Priscilla@Ngina";

    public static Connection connect() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish connection
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    // Register Logic
    public static boolean userExists(String username, String email) {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // If a result is returned, the user exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting user!");
            e.printStackTrace();
        }
    }

    // Register Logic

    // Login logic
    public static boolean checkCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Login logic


    // Client
    // Insert a client into the clients table
    public static void insertClient (String fullname, String phone, String address, String borehole_location) {
        String sql = "INSERT INTO clients (fullname, address, phone, borehole_location) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1,fullname);
            pstmt.setString(2,phone);
            pstmt.setString(3,address);
            pstmt.setString(4,borehole_location);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Client details inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting client details!");
            e.printStackTrace();
        }
    }
    // Client

}
