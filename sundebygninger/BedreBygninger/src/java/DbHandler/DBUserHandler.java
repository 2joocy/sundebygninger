package DbHandler;

import entities.User;
import exceptions.UserExistsException;
import java.awt.HeadlessException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains methods and functions that deliver and retrieve data to- and from the database with regards to any user-related subjects.
 */
public class DBUserHandler {

    Connection conn;
    
    public DBUserHandler() throws ClassNotFoundException, SQLException {
        this.conn = DBConnection.getConnection();
    }
    
    public DBUserHandler(Connection conn) {
        this.conn = conn;
    }
    
    public User checkLogin(String email, String password) throws SQLException {
        User newUser = null;
        password = encryptPassword(password);
        String sql = "SELECT idUser, email, businessName, phone, status, fullName, createdDate FROM user WHERE email=? AND password=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.setString(2, password);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            newUser = new User(myRS.getInt("idUser"), myRS.getString("email"), myRS.getString("businessName"), myRS.getString("phone"), myRS.getString("status"), myRS.getString("fullName"), myRS.getString("createdDate"));
        }
        return newUser;
    }

    public void registerUser(String email, String password, String businessName, String phone, String status, String fullName, String createdDate) throws SQLException, UserExistsException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();

        if (userExists(email)) {
            throw new UserExistsException("Email already in use.");
        }
        
        String sql = "INSERT INTO user (email, password, businessName, phone, status, fullName, createdDate)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.setString(2, this.encryptPassword(password));
        prepared.setString(3, businessName);
        prepared.setString(4, phone);
        prepared.setString(5, status);
        prepared.setString(6, fullName);
        prepared.setString(7, dateFormat.format(datePre));

        prepared.executeUpdate();
    }

    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, username);
        ResultSet result = prepared.executeQuery();
        if (result.next()) {
            return true;
        }
        return false;
    }
    
    public String getUserFromDB(int id) throws SQLException{
        String user = ""; 
        String sql = "SELECT businessName FROM user WHERE idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, id);
        ResultSet result = prepared.executeQuery();
        if (result.next()) {
            user = result.getString("businessName");
        }
        return user;
    }
    
    public int countUnConfirmed() throws SQLException {
        int count = 0;
        String sql = "SELECT idUser FROM user WHERE status='not'";
        //System.out.println(sql);
        PreparedStatement prepared = conn.prepareStatement(sql);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            count++;
        }
        return count;
    }

    public void confirmUser(int id) throws SQLException {
        String sql = "UPDATE user set status='customer' where idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, id);
        prepared.executeUpdate();
    }
    
    public void confirmUser(String email) throws SQLException {
        String sql = "UPDATE user set status='customer' where email=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.executeUpdate();
    }

    public void removeUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, id);
        prepared.executeUpdate();
    }
    
    public void removeUser(String email) throws SQLException {
        String sql = "DELETE FROM user WHERE email=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.executeUpdate();
    }
    
    public void denyUser(int id) throws SQLException {
        String sql = "UPDATE user set status='denied' where idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, id);
        prepared.executeUpdate();
    }

    public void denyUser(String email) throws SQLException {
        String sql = "UPDATE user set status='denied' where email=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.executeUpdate();
    }
    
    public void updatePassword(String username, String password) throws SQLException {
        password = this.encryptPassword(password);
        String sql = "UPDATE user set password=? where email=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, password);
        prepared.setString(2, username);
        prepared.executeUpdate();
    }

    public String forgotPass(String email, String businessName) throws SQLException {
        String subject = "Password Reset Request - Sundere Bygninger";
        String randomPass = randomString(12);
        String message = "Hello " + businessName + ". \n You've recently requested "
                + "that you've forgotten your password. This email will contain your password. "
                + "Should you feel like changing it, you can do it under "
                + "account management after logging in. \n Password: " + randomPass;
        String status = SendMailTLS.sendMessage(email, subject, message);
        String encrypted = encryptPassword(randomPass);
        updatePassword(email, encrypted);
        return randomPass + "      hashed: " + encrypted + "                        " + status;
    }

    private String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    private String encryptPassword(String password) {
        String encryptedPassword = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        encryptedPassword = sb.toString();
        return encryptedPassword;
    }

    public boolean correctPass(String password, String email) throws SQLException {
        String password2 = this.encryptPassword(password);
        String sql = "SELECT idUser FROM user WHERE email=? AND password=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.setString(2, password2);
        ResultSet myRS = prepared.executeQuery();
        return myRS.next();
    }

    public void updateEmail(String email, int id) throws SQLException {
        String sql = "UPDATE user set email=? where idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, email);
        prepared.setInt(2, id);
        prepared.executeUpdate();
    }

    public Connection getConn() {
        return conn;
    }

}