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

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
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

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
    public static boolean clientExists(String fullname, String phone) {
        String sql = "SELECT * FROM clients WHERE fullname = ? OR phone = ?";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fullname);
            pstmt.setString(2, phone);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertClient (String fullname, String phone, String address, String borehole_location,String clientCategory, String surveyFee, String localAuthorityFee) {
        String sql = "INSERT INTO clients (fullname, phone, address, borehole_location,client_category, survey_fee, local_authority_fee) VALUES (?, ?, ?, ?,?,?,?)";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1,fullname);
            pstmt.setString(2,phone);
            pstmt.setString(3,address);
            pstmt.setString(4,borehole_location);
            pstmt.setString(5, clientCategory);
            pstmt.setString(6, surveyFee);
            pstmt.setString(7, localAuthorityFee);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Client details inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting client details!");
            e.printStackTrace();
        }
    }
    // Client


    // Service
    public static void insertServices (String typeDrilling, String downPayment, String typePump, String pumpCost, String plumbing, String maintenance, String tax, String totalFee ) {
        String sql = "INSERT INTO services (typeDrilling, downPayment, typePump, pumpCost, plumbing, maintenance, tax, totalFee) VALUES (?, ?, ?, ?,?,?,?,?)";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1,typeDrilling);
            pstmt.setString(2,downPayment);
            pstmt.setString(3,typePump);
            pstmt.setString(4,pumpCost);
            pstmt.setString(5,plumbing);
            pstmt.setString(6,maintenance);
            pstmt.setString(7,tax);
            pstmt.setString(8,totalFee);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Services details inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting services details!");
            e.printStackTrace();
        }
    }
    // Service

    // =========================================================
    //               REPORT DATA FOR ADMIN PANEL
    // =========================================================

    // === CLIENTS ===
    public static List<Object[]> getAllClients() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT c.fullname, s.totalFee FROM clients c " +
                "JOIN services s ON c.client_id = s.client_id";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Object[]{
                    rs.getString("fullName"),
                    rs.getDouble("amount_paid")});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    // === PLUMBING ===
    public static List<Object[]> getAllPlumbing() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT c.fullname, s.plumbing FROM clients c " +
                "JOIN services s ON c.client_id = s.client_id";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Object[]{
                    rs.getString("fullName"),
                    rs.getDouble("plumbing")});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double getTotalPlumbingCharges() {
        double total = 0;
        String sql = "SELECT SUM(plumbing) AS total FROM services";

        try (Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return total;
    }

    // === TAX ===
    public static List<Object[]> getAllTaxes() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT c.fullname, s.tax FROM clients c " +
                "JOIN services s ON c.client_id = s.client_id";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Object[]{
                    rs.getString("fullName"),
                    rs.getDouble("tax")});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    // === FEES ===
    public static List<Object[]> getAllFees() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT fullname, survey_fee, local_authority_fee FROM clients";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Object[]{
                    rs.getString("fullName"),
                    rs.getDouble("survey_fee"),
                    rs.getDouble("local_authority_fee")});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double getTotalFees() {
        double total = 0;
        String sql = "SELECT SUM(survey_fee + local_authority_fee) AS total FROM clients";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return total;
    }

    // === REVENUE ===
    public static List<Object[]> getAllRevenue() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT c.fullname, s.tax FROM clients c " +
                "JOIN services s ON c.client_id = s.client_id";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Object[]{
                    rs.getString("fullName"),
                    rs.getDouble("tax")});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
