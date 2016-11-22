/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import exceptions.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CHRIS
 */
public class DBHTMLPresenter {
    
    Connection conn;
    
    public DBHTMLPresenter() throws ClassNotFoundException, SQLException {
        conn = DBConnection.getConnection();
    }
    
    public DBHTMLPresenter(Connection conn) {
        this.conn = conn;
    }
    
    public String getBuildings(int idUser) throws SQLException, DatabaseConnectionException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
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
            tableData += "<tr><td>" + getImageHTML(imageID, 50, 50)
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
            /*
    int idBuilding, String address, String cadastral, String buildingGrade, 
    String area, String zipcode, String city, String condition, String service, 
    String extraText, double buildingArea, int builtYear, int fk_idUser, 
    int fk_MainPicture, int fk_idReport, Date dateCreated
             */
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    
    public String getReportOverview(int idBuilding) throws DatabaseConnectionException, SQLException {
        String data = "";
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String sql = "SELECT * from report where idReport=(select fk_idReport from building where idBuilding=?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idBuilding);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            data += "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='overviewReport'><input type='hidden' name='idReport' value=' " + myRS.getInt("idReport") + "'><input type='hidden' name='idBuilding' value=' " + idBuilding + "'>";
            data += "<h3 style='color: white;'>Report ID: " + myRS.getInt("idReport");
            data += "<br /><br />Roof Damage: " + myRS.getBoolean("roofRemarks");
            data += "<br /><br />Outer Wall Damage: " + myRS.getBoolean("outerWallRemarks");
            data += "<br /><br />Worker Responsible: " + myRS.getInt("fk_idEmployee") + "</h3>";
            data += "<br /><button style='padding: 15px; width: 150px; border: 0px solid black; border-radius: 3px;' type='submit'>Review Report</button></form>";
        }
        return data;
    }

    public String getReportOverviewWithReportID(int idReport) throws DatabaseConnectionException, SQLException {
        String data = "";
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String sql = "SELECT * from report where idReport=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, idReport);
        ResultSet myRS = prepared.executeQuery();
        if (myRS.next()) {
            data += "<h3 style='color: white;'>Report ID: " + myRS.getInt("idReport");
            data += "<br /><br />Roof Damage: " + myRS.getBoolean("roofRemarks");
            data += "<br /><br />Outer Wall Damage: " + myRS.getBoolean("outerWallRemarks");
            data += "<br /><br />Worker Responsible: " + myRS.getInt("fk_idEmployee") + "</h3>";
        }
        return data;
    }

    
    public String getRooms(int id) throws DatabaseConnectionException, SQLException {
        String roomData = "";
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
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
        return roomData;
    }

    public String showRoomReport(int id) throws SQLException, DatabaseConnectionException {
        String tableData = "";
        int counter = 0;
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String sql = "SELECT * from room where fk_idReport=(select idReport from report where idReport=?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setInt(1, id);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            counter++;
            tableData += "<center><div class='roomReport'><h2 style='color: white;'>Room Report: " + counter + "</h2><h3 style='color: white;'><br / >" + "<br / >";
            tableData += "Remarks: " + myRS.getBoolean("remarks") + "<br / >" + "<br / >";
            tableData += "Damage: " + myRS.getBoolean("damage") + "<br / >" + "<br / >";
            tableData += "Date of Damage: " + myRS.getString("damageDate") + "<br / >" + "<br / >";
            tableData += "Damage Location: " + myRS.getString("damageWhere") + "<br / >" + "<br / >";
            tableData += "Reason behind the damage: " + myRS.getString("damageHappened") + "<br / >" + "<br / >";
            tableData += "Damage Repaired: " + myRS.getString("damageRepaired") + "<br / >" + "<br / >";
            tableData += "Damage Type: " + myRS.getBoolean("damageWater") + "<br / >" + "<br / >";
            tableData += "Damage Type: " + myRS.getBoolean("damageRot") + "<br / >" + "<br / >";
            tableData += "Damage Type: " + myRS.getBoolean("damageMold") + "<br / >" + "<br / >";
            tableData += "Damage Type: " + myRS.getBoolean("damageFire") + "<br / >" + "<br / >";
            tableData += "Damage Type: " + myRS.getString("damageOther") + "<br / >" + "<br / >";
            tableData += "Wall Remarks: " + myRS.getBoolean("wallRemark") + "<br / >" + "<br / >";
            tableData += "Wall Text: " + myRS.getString("wallText") + "<br / >" + "<br / >";
            tableData += "Roof Remarks: " + myRS.getBoolean("roofRemark") + "<br / >" + "<br / >";
            tableData += "Roof Text: " + myRS.getString("roofText") + "<br / >" + "<br / >";
            tableData += "Floor Remarks: " + myRS.getBoolean("floorRemark") + "<br / >" + "<br / >";
            tableData += "Floor Text: " + myRS.getString("floorText") + "<br / >" + "<br / >";
            tableData += "Moisture Scan Performed: " + myRS.getBoolean("moistureScan") + "<br / >" + "<br / >";
            tableData += "Moisture Scan Description: " + myRS.getString("moistureScanText") + "<br / >" + "<br / >";
            tableData += "Moisture Scan Measurements: " + myRS.getString("moistureScanMeasured") + "<br / >" + "<br / >";
            tableData += "Conclusion: " + myRS.getString("conclusionText") + "<br / >" + "<br / >";
            tableData += "Report ID: " + myRS.getInt("fk_idReport") + "<br / >" + "<br / >";
            tableData += "</h3></div></center>" + "<br / >" + "<br / >" + "<br / >" + "<br / >";
        }
        return tableData;
    }

    public String getService(int idUser) throws SQLException, DatabaseConnectionException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String tableData = "<table class='table table-hover'>\n"
            + "    <thead>\n"
            + "      <tr>\n"
            + "        <th>Address</th>\n"
            + "        <th>City</th>\n"
            + "        <th>Building Year</th>\n"
            + "      </tr>\n"
            + "    </thead>\n"
            + "    <tbody>";
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
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String createMenu(DBBuildingHandler dbb, DBUserHandler dbu, String status) throws DatabaseConnectionException {
        String menu = "";
        if (status == null) {
            return "Unspecified Login. Please retry!";
        } else if (status.equalsIgnoreCase("customer")) {
            return "<li><a href='overviewBuilding.jsp'>Estate</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='account.jsp'>Account Management</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>";
        } else if (status.equalsIgnoreCase("worker")) {
            return "<li><a href='overviewBuilding.jsp'>Estates</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='overviewUsers.jsp'>Account Management(" + dbu.countUnConfirmed() + ")</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>"
                    + "            <li><a href='overviewAwaitingService.jsp'>Awaiting Service(" + dbb.getAwaitingService() + ")</a></li>";
        }
        return "";
    }

    public String getAwaitingServiceCustomer(DBBuildingHandler dbb, String parameter, int id, String userRole) throws DatabaseConnectionException, SQLException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>Building ID</th>\n"
                + "        <th>Address</th>\n"
                + "        <th>City</th>\n"
                + "        <th>Building Year</th>\n"
                + "        <th>Worker Responsible</th>\n"
                + "        <th>Review Service</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        
        String sql = "SELECT * FROM building where service=? AND fk_idUser=(select idUser from user where idUser=?)";
        if (userRole.equals("worker")) {
            sql = "SELECT * FROM building where service=?";
        }
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, parameter);
        if (!userRole.equals("worker")) {
            prepared.setInt(2, id);
        }
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            int idBuilding = myRS.getInt("idBuilding");
            int idEmployee = dbb.getEmployeeFromIdBuilding(idBuilding);
            int idReport = dbb.getFkIdReport(idBuilding);
            String address = myRS.getString("address");
            String city = myRS.getString("city");
            String builtYear = myRS.getString("builtYear");
            if (parameter.equalsIgnoreCase("reviewed")) {
                tableData += "<form method ='POST' action='Front'><input type='hidden' name='methodForm' value='reviewReviewedService' /><input type='hidden' name='idBuilding' value='" + idBuilding + "' /><input type='hidden' name='idReport' value='" + idReport + "' /><td>" + idBuilding + "</td><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "</td><td>" + idEmployee + "</td><td><button type='submit' class='srvbtn' style='padding: 10px;'>Review Service</button></td></tr></form>";
            } else if (parameter.equalsIgnoreCase("awaiting")) {
                tableData += "<tr><td>" + idBuilding + "</td><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "</td></tr>";
            } else if (parameter.equalsIgnoreCase("finished")) {
                tableData += "<form method ='POST' action='Front'><input type='hidden' name='methodForm' value='reviewReviewedService' /><input type='hidden' name='idBuilding' value='" + idBuilding + "' /><input type='hidden' name='idReport' value='" + idReport + "' /><td>" + idBuilding + "</td><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "</td><td>" + idEmployee + "</td><td><button type='submit' class='srvbtn' style='padding: 10px;'>Review Service</button></td></tr></form>";
            } else if (parameter.equalsIgnoreCase("denied")) {
                tableData += "<tr><td>" + idBuilding + "</td><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "</td></tr>";
            }

        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String printAwaitingService() throws DatabaseConnectionException, SQLException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
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
        String sql = "SELECT * FROM building WHERE service=?";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "awaiting");
        ResultSet myRS = prepared.executeQuery();
        DBUserHandler db = new DBUserHandler();
        while (myRS.next()) {
            tableData += "<tr><form method ='POST' action='Front'><td>" + myRS.getString("address") + "</td><td>" + myRS.getString("city") + "</td><td>" + myRS.getString("builtYear") + "</td><td>" + db.getUserFromDB(myRS.getInt("fk_idUser")) + "</td><td><button class='addSrvBtn' type='submit'>View</button><input type='hidden' name='methodForm' value='addReport'><input type='hidden' name='idBuilding' value='" + myRS.getInt("idBuilding") + "'></td></form>";
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String printAwaitingReview(int id) throws DatabaseConnectionException, SQLException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
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
        String sql = "SELECT * FROM building WHERE service=? AND fk_idUser=(select from user where idUser=?)";
        PreparedStatement prepared = conn.prepareStatement(sql);
        prepared.setString(1, "reviewed");
        ResultSet myRS = prepared.executeQuery();
        DBUserHandler db = new DBUserHandler();
        while (myRS.next()) {
            tableData += "<tr><form method ='POST' action='Front'><td>" + myRS.getString("address") + "</td><td>" + myRS.getString("city") + "</td><td>" + myRS.getString("builtYear") + "</td><td>" + db.getUserFromDB(myRS.getInt("fk_idUser")) + "</td><td><button class='addSrvBtn' type='submit'>View</button><input type='hidden' name='methodForm' value='viewReport'><input type='hidden' name='idBuilding' value='" + myRS.getInt("idBuilding") + "'></td></form>";
        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }
    
    public String getUnConfirmed() throws DatabaseConnectionException, SQLException {
        if (conn == null) {
            throw new DatabaseConnectionException("Connection to DB is undefined.");
        }
        String tableData = "<table class='table table-hover'>\n"
                + "    <thead>\n"
                + "      <tr>\n"
                + "        <th>ID</th>\n"
                + "        <th>Email</th>\n"
                + "        <th>Business Name</th>\n"
                + "         <th>Confirm User</th>\n"
                + "         <th>Deny User</th>\n"
                + "      </tr>\n"
                + "    </thead>\n"
                + "    <tbody>";
        String sql = "SELECT idUser, email, businessName FROM user WHERE status='not'";
        //System.out.println(sql);
        PreparedStatement prepared = conn.prepareStatement(sql);
        ResultSet myRS = prepared.executeQuery();
        while (myRS.next()) {
            String email = myRS.getString("email");
            String businessName = myRS.getString("businessName");
            int id = myRS.getInt("idUser");

            tableData += "<tr><form method ='POST' action='Front'><td>" + id 
                    + "</td><td>" + email + "</td><td>" + businessName + 
                    "<input type='hidden' name='methodForm' value='confirmUsers'><input type='hidden' name='userID' value='" 
                    + id + "'></td><td><button type='submit'>Confirm User</button></td></form><td>"
                    + "<form method ='POST' action='Front'><input type='hidden' name='methodForm' value='denyUsers'>"
                    + "<input type='hidden' name='userID' value='" + id + "'><button type='submit'>Deny User</button></form></td></tr>";

        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String getImageHTML(int id) {
        return "<img src=\"ImageServlet?id=" + id + "\"/>";
    }

    public String getImageHTML(int id, int width, int height) {
        return "<img src=\"ImageServlet?id=" + id + "\" height=\"" + height + "\" width=\"" + width + "\"/>";
    }

}