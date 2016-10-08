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
import javax.swing.JOptionPane;

/**
 *
 * @author William-PC
 */
public class DBHandler {
    
    private boolean showJoptionPanes = false;

    public User checkLogin(String username, String password) {
        User newUser = null;
        password = encryptPassword(password);
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idUser, email, businessName, status FROM user WHERE email=? AND password=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setString(1, username);
            prepared.setString(2, password);
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, "Using username: " + username + ", password: " + password);
            }
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                newUser = new User(myRS.getInt("idUser"), username, myRS.getString("businessName"), myRS.getString("status"));
            }
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, newUser == null ? "User is null!" : "User found, " + newUser.getEmail());
            }
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return newUser;
    }

    public boolean userExists(String username) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT * FROM user WHERE email=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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
    
    public int countUnConfirmed() {
        int count = 0;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idUser FROM user WHERE status='not'";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
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

    public void confirmUser(String id) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "UPDATE user set status='customer' where idUser=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, Integer.parseInt(id));
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public String getUnConfirmed() {
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>ID</th>\n"
                + "        <th>Email</th>\n"
                + "        <th>Business Name</th>\n"
                + "         <th></th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        String email = "";
        String businessName = "";
        int id = 0;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idUser, email, businessName FROM user WHERE status='not'";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                email = myRS.getString("email");
                businessName = myRS.getString("businessName");
                id = myRS.getInt("idUser");

                tableData += "<tr><form method ='POST' action='Front'><td>" + id + "</td><td>" + email + "</td><td>" + businessName + "<input type='hidden' name='methodForm' value='confirmUsers'><input type='hidden' name='userID' value='" + id + "'></td><td><button type='submit'>Confirm User</button></td></form></tr>";

            }
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String registerUser(String businessName, String password, String email, String confirmed) {
        if (userExists(email)) {
            return "Error, email already in use.";
        }
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "INSERT INTO user (email, password, businessName, status)"
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setString(2, password);
            prepared.setString(3, businessName);
            prepared.setString(4, confirmed);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            if (showJoptionPanes) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return "";
    }

    public void addBuilding() {
        
    }

    public void updatePassword(String username, String password) {
         try {
            Connection myConn = DBConnection.getConnection();
            String sql = "UPDATE user set password=? where email=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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
                    + "Should you change feel like changing it, you can do it under "
                    + "account management after logging in. \n Password: " + randomPass;
        String status = SendMailTLS.sendMessage(email, subject, message);
        String encrypted = encryptPassword(randomPass);
        updatePassword(email, encrypted);
        return randomPass + "      hashed: " + encrypted + "                        " + status;
    }

    String randomString(int len){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++) {
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
    
}