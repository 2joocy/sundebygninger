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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author William-PC
 */
public class DBHandler {

    public User checkLogin(String username, String password) {
        User newUser = null;
//        password = encryptPassword(password);
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT id, email, businessName, confirmed FROM user WHERE email='" + username + "' AND password='" + password + "'";
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
            String sql = "SELECT id FROM user WHERE confirmed='not'";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return count;
    }

    public void confirmUser(String id) {
        try {
            Connection myConn = DBConnection.getConnection();
            java.sql.Statement mySt = myConn.createStatement();
            String sql = "UPDATE user set confirmed='customer' where id=" + Integer.parseInt(id);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
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
            String sql = "SELECT id, email, businessName FROM user WHERE confirmed='not'";
            System.out.println(sql);
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                email = myRS.getString("email");
                businessName = myRS.getString("businessName");
                id = myRS.getInt("id");

                tableData += "<tr><form method ='POST' action='Front'><td>" + id + "</td><td>" + email + "</td><td>" + businessName + "<input type='hidden' name='methodForm' value='confirmUsers'><input type='hidden' name='userID' value='" + id + "'></td><td><button type='submit'>Confirm User</button></td></form></tr>";

            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public void registerUser(String businessName, String password, String email, String confirmed) {
        try {
            Connection myConn = DBConnection.getConnection();
            java.sql.Statement mySt = myConn.createStatement();
            String sql = "INSERT INTO user (email, password, businessName, confirmed)"
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setString(1, email);
            prepared.setString(2, password);
            prepared.setString(3, businessName);
            prepared.setString(4, confirmed);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void addHouse() {

    }

    public void forgotPass(String email, String password, String businessName) {
        String to = email;

        String from = "bingobango@børneporno.dk";

        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Password Reset Request - Sundere Bygninger");
            message.setText("Hello " + businessName + ". \n You've recently requested "
                    + "that you've forgotten your password. This email will contain your password. "
                    + "Should you change feel like changing it, you can do it under "
                    + "account management after logging in. \n Password: " + password);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
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
