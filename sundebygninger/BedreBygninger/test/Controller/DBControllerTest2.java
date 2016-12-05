package Controller;

import DbHandler.DBConnection;
import controller.DBController;
import entities.Building;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.Mockito;

public class DBControllerTest2 {
    
    DBController ctrl;

    public DBControllerTest2() throws ClassNotFoundException, SQLException {
        this.ctrl = new DBController(DBConnection.getTestConnection());
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
    
    @Test(expected = SQLException.class)
    public void testAddBuildingException() throws SQLException {
        ctrl = Mockito.mock(DBController.class);
        Mockito.when(ctrl.addBuilding("", "", "", "", "", "", "", "", "", "", 0)).thenThrow(new SQLException("Test SQL exception"));
        ctrl.addBuilding("", "", "", "", "", "", "", "", "", "", 0);
    }
    
}