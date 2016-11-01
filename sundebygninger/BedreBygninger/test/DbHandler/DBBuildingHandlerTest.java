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

    @Test
    public void testConstructor(){
        DBBuildingHandler dbb = new DBBuildingHandler();
        assertNotNull(dbb.getConn());
        dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getConn());
    }
    
    /**
     * Test of addBuilding method, of class DBBuildingHandler.
     */
    @Test
    public void testAddBuilding() {
        System.out.println("addBuilding");
        DBBuildingHandler instance = new DBBuildingHandler(DBConnection.getTestConnection());
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
        assertEquals(b, loaded);      //Check if the two buildings have same info
        System.out.println("Building was loaded correctly.");
        instance.removeBuilding(loaded.getIdBuilding());        //Delete building from DB
        Building hopefullyNull = instance.getBuilding(address);
        assertNull(hopefullyNull);
    }
    
    @Test
    public void testGetBuildings(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getBuildings(9));
    }
    
    @Test
    public void testGetBuilding(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getBuilding(14));
        assertNotNull(dbb.getBuilding("YupYup"));
    }
    
    @Test
    public void testGetRooms(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getRooms(8));
    }
    
    @Test
    public void testEditBuilding(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        dbb.editBuilding("YupYup", "Snask", "123", "Yush", "1337", "YoloSwag", "Harambe", "27/10/2016 16:21:00", 15);
        assertNotNull(dbb.getBuilding("YupYup"));
    }
    
    @Test
    public void testGetBuildingCount(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getBuildingCount(9));
        assertNotNull(dbb.getBuildingCount());
    }
    
    @Test
    public void testGetService(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getService(9));
    }
    
    @Test
    public void testCreateMenu(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.createMenu("customer"));
        assertNotNull(dbb.createMenu("worker"));
        assertNotNull(dbb.createMenu(null));
    }
    
    @Test
    public void testGetReportField(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNull(dbb.getReportField(7, ""));
    }
    
    @Test
    public void testGetImageHTML(){
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        assertNotNull(dbb.getImageHTML(9));
        assertNotNull(dbb.getImageHTML(9, 1, 1));
    }
    
    @Test
    public void testSubmitReport() {
        DBBuildingHandler handler = new DBBuildingHandler(DBConnection.getTestConnection());
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
