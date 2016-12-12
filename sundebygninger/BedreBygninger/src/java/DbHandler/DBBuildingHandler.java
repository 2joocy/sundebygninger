/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.Building;
import entities.Report;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * This class contains methods and functions that deliver and retrieve data to- and from the database with regards to any building-related subjects.
 */
public class DBBuildingHandler {

    private Connection conn;

    public DBBuildingHandler() throws ClassNotFoundException, SQLException {
        this.conn = DBConnection.getConnection();
    }

    public DBBuildingHandler(Connection conn) {
        this.conn = conn;
    }

    public int addBuilding(Building b) throws SQLException {
        return addBuilding(b.getAddress(), b.getCadastral(), b.getBuiltYear(), b.getArea(), b.getZipcode(), b.getCity(),
                b.getCondition(), b.getService(), b.getExtraText(), b.getDateCreated(), b.getFk_idUser());
    }

     /**
     * This class creates a object, of type Building. 
     * It afterwards uploads this object to the database accordingly
    @param address Address of the building
    @param cadastral Image of district, of the address
    @param builtYear Year, of making of the building
    @param area District of the building
    @param zipcode Zipcode of the building
    @param city City of the building
    @param conditionText Grade of the building, described by Polygon worker
    @param service Service description
    @param extraText Description text, rather useless
    @param dateCreated Specific date of creation of the server
    @param fk_idUser The user ID, of the user that the building belongs to
     * @return
     * @throws java.sql.SQLException
    
    */
    public int addBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String conditionText,
            String service, String extraText, String dateCreated, int fk_idUser) throws SQLException {

        String sql = "INSERT INTO building (address, cadastral, builtYear, area, zipcode, city, conditionText, service, extraText, dateCreated, fk_idUser)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,(select idUser from user where idUser=?))";
        PreparedStatement prepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        prepared.setString(1, address);
        prepared.setString(2, cadastral);
        prepared.setString(3, builtYear);
        prepared.setString(4, area);
        prepared.setString(5, zipcode);
        prepared.setString(6, city);
        prepared.setString(7, conditionText);
        prepared.setString(8, service);
        prepared.setString(9, extraText);
        prepared.setString(10, dateCreated);
        prepared.setInt(11, fk_idUser);

        prepared.executeUpdate();

        ResultSet myRS = prepared.getGeneratedKeys();
        if (myRS.next()) {
            int buildingId = myRS.getInt(1);
            submitReport(buildingId, "", false, 0, "", false, 0, "", 0, "");
            return buildingId;
        }
        return -1;
    }

    public Building getBuilding(int idBuilding) throws SQLException {
        Building building = null;
        String sql = "SELECT * FROM building WHERE idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idBuilding);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            building = new Building(
                    myRS.getInt("idBuilding"),
                    myRS.getString("address"),
                    myRS.getString("cadastral"),
                    myRS.getString("area"),
                    myRS.getString("zipcode"),
                    myRS.getString("city"),
                    myRS.getString("conditionText"),
                    myRS.getString("service"),
                    myRS.getString("extraText"),
                    myRS.getString("builtYear"),
                    myRS.getInt("fk_idUser"),
                    myRS.getInt("fk_idMainPicture"),
                    myRS.getInt("fk_idReport"),
                    myRS.getString("dateCreated")
            );
        }
        return building;
    }

    public Building getBuilding(String address) throws SQLException {
        Building building = null;
        String sql = "SELECT * FROM building WHERE address=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, address);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            building = new Building(
                    myRS.getInt("idBuilding"),
                    myRS.getString("address"),
                    myRS.getString("cadastral"),
                    myRS.getString("area"),
                    myRS.getString("zipcode"),
                    myRS.getString("city"),
                    myRS.getString("conditionText"),
                    myRS.getString("service"),
                    myRS.getString("extraText"),
                    myRS.getString("builtYear"),
                    myRS.getInt("fk_idUser"),
                    myRS.getInt("fk_idMainPicture"),
                    myRS.getInt("fk_idReport"),
                    myRS.getString("dateCreated")
            );
        }
        return building;
    }

    private int submitReport(int buildingId, String buildingUsage, boolean roofRemarks,
            int fk_idPictureRoof, String roofText, boolean outerWallRemarks, int fk_idPictureOuterRoof, String outerWallText, int fk_idEmployee, String buildingResponsible) throws SQLException {
        String sql = "INSERT INTO report (buildingUsage, roofRemarks, fk_idPictureRoof, roofText, outerWallRemarks, fk_idPictureOuterRoof, outerWallText, fk_idEmployee, buildingResponsible) VALUES "
                    + "(?,?,(select idPicture from picture where idPicture=?),?,?,(select idPicture from picture where idPicture=?),?,(select idUser from user where idUser=?),?)";
        PreparedStatement prepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepared.setString(1, buildingUsage);
        prepared.setInt(2, (roofRemarks ? 1 : 0));
        prepared.setInt(3, fk_idPictureRoof);
        prepared.setString(4, roofText);
        prepared.setBoolean(5, outerWallRemarks);
        prepared.setInt(6, fk_idPictureOuterRoof);
        prepared.setString(7, outerWallText);
        prepared.setInt(8, fk_idEmployee);
        prepared.setString(9, buildingResponsible);
        prepared.executeUpdate();
        ResultSet rs = prepared.getGeneratedKeys();
        if (rs.next()) {
            int reportId = rs.getInt(1);
            insertFkReport(buildingId, reportId);
            return reportId;
        }
        return -1;
    }

    public int getFkIdReport(int BuildingId) throws SQLException {
        String sql = "SELECT fk_idReport from building where idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, BuildingId);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            return myRS.getInt("fk_idReport");
        }
        return -1;
    }

    public Report getReport(int reportId) throws SQLException {
        String sql = "SELECT * FROM report WHERE idReport=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, reportId);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            Report report = new Report(
                    myRS.getInt("idReport"),
                    myRS.getString("buildingUsage"),
                    myRS.getBoolean("roofRemarks"),
                    myRS.getInt("fk_idPictureRoof"),
                    myRS.getString("roofText"),
                    myRS.getBoolean("outerWallRemarks"),
                    myRS.getInt("fk_idPictureOuterRoof"),
                    myRS.getString("outerWallText"),
                    myRS.getInt("fk_idEmployee"),
                    myRS.getString("buildingResponsible")
            );
            return report;
        }
        return null;
    }
    
    public int getReportCount(int idUser) throws SQLException{
        int counter = 0;
        String sql = "select fk_idReport from building join report ON building.fk_idReport = report.idReport " +
                         "where fk_idUser=(select idUser from user where idUser=?) and service=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idUser);
        prepared.setString(2, "reviewed");
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            counter++;
        }
        return counter;
    }

    public Report getReportFromBuildingId(int buildingId) throws SQLException {
        String sql = "SELECT fk_idReport FROM building where idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, buildingId);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            int reportId = myRS.getInt("fk_idReport");
            return getReport(reportId);
        }
        return null;
    }

    private void insertFkReport(int buildingId, int idReport) throws SQLException {
        String sql = "UPDATE building set fk_idReport=? where idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idReport);
        prepared.setInt(2, buildingId);
        prepared.executeUpdate();
    }

    public void editReport(Report report) throws SQLException {
        String sql = "UPDATE report set buildingUsage=?, roofRemarks=?, roofText=?, outerWallRemarks=?, outerWallText=?, fk_idEmployee=?, buildingResponsible=? where idReport=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, report.getBuildingUsage());
        prepared.setBoolean(2, report.getRoofRemarks());
        prepared.setString(3, report.getRoofText());
        prepared.setBoolean(4, report.isOuterWallRemarks());
        prepared.setString(5, report.getOuterWallText());
        prepared.setInt(6, report.getFk_idEmployee());
        prepared.setString(7, report.getBuildingResponsible());
        prepared.setInt(8, report.getIdReport());
        prepared.executeUpdate();
    }

    public void addRoomReport(boolean remarks, boolean damage, String damageDate,
            String damageWhere, String damageHappened,
            String damageRepaired, boolean damageWater, boolean damageRot, boolean damageMold,
            boolean damageFire, String damageOther, boolean wallRemark, String wallText,
            boolean roofRemark, String roofText, boolean floorRemark, String floorText,
            boolean moistureScan, String moistureScanText, String moistureScanMeasured,
            String conclusionText, int idReport) throws SQLException {
        String sql = "INSERT INTO room (remarks, damage, damageDate, damageWhere, damageHappened, damageRepaired, damageWater, damageRot, damageMold, damageFire, damageOther, wallRemark, wallText, roofRemark, roofText, floorRemark, floorText, moistureScan, moistureScanText, moistureScanMeasured, conclusionText, fk_idReport) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,(select idReport from report where idReport=?))";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setBoolean(1, remarks);
        prepared.setBoolean(2, damage);
        prepared.setString(3, damageDate);
        prepared.setString(4, damageWhere);
        prepared.setString(5, damageHappened);
        prepared.setString(6, damageRepaired);
        prepared.setBoolean(7, damageWater);
        prepared.setBoolean(8, damageRot);
        prepared.setBoolean(9, damageMold);
        prepared.setBoolean(10, damageFire);
        prepared.setString(11, damageOther);
        prepared.setBoolean(12, wallRemark);
        prepared.setString(13, wallText);
        prepared.setBoolean(14, roofRemark);
        prepared.setString(15, roofText);
        prepared.setBoolean(16, floorRemark);
        prepared.setString(17, floorText);
        prepared.setBoolean(18, moistureScan);
        prepared.setString(19, moistureScanText);
        prepared.setString(20, moistureScanMeasured);
        prepared.setString(21, conclusionText);
        prepared.setInt(22, idReport);
        prepared.executeUpdate();
    }

    public int countRooms(int idReport) throws SQLException {
        int counter = 0;
        String sql = "SELECT * FROM room where fk_idReport=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idReport);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            counter++;
        }
        return counter;
    }

    public void removeReport(int reportId) throws SQLException {
        String sql = "DELETE FROM report where idReport=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, reportId);
        prepared.executeUpdate();
    }

    public void requestService(int id) throws SQLException {
        String sql = "UPDATE building set service=? where idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "awaiting");
        prepared.setInt(2, id);
        prepared.executeUpdate();
    }

    public void setReviewed(int idBuilding) throws SQLException {
        String sql = "UPDATE building set service=? where idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "reviewed");
        prepared.setInt(2, idBuilding);
        prepared.executeUpdate();
    }

    public void editBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String condition,
            String extraText, int idBuilding) throws SQLException {
        String sql = "UPDATE building set address=?, cadastral=?, builtYear=?, area=?, zipcode=?, city=?, conditionText=?, extraText=? WHERE idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, address);
        prepared.setString(2, cadastral);
        prepared.setString(3, builtYear);
        prepared.setString(4, area);
        prepared.setString(5, zipcode);
        prepared.setString(6, city);
        prepared.setString(7, condition);
        prepared.setString(8, extraText);
        prepared.setInt(9, idBuilding);
        prepared.executeUpdate();
    }

    public int getBuildingCount(int idUser) throws SQLException {
        int count = 0;
        String sql = "SELECT idBuilding FROM building WHERE fk_idUser=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idUser);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            count++;
        }
        return count;
    }

    public int getBuildingCount() throws SQLException {
        int count = 0;
        String sql = "SELECT idBuilding FROM building";
        PreparedStatement prepared = conn.prepareStatement(sql);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            count++;
        }
        return count;
    }

    public int getAwaitingService() throws SQLException {
        int count = 0;
        String sql = "SELECT * FROM building WHERE service=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "awaiting");
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            count++;
        }
        return count;
    }

    public int getEmployeeFromIdBuilding(int idBuilding) throws SQLException {
        String sql = "SELECT fk_idEmployee FROM report WHERE idReport=(select fk_idReport from building where idBuilding=?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idBuilding);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            return myRS.getInt("fk_idEmployee");
        }
        return -1;
    }

    public int getAwaitingReview(int id) throws SQLException {
        int counter = 0;
        String sql = "SELECT * FROM building WHERE service=? AND fk_idUser=(select from user where idUser=?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "reviewed");
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            counter++;
        }
        return counter;
    }

    public void removeBuilding(int buildingId) throws SQLException {
        String sql = "DELETE FROM building WHERE idBuilding=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, buildingId);
        removeReport(getFkIdReport(buildingId));
        prepared.executeUpdate();
    }

    public void removePicture(int pictureId) throws SQLException {
        String sql = "DELETE FROM picture WHERE idPicture=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, pictureId);
        prepared.executeUpdate();
    }

    public Connection getConn() {
        return conn;
    }

}