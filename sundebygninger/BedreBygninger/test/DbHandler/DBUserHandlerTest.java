/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.User;
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
        db.registerUser("testmail@mail.dk", "pass", "Bingo", "85858585", "not", "Bingomanden", "Today");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denyUser method, of class DBUserHandler.
     */
    @Test
    public void testDenyUser() {
        System.out.println("denyUser");
        String id = "";
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        instance.denyUser(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnConfirmed method, of class DBUserHandler.
     */
    @Test
    public void testGetUnConfirmed() {
        System.out.println("getUnConfirmed");
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        String expResult = "";
        String result = instance.getUnConfirmed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePassword method, of class DBUserHandler.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("updatePassword");
        String username = "";
        String password = "";
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        instance.updatePassword(username, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * Test of encryptPassword method, of class DBUserHandler.
     */
    @Test
    public void testEncryptPassword() {
        System.out.println("encryptPassword");
        String password = "";
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        String expResult = "";
        String result = instance.encryptPassword(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of correctPass method, of class DBUserHandler.
     */
    @Test
    public void testCorrectPass() {
        System.out.println("correctPass");
        String password = "";
        String email = "";
        DBUserHandler instance = new DBUserHandler(DBConnection.getTestConnection());
        boolean expResult = false;
        boolean result = instance.correctPass(password, email);
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
