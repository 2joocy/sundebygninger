/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.User;
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
import javax.swing.JOptionPane;

public class DBUserHandler {

    Connection conn;
    
    public DBUserHandler() throws ClassNotFoundException, SQLException {
        this.conn = DBConnection.getConnection();
    }
    
    public DBUserHandler(Connection conn) {
        this.conn = conn;
    }
    
    private final boolean showJoptionPanes = false;

    public User checkLogin(String email, String password) {
        User newUser = null;
        password = encryptPassword(password);
        try {
            String sql = "SELECT idUser, email, businessName, phone, status, fullName, createdDate FROM user WHERE email=? AND password=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setString(2, password);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                newUser = new User(myRS.getInt("idUser"), myRS.getString("email"), myRS.getString("businessName"), myRS.getString("phone"), myRS.getString("status"), myRS.getString("fullName"), myRS.getString("createdDate"));
            }
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return newUser;
    }

    public String registerUser(String email, String password, String businessName, String phone, String status, String fullName, String createdDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();

        if (userExists(email)) {
            return "Error, email already in use.";
        }

        try {
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
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return "";
    }

    public boolean userExists(String username) {
        try {
            String sql = "SELECT * FROM user WHERE email=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, username);
            ResultSet result = prepared.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getUserFromDB(int id){
        String user = ""; 
        try {
            String sql = "SELECT businessName FROM user WHERE idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet result = prepared.executeQuery();
            if (result.next()) {
                user = result.getString("businessName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return user;
    }
    
    public int countUnConfirmed() {
        int count = 0;
        try {
            String sql = "SELECT idUser FROM user WHERE status='not'";
            //System.out.println(sql);
            PreparedStatement prepared = conn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return count;
    }

    public void confirmUser(int id) {
        try {
            String sql = "UPDATE user set status='customer' where idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    public void confirmUser(String email) {
        try {
            String sql = "UPDATE user set status='customer' where email=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void removeUser(int id){
        try {
            String sql = "DELETE FROM user WHERE idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    public void removeUser(String email){
        try {
            String sql = "DELETE FROM user WHERE email=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    public void denyUser(int id) {
        try {
            String sql = "UPDATE user set status='denied' where idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void denyUser(String email) {
        try {
            String sql = "UPDATE user set status='denied' where email=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    public void updatePassword(String username, String password) {
        password = this.encryptPassword(password);
        try {
            String sql = "UPDATE user set password=? where email=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, password);
            prepared.setString(2, username);
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String forgotPass(String email, String businessName) {
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

    public String encryptPassword(String password) {
        String encryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    public boolean correctPass(String password, String email) {
        boolean isCorrect = false;
        String password2 = this.encryptPassword(password);
        int count = 0;
        try {
            String sql = "SELECT idUser FROM user WHERE email=? AND password=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setString(2, password2);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {

        }

        if (count > 0) {
            isCorrect = true;
        }

        return isCorrect;
    }

    public void updateEmail(String email, int id) {
        try {
            String sql = "UPDATE user set email=? where idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setInt(2, id);
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

}