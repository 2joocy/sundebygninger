/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modafokka;

import static org.junit.Assert.*;
import org.mockito.*;
import DbHandler.DBConnection;
import controller.DBController;
import entities.Building;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author William Pfaffe
 */
public class DbControllerTesting {
    public Building b;
    
    @Mock
    private DBController ctrl;
    
    @BeforeMethod
    public void createBuilding(){
        b = mock(Building.class);
    }

    public DbControllerTesting() {
    }
    
    @Rule
    public ExpectedException excThrow = ExpectedException.none();
    
   
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
    
    @Test
    public void testAddBuilding() throws SQLException{
        System.out.println("addBuilding");
        int buildingId = ctrl.addBuilding("Testvej 1", "a", "1985", "Hedehusene", "2640", "Bingobyen", "", "", "", "85", 21);    //Add building to DB
        Building loaded = ctrl.getBuilding(buildingId);    //Retrieve building from DB
        assertNotNull(loaded); //Checks to see if the building exists
        ctrl.removeBuilding(buildingId); //Delete building from DB
    }
    
    @Test(expected = SQLException.class)
    public void testExceptionAddBuilding() throws SQLException{
        b = ctrl.getBuilding(anyInt());
        when(b == null).thenThrow(new SQLException());
        fail("No such building exist");
    }
    
}
