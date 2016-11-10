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

    public DBBuildingHandler() {
        this.conn = DBConnection.getConnection();
    }

    public DBBuildingHandler(Connection conn) {
        this.conn = conn;
    }

    public int addBuilding(Building b) {
        return addBuilding(b.getAddress(), b.getCadastral(), b.getBuiltYear(), b.getArea(), b.getZipcode(), b.getCity(),
                b.getCondition(), b.getService(), b.getExtraText(), b.getDateCreated(), b.getFk_idUser());
    }

    public int addBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String conditionText,
            String service, String extraText, String dateCreated, int fk_idUser) {

        try {
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
            if(myRS.next()){
                int buildingId = myRS.getInt(1);
                submitReport(buildingId, "", false, 0, "", false, 0, "", 0, "");
                return buildingId;
            }
            
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "[DBBuiling.addBuilding] " + ex);
        }
        return -1;
    }

    public String getBuildings(int idUser) {
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>Thumbnail</th>\n"
                + "        <th>Address</th>\n"
                + "        <th>Zipcode</th>\n"
                + "        <th>City</th>\n"
                + "        <th>Edit Building</th>\n"
                + "        <th>Remove Building</th>\n"
                + "        <th>Service Building</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        try {
            /*
    int idBuilding, String address, String cadastral, String buildingGrade, 
    String area, String zipcode, String city, String condition, String service, 
    String extraText, double buildingArea, int builtYear, int fk_idUser, 
    int fk_MainPicture, int fk_idReport, Date dateCreated
             */

            String sql = "SELECT * FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idUser);
            ResultSet myRS = prepared.executeQuery();
            int id = 1;
            while (myRS.next()) {
                int idBuilding = myRS.getInt("idBuilding");
                int imageID = myRS.getInt("fk_idMainPicture");
                String address = myRS.getString("address");
                String city = myRS.getString("city");
                String zipcode = myRS.getString("zipcode");
                tableData += "<tr><td>" + ImageHandler.getImageHTML(imageID, 50, 50)
                        + "<td>" + address + "</td><td>" + zipcode + "</td><td>" + city
                        + "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='editBuilding'><td><button class='editbtn' type='submit'>Show/Edit</button></td>"
                        + "<input type='hidden' name='idBuilding' value='" + idBuilding + "'></td>"
                        + "</form>"
                        + "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='deleteBuilding'><td><button class='dltbtn' type='submit'>Delete</button></td>"
                        + "<input type='hidden' name='idBuilding' value='" + idBuilding + "'></td>"
                        + "</form>"
                        + "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='getService'><td><button class='srvbtn' type='submit'>Service</button></td>"
                        + "<input type='hidden' name='idBuilding' value='" + idBuilding + "'></td>"
                        + "</form>"
                        + "</tr>";
                id++;
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public Building getBuilding(int idBuilding) {
        Building building = null;
        try {
            String sql = "SELECT * FROM building WHERE idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idBuilding);
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

    private int submitReport(int buildingId ,String buildingUsage, boolean roofRemarks,
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
    
    public int getFkIdReport(int BuildingId){
        try {
            String sql = "SELECT fk_idReport from building where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, BuildingId);
            ResultSet myRS = prepared.executeQuery();
            if(myRS.next()) {
              return myRS.getInt("fk_idReport");
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public Report getReport(int reportId){
        try{
            String sql = "SELECT * FROM report WHERE idReport=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, reportId);
            ResultSet myRS = prepared.executeQuery();
            if(myRS.next()) {
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
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Report getReportFromBuildingId(int buildingId){
        String sql = "SELECT fk_idReport FROM building where idBuilding=?";
        try{
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, buildingId);
            ResultSet myRS = prepared.executeQuery();
            if(myRS.next()){
                int reportId = myRS.getInt("fk_idReport");
                return getReport(reportId);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private void insertFkReport(int buildingId, int idReport){
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
    
    public void addRoomReport(boolean remarks, boolean damage, String damageDate, 
            String damageWhere, String damageHappened, 
            String damageRepaired, boolean damageWater, boolean damageRot, boolean damageMold, 
            boolean damageFire, String damageOther, boolean wallRemark, String wallText, 
            boolean roofRemark, String roofText, boolean floorRemark, String floorText, 
            boolean moistureScan, String moistureScanText, String moistureScanMeasured, 
            String conclusionText, int idReport){
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
    
    private void removeReport(int reportId){
        String sql = "DELETE FROM report where idReport=?";
        try{
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, reportId);
            prepared.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String getReportOverview(int id){
        String data = "";
        try {
            String sql = "SELECT * from report where idReport=(select fk_idReport from building where idBuilding=?)";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet myRS = prepared.executeQuery();
            if(myRS.next()) {
              data += "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='overviewReport'><input type='hidden' name='reportID' value=' " + myRS.getInt("idReport") + "'>";
              data += "<h3 style='color: white;'>Report ID: " + myRS.getInt("idReport");
              data += "<br /><br />Roof Damage: " + myRS.getBoolean("roofRemarks");
              data += "<br /><br />Outer Wall Damage: " + myRS.getBoolean("outerWallRemarks");
              data += "<br /><br />Worker Responsible: " + myRS.getInt("fk_idEmployee") + "</h3>";
              data += "<button style='padding: 15px; width: 150px; border: 0px solid black; border-radius: 3px;' type='submit'>Review Report</button></form>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return data;
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

    public void unrequestService(int id) {
        try {
            String sql = "UPDATE building set service=? where idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "reviewed");
            prepared.setInt(2, id);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public String getRooms(int id) {
        String roomData = "";
        try {
            String sql = "SELECT idRoomBuilding, roomDescribtion from roomBuilding where fk_idBuilding=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                int roomID = myRS.getInt("idRoomBuilding");
                String roomDesc = myRS.getString("roomDescribtion");

                roomData += "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='serviceRoom'><h4 style='color: white;'>" + roomDesc + "</h4><br><p style='color:white;'><button class='.btn-success' type='submit'>Service Room</button>"
                        + "<input type='hidden' name='idRoom' value='" + roomID + "'></td>"
                        + "</form>"
                        + "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='editRoom'><p style='color:white;'><button class='.btn-success' type='submit'>Edit Room</button>"
                        + "<input type='hidden' name='idRoom' value='" + roomID + "'></td>"
                        + "</form><br>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return roomData;
    }

    public String showReport(int id){
        String tableData = "";
        try {
            String sql = "SELECT * from room where fk_idReport=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
            tableData += "<center><h3 style='color: white;'><br / ><br / >";
            tableData += "Remarks: " + myRS.getBoolean("remarks");
            tableData += "Damage: " +myRS.getBoolean("damage");
            tableData += "Date of Damage: " +myRS.getString("damageDate");
            tableData += "Damage Location: " +myRS.getString("damageWhere");
            tableData += "Reason behind the damage: " +myRS.getString("damageHappened");
            tableData += "Damage Repaired: " +myRS.getString("damageRepaired");
            tableData += "Damage Type: " +myRS.getBoolean("damageWater");
            tableData += "Damage Type: " +myRS.getBoolean("damageRot");
            tableData += "Damage Type: " +myRS.getBoolean("damageMold");
            tableData += "Damage Type: " +myRS.getBoolean("damageFire");
            tableData += "Damage Type: " +myRS.getString("damageOther");
            tableData += "Wall Remarks: " +myRS.getBoolean("wallRemark");
            tableData += "Wall Text: " +myRS.getString("wallText");
            tableData += "Roof Remarks: " +myRS.getBoolean("roofRemark");
            tableData += "Roof Text: " +myRS.getString("roofText");
            tableData += "Floor Remarks: " +myRS.getBoolean("floorRemark");
            tableData += "Floor Text: " +myRS.getString("floorText");
            tableData += "Moisture Scan Performed: " +myRS.getBoolean("moistureScan");
            tableData += "Moisture Scan Description: " +myRS.getString("moistureScanText");
            tableData += "Moisture Scan Measurements: " +myRS.getString("moistureScanMeasured");
            tableData += "Conclusion: " +myRS.getString("conclusionText");
            tableData += "Report ID: " +myRS.getInt("fk_idReport");
            tableData += "</center></h3>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        return tableData;
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

    public String getService(int idUser) {
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>Address</th>\n"
                + "        <th>City</th>\n"
                + "        <th>Building Year</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        try {
            String sql = "SELECT idBuilding, address, cadastral, builtYear, area, zipcode, city, condition, service, extraText, dateCreated, fk_idUser, fk_idMainPicture, fk_idReport FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, idUser);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                int idBuilding = myRS.getInt("idBuilding");
                String address = myRS.getString("address");
                String city = myRS.getString("city");
                int builtYear = myRS.getInt("builtYear");

                tableData += "<tr><form method ='POST' action='Front'><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "<input type='hidden' name='methodForm' value='serviceBuilding'><input type='hidden' name='idBuilding' value='" + idBuilding + "'></td></tr>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public void submitRoom() {

    }

    public String createMenu(String status) {
        DBUserHandler db = new DBUserHandler(conn);
        String menu = "";
         if (status == null) {
            return "Unspecified Login. Please retry!";
         } else if (status.equalsIgnoreCase("customer")) {
            return "<li><a href='overviewBuilding.jsp'>Estate</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='account.jsp'>Account Management</a></li>\n"
                    + "            <li><a href='report.jsp'>Reports(" + this.getAwaitingReview(0) + ")</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>";
        } else if (status.equalsIgnoreCase("worker")) {
            return "<li><a href='overviewBuilding.jsp'>Estates</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='overviewUsers.jsp'>Account Management(" + db.countUnConfirmed() + ")</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>"
                    + "            <li><a href='overviewAwaitingService.jsp'>Awaiting Service(" + this.getAwaitingService() + ")</a></li>";
        }
        return "";
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

    
    public String printAwaitingService() {
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>Address</th>\n"
                + "        <th>City</th>\n"
                + "        <th>Building Year</th>\n"
                + "        <th>User</th>\n"
                + "        <th>Edit Report</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        try {
            String sql = "SELECT * FROM building WHERE service=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "awaiting");
            ResultSet myRS = prepared.executeQuery();
            DBUserHandler db = new DBUserHandler();
            while (myRS.next()) {
                tableData += "<tr><form method ='POST' action='Front'><td>" + myRS.getString("address") + "</td><td>" + myRS.getString("city") + "</td><td>" + myRS.getString("builtYear") + "</td><td>" + db.getUserFromDB(myRS.getInt("fk_idUser")) + "</td><td><button class='addSrvBtn' type='submit'>View</button><input type='hidden' name='methodForm' value='addReport'><input type='hidden' name='idBuilding' value='" + myRS.getInt("idBuilding") + "'></td></form>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }

        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    
    public String printAwaitingReview(int id) {
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>Address</th>\n"
                + "        <th>City</th>\n"
                + "        <th>Building Year</th>\n"
                + "        <th>User</th>\n"
                + "        <th>Review Report</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        try {
            String sql = "SELECT * FROM building WHERE service=? AND fk_idUser=(select from user where idUser=?)";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "reviewed");
            ResultSet myRS = prepared.executeQuery();
            DBUserHandler db = new DBUserHandler();
            while (myRS.next()) {
                tableData += "<tr><form method ='POST' action='Front'><td>" + myRS.getString("address") + "</td><td>" + myRS.getString("city") + "</td><td>" + myRS.getString("builtYear") + "</td><td>" + db.getUserFromDB(myRS.getInt("fk_idUser")) + "</td><td><button class='addSrvBtn' type='submit'>View</button><input type='hidden' name='methodForm' value='viewReport'><input type='hidden' name='idBuilding' value='" + myRS.getInt("idBuilding") + "'></td></form>";
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
        }

        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
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
            removeReport(getReportFromBuildingId(buildingId).getIdReport());
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removePicture(int pictureId){
        try{
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