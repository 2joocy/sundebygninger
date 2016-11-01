/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.User;
import javax.swing.JOptionPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CHRIS
 */
public class DBUserHandlerTest {
    
    public DBUserHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor(){
        DBUserHandler db = new DBUserHandler();
        assertNotNull(db.getConn());
    }
    
    /**
     * Test of checkLogin method, of class DBUserHandler.
     */
    @Test
    public void testCheckAddRemoveUser() {
        System.out.println("checkLogin");
        String email = "testmail@mail.dk";
        String password = "pass";
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        String s = db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        assertEquals(s, "Error, email already in use.");
        User user = db.checkLogin(email, password);
        assertNotNull(user);
        db.removeUser(email);
        db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        user = db.checkLogin(user.getEmail(), password);
        db.removeUser(user.getIdUser());
        user = db.checkLogin(user.getEmail(), password);
        assertNull(user);
    }
   

    /**
     * Test of countUnConfirmed method, of class DBUserHandler.
     */
    @Test
    public void testCountUnConfirmed() {
        System.out.println("countUnConfirmed");
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        assertEquals(db.countUnConfirmed(), 0);
        db.registerUser("testmail@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        assertEquals(db.countUnConfirmed(), 1);
        db.removeUser("testmail@mail.dk");
        
    }

    /**
     * Test of confirmUser method, of class DBUserHandler.
     */
    @Test
    public void testConfirmUser() {
        System.out.println("confirmUser");
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        db.registerUser("testmail1@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        User user = db.checkLogin("testmail1@mail.dk", "pass");
        assertEquals(user.getStatus(), "not");
        db.confirmUser("testmail1@mail.dk");
        user = db.checkLogin("testmail1@mail.dk", "pass");
        assertEquals(user.getStatus(), "customer");
        db.removeUser("testmail1@mail.dk");
    }
    
    @Test
    public void testConfirmUser2() {
        System.out.println("confirmUser");
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        db.registerUser("testmail1@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        User user = db.checkLogin("testmail1@mail.dk", "pass");
        assertEquals(user.getStatus(), "not");
        db.confirmUser(user.getIdUser());
        user = db.checkLogin("testmail1@mail.dk", "pass");
        assertEquals(user.getStatus(), "customer");
        db.removeUser("testmail1@mail.dk");
    }

    /**
     * Test of denyUser method, of class DBUserHandler.
     */
    @Test
    public void testDenyUser() {
        System.out.println("denyUser");
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        db.registerUser("testmail2@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        User user = db.checkLogin("testmail2@mail.dk", "pass");
        assertEquals(user.getStatus(), "not");
        db.denyUser("testmail2@mail.dk");
        db.denyUser(user.getIdUser());
        user = db.checkLogin("testmail2@mail.dk", "pass");
        assertEquals(user.getStatus(), "denied");
        db.removeUser("testmail2@mail.dk");
    }
    

    
    /**
     * Test of updatePassword method, of class DBUserHandler.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("updatePassword");
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        db.registerUser("testmail3@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        User user = db.checkLogin("testmail3@mail.dk", "pass");
        assertNotNull(user);
        db.updatePassword("testmail3@mail.dk", "pass1");
        user = db.checkLogin("testmail3@mail.dk", "pass1");
        assertNotNull(user);
        db.removeUser("testmail3@mail.dk");
    }

    
    @Test
    public void testGetUnconfirmed(){
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        String s = db.getUnConfirmed();
        assertNotNull(s);
    }
    
    @Test
    public void testCorrectPass(){
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        String email = "testmail@mail.dk";
        String password = "pass";
        db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        assertTrue(db.correctPass(password, email));
        db.removeUser(email);
    }
    
    /**
     * Test of forgotPass method, of class DBUserHandler.
     */
    @Test
    public void testForgotPass() {
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        String email = "viktor@gmail.com";
        String password = "pass";
        db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        assertNotNull(db.forgotPass(email, "Bingo"));
        db.removeUser(email);
    }

    /**
     * Test of randomString method, of class DBUserHandler.
     */
    @Test
    public void testRandomString() {
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        assertNotNull(db.randomString(8));
    }

    /**
     * Test of updateEmail method, of class DBUserHandler.
     */
    @Test
    public void testUpdateEmail() {
        DBUserHandler db = new DBUserHandler(DBConnection.getTestConnection());
        String email = "test@gmail.com";
        String password = "pass";
        db.registerUser(email, password, "Bingo", "85858585", "not", "Bingomanden", "Today");
        User user = db.checkLogin(email, password);
        db.updateEmail("newemail@gmail.com", user.getIdUser());
        user = db.checkLogin("newemail@gmail.com", password);
        assertEquals("newemail@gmail.com", user.getEmail());
        db.removeUser(user.getIdUser());
    }
    
}
