/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.Building;
import entities.Report;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
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
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        int buildingId = dbb.addBuilding("Testvej 1", "a", "1985", "Hedehusene", "2640", "Bingobyen", "", "", "", "85", 1);    //Add building to DB
        Building loaded = dbb.getBuilding(buildingId);    //Retrieve building from DB
        Building loaded2 = dbb.getBuilding(loaded.getAddress());    //Retrieve building from DB
        assertEquals(loaded.getAddress(), loaded2.getAddress()); //Check if the two buildings have same info
        assertEquals(loaded.getFk_idReport(), dbb.getFkIdReport(loaded.getIdBuilding()));
        dbb.removeBuilding(buildingId);        //Delete building from DB
        Building hopefullyNull = dbb.getBuilding(buildingId);
        assertNull(hopefullyNull);
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
        assertNotNull(ImageHandler.getImageHTML(9));
        assertNotNull(ImageHandler.getImageHTML(9, 1, 1));
    }
    
    @Test
    public void testUploadImage() {
        DBBuildingHandler dbb = new DBBuildingHandler(DBConnection.getTestConnection());
        int value = ImageHandler.uploadImage(dbb.getConn(), "TestImage", "png", getPremadePart());
        System.out.println("[testUploadMainImage] " + value);
        assertNotEquals(-1, value);
        dbb.removePicture(value);
    }
    
    
    
    public Part getPremadePart() {
        String path = "extra/Activity Diagram/MembershipApproval.png";
        File f = new File(path);
        return new Part() {
            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(f);
            }

            @Override
            public String getContentType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getSubmittedFileName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public long getSize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void write(String fileName) throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void delete() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getHeader(String name) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Collection<String> getHeaders(String name) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Collection<String> getHeaderNames() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
    
}