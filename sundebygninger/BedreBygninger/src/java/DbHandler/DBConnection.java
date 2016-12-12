package DbHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
 
    private static Connection con;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con == null || con.getMetaData().getURL().contains("test")) {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://viter.dk/sundebygninger", "transformer", "bookworm#17laesehest");
        }
        return con;
    }
    
    public static Connection getTestConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://viter.dk/testsundebygninger", "transformer", "bookworm#17laesehest");
        return con;
    }
    
}