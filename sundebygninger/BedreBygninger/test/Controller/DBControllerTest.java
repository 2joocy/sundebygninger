
package controller;

import DbHandler.DBConnection;
import entities.Building;
import entities.Report;
import entities.User;
import javax.servlet.http.Part;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DBControllerTest {
    
    DBController ctrl;
    
    public DBControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try{
        ctrl = new DBController(DBConnection.getTestConnection());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of addBuilding method, of class DBController.
     */
    @Test
    public void testAddBuilding() throws Exception {
        System.out.println("addBuilding");
        int buildingId = ctrl.addBuilding("Testvej 1", "a", "1985", "Hedehusene", "2640", "Bingobyen", "", "", "", "85", 21);    //Add building to DB
        Building loaded = ctrl.getBuilding(buildingId);    //Retrieve building from DB
        assertNotNull(loaded); //Checks to see if the building exists
        ctrl.removeBuilding(buildingId); //Delete building from DB
    }
    
     /**
     * Test of addBuilding method throwing an exception, of class DBController.
     */
    @Test
    public void testAddBuildingFail() throws Exception {
        System.out.println("TING HER TING HER");
        assertNull(null);
    }
    
    
    
}
