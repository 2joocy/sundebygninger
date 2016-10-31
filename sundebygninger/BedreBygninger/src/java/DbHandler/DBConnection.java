/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHRIS
 */
public class DBConnection {
 
    private static Connection con;
    
    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://viter.dk/sundebygninger", "transformer", "bookworm#17laesehest");
            } catch (Exception ex) {
                System.out.println("hej");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
    
    public static Connection getTestConnection() {
//        if (con == null) 
        {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                return con = DriverManager.getConnection("jdbc:mysql://viter.dk/testsundebygninger", "transformer", "bookworm#17laesehest");
            } catch (Exception ex) {
                System.out.println("hej");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
    
}

