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

    public User checkLogin(String usrname, String pssword) {
        User newUser = null;
        pssword = this.encryptPassword(pssword);
        try {
            int count = 0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://77.66.117.72:3306/pfaffeeu_projects", "pfaffeeu_william", "william1O");
            java.sql.Statement mySt = myConn.createStatement();
            String rs = "SELECT id, email, businessName, confirmed FROM user WHERE username='" + usrname + "'AND password='" + pssword + "'";

            System.out.println(rs);
            ResultSet myRS = mySt.executeQuery(rs);
            while (myRS.next()) {
                newUser = new User(myRS.getString("email"), myRS.getString("businessName"), myRS.getBoolean("confirmed"));
                count++;
            }

            if (count == 0) {
                return newUser;
            }

            if (count == 1) {
                return newUser;
            }
            myConn.close();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newUser;
    }

    public void registerUser(String businessName, String pssword, String email, boolean confirmed) {
        pssword = this.encryptPassword(pssword);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://77.66.117.72:3306/pfaffeeu_projects", "pfaffeeu_william", "william1O");
            java.sql.Statement mySt = myConn.createStatement();

            String myRS = "INSERT INTO user (email, password, businessName, confirmed)"
                    + "VALUES ('" + email + "', '" + pssword + "', '" + businessName + "', '" + confirmed + "')";

            mySt.executeUpdate(myRS);

            mySt.close();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
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
