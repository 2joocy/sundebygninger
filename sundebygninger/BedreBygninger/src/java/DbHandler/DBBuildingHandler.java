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

    public Building getBuilding(int idBuilding) {
        Building building = null;
        try {
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

        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error [getBuilding(id)]");
            ex.printStackTrace();
        }
        return building;
    }

    public Building getBuilding(String address) {
        Building building = null;
        try {
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

        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return building;
    }

    private int submitReport(int buildingId, String buildingUsage, boolean roofRemarks,
            int fk_idPictureRoof, String roofText, boolean outerWallRemarks, int fk_idPictureOuterRoof, String outerWallText, int fk_idEmployee, String buildingResponsible) {
        try {
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
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public int getFkIdReport(int BuildingId) {
        try {
            String sql = "SELECT fk_idReport from building where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, BuildingId);
            ResultSet myRS = prepared.executeQuery();
            if (myRS.next()) {
                return myRS.getInt("fk_idReport");
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public Report getReport(int reportId) {
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public int getReportCount(int idUser){
        int counter = 0;
        try {
            String sql = "select fk_idReport from building join report ON building.fk_idReport = report.idReport " +
                         "where fk_idUser=(select idUser from user where idUser=?) and service=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idUser);
            prepared.setString(2, "reviewed");
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                counter++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return counter;
    }

    public Report getReportFromBuildingId(int buildingId) {
        String sql = "SELECT fk_idReport FROM building where idBuilding=?";
        try {
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, buildingId);
            ResultSet myRS = prepared.executeQuery();
            if (myRS.next()) {
                int reportId = myRS.getInt("fk_idReport");
                return getReport(reportId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void insertFkReport(int buildingId, int idReport) {
        try {
            String sql = "UPDATE building set fk_idReport=? where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idReport);
            prepared.setInt(2, buildingId);
            prepared.executeUpdate();

        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public void editReport(Report report) {

        try {
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

        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void addRoomReport(boolean remarks, boolean damage, String damageDate,
            String damageWhere, String damageHappened,
            String damageRepaired, boolean damageWater, boolean damageRot, boolean damageMold,
            boolean damageFire, String damageOther, boolean wallRemark, String wallText,
            boolean roofRemark, String roofText, boolean floorRemark, String floorText,
            boolean moistureScan, String moistureScanText, String moistureScanMeasured,
            String conclusionText, int idReport) {
        try {
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

        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public int countRooms(int idReport) {
        int counter = 0;
        try {
            String sql = "SELECT * FROM room where fk_idReport=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idReport);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                counter++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return counter;
    }

    public void removeReport(int reportId) {
        String sql = "DELETE FROM report where idReport=?";
        try {
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, reportId);
            prepared.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void requestService(int id) {
        try {
            String sql = "UPDATE building set service=? where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "awaiting");
            prepared.setInt(2, id);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public void setReviewed(int idBuilding) {
        try {
            String sql = "UPDATE building set service=? where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "reviewed");
            prepared.setInt(2, idBuilding);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public void editBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String condition,
            String extraText, int idBuilding) {
        try {
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

        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public int getBuildingCount(int idUser) {
        int count = 0;
        try {
            String sql = "SELECT idBuilding FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idUser);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int getBuildingCount() {
        int count = 0;
        try {
            String sql = "SELECT idBuilding FROM building";
            PreparedStatement prepared = conn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int getAwaitingService() {
        int count = 0;
        try {
            String sql = "SELECT * FROM building WHERE service=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "awaiting");
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                count++;
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int getEmployeeFromIdBuilding(int idBuilding) {
        try {
            String sql = "SELECT fk_idEmployee FROM report WHERE idReport=(select fk_idReport from building where idBuilding=?)";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idBuilding);
            ResultSet myRS = prepared.executeQuery();
            if (myRS.next()) {
                return myRS.getInt("fk_idEmployee");
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return -1;
    }

    public int getAwaitingReview(int id) {
        int counter = 0;
        try {
            String sql = "SELECT * FROM building WHERE service=? AND fk_idUser=(select from user where idUser=?)";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "reviewed");
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                counter++;
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }

        return counter;
    }

    public void removeBuilding(int buildingId) {
        try {
            String sql = "DELETE FROM building WHERE idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, buildingId);
            removeReport(getFkIdReport(buildingId));
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePicture(int pictureId) {
        try {
            String sql = "DELETE FROM picture WHERE idPicture=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, pictureId);
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

}