/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.Building;
import java.awt.Image;
import javax.servlet.http.Part;
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
public class DBBuildingHandlerTest {
    
    public DBBuildingHandlerTest() {
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
     * Test of addBuilding method, of class DBBuildingHandler.
     */
    @Test
    public void testAddBuilding() {
        System.out.println("addBuilding");
        DBBuildingHandler instance = new DBBuildingHandler();
        String address = "aa123123123";
        String cadastral = "a";
        String builtYear = "a";
        String area = "a";
        String zipcode = "a";
        String city = "a";
        String conditionText = "a";
        String service = "a";
        String extraText = "a";
        String dateCreated = "a";
        int idBuilding = instance.getBuildingCount();
        int fk_idUser = 1;
        int fk_idMainPicture = 0;
        int fk_idReport = 0;
        
        Building b = new Building(idBuilding, address, cadastral, area, zipcode, city, conditionText, service, extraText, builtYear, fk_idUser, fk_idMainPicture, fk_idReport, dateCreated);
        instance.addBuilding(b);    //Add building to DB
        Building loaded = instance.getBuilding(address);    //Retrieve building from DB
        if (b.equals(loaded)) {     //Check if the two buildings have same info
            System.out.println("Building was loaded correctly.");
            instance.removeBuilding(loaded.getIdBuilding());        //Delete building from DB
            Building hopefullyNull = instance.getBuilding(address);
            assertNull(hopefullyNull);
            return;
        }
        fail("Failed and stuff");
    }
    
    @Test
    public void testSubmitReport() {
        DBBuildingHandler handler = new DBBuildingHandler();
        String buildingUsage = "Yes.";
        boolean roofRemarks = false;
        String roofText = "It's a roof.";
        String outerWallText = "Yep, definitely a wall.";
        String buildingResponsible = "Me.";
        int fk_idPictureRoof = 0;
        int fk_idPictureOuterRoof = 0;
        int fk_idEmployee = 15;
        handler.submitReport(buildingUsage, roofRemarks, fk_idPictureRoof, roofText, true, fk_idPictureOuterRoof, outerWallText, fk_idEmployee, buildingResponsible);
        assertTrue(true);
    }
    
}
