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
        User user = db.checkLogin(email, password);
        assertNotNull(user);
        db.removeUser(email);
        user = db.checkLogin(email, password);
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

    /**
     * Test of forgotPass method, of class DBUserHandler.
     */
    @Test
    public void testForgotPass() {
        System.out.println("forgotPass");
        String email = "";
        String businessName = "";
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        String expResult = "";
        String result = instance.forgotPass(email, businessName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of randomString method, of class DBUserHandler.
     */
    @Test
    public void testRandomString() {
        System.out.println("randomString");
        int len = 0;
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        String expResult = "";
        String result = instance.randomString(len);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateEmail method, of class DBUserHandler.
     */
    @Test
    public void testUpdateEmail() {
        System.out.println("updateEmail");
        String email = "";
        int id = 0;
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        instance.updateEmail(email, id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
