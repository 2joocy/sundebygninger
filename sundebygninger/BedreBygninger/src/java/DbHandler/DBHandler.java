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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author William-PC
 */
public class DBHandler {

    public User checkLogin(String username, String password) {
        User newUser = null;
        password = encryptPassword(password);
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT id, email, businessName, confirmed FROM user WHERE email='" + username + "' AND password='" + password + "'";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                newUser = new User(myRS.getInt("id"), username, myRS.getString("businessName"), myRS.getString("confirmed"));
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return newUser;
    }

    public int countUnConfirmed() {
        int count = 0;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT id FROM user WHERE confirmed=?";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setBoolean(1, false);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return count;
    }

    public void registerUser(String businessName, String password, String email, boolean confirmed) {
        password = encryptPassword(password);
        try {
            Connection myConn = DBConnection.getConnection();
            java.sql.Statement mySt = myConn.createStatement();
            String sql = "INSERT INTO user (email, password, businessName, confirmed)"
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setString(2, password);
            prepared.setString(3, businessName);
            prepared.setBoolean(4, confirmed);
            prepared.executeQuery();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
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
