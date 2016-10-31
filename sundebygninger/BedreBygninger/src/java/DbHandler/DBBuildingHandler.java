/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.Building;
import entities.User;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

public class DBBuildingHandler {

    public void addBuilding(Building b) {
        addBuilding(b.getAddress(), b.getCadastral(), b.getBuiltYear(), b.getArea(), b.getZipcode(), b.getCity(), 
                    b.getCondition(), b.getService(), b.getExtraText(), b.getDateCreated(), b.getFk_idUser(), b.getFk_idMainPicture(), b.getFk_idReport());
    }
    
    public void addBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String conditionText,
            String service, String extraText, String dateCreated, int fk_idUser,
            int fk_idMainPicture, int fk_idReport) {

        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "INSERT INTO building (address, cadastral, builtYear, area, zipcode, city, conditionText, service, extraText, dateCreated, fk_idUser, fk_idMainPicture, fk_idReport)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,(select idUser from user where idUser=?),null,null)";
            PreparedStatement prepared = myConn.prepareStatement(sql);

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
            //prepared.setInt(12, fk_idMainPicture);
            //prepared.setInt(13, fk_idReport);

            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "[DBBuiling.addBuilding] " + ex);
        }

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

            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT * FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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
        } catch (SQLException | HeadlessException ex) {

        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public Building getBuilding(int idUser) {
        Building building = null;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT * FROM building WHERE idBuilding=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, idUser);
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

        }
        return building;
    }

    public Building getBuilding(String address) {
        Building building = null;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT * FROM building WHERE address=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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

        }
        return building;
    }

    public void submitReport(String buildingUsage, boolean roofRemarks,
            int fk_idPictureRoof, String roofText, boolean outerWallRemarks, int fk_idPictureOuterRoof, String outerWallText, int fk_idEmployee, String buildingResponsible) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "INSERT INTO report (buildingUsage, roofRemarks, fk_idPictureRoof, roofText, outerWallRemarks, fk_idPictureOuterRoof, outerWallText, fk_idEmployee, buildingResponsible) VALUES "
                    + "(?,?,(select idPicture from picture where idPicture=?),?,?,(select idPicture from picture where idPicture=?),?,(select idUser from user where idUser=?),?)";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "[DBBuilding.submitReport] " + ex);
        }
    }

    public String getRooms(int id) {
        String roomData = "";
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idRoomBuilding, roomDescribtion from roomBuilding where fk_idBuilding=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
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

        }
        return roomData;
    }

    public void editBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String condition,
            String extraText, int userID) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "UPDATE building set address=?, cadastral=?, builtYear=?, area=?, zipcode=?, city=?, conditionText=?, extraText=? WHERE idBuilding=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setString(1, address);
            prepared.setString(2, cadastral);
            prepared.setString(3, builtYear);
            prepared.setString(4, area);
            prepared.setString(5, zipcode);
            prepared.setString(6, city);
            prepared.setString(7, condition);
            prepared.setString(8, extraText);
            prepared.setInt(9, userID);
            prepared.executeUpdate();

        } catch (SQLException | HeadlessException ex) {

        }
    }

    public int getBuildingCount(int id) {
        int count = 0;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idBuilding FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet myRS = prepared.executeQuery();
            while(myRS.next()){
                count++;
            }
        } catch (SQLException | HeadlessException ex) {

        }
        return count;
    }

    public int getBuildingCount() {
        int count = 0;
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idBuilding FROM building";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet myRS = prepared.executeQuery();
            while(myRS.next()){
                count++;
            }
        } catch (SQLException | HeadlessException ex) {

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
            /*
    int idBuilding, String address, String cadastral, String buildingGrade, 
    String area, String zipcode, String city, String condition, String service, 
    String extraText, double buildingArea, int builtYear, int fk_idUser, 
    int fk_MainPicture, int fk_idReport, Date dateCreated
             */

            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT idBuilding, address, cadastral, builtYear, area, zipcode, city, condition, service, extraText, dateCreated, fk_idUser, fk_idMainPicture, fk_idReport FROM building WHERE fk_idUser=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, idUser);
            ResultSet myRS = prepared.executeQuery();
            while (myRS.next()) {
                int idBuilding = myRS.getInt("idBuilding");
                String address = myRS.getString("address");
                String city = myRS.getString("city");
                int builtYear = myRS.getInt("builtYear");

                tableData += "<tr><form method ='POST' action='Front'><td>" + address + "</td><td>" + city + "</td><td>" + builtYear + "<input type='hidden' name='methodForm' value='serviceBuilding'><input type='hidden' name='idBuilding' value='" + idBuilding + "'></td></tr>";

//                Building building = new Building(myRS.getInt("idBuilding"), 
//                                                 myRS.getString("address"),
//                                                 myRS.getString("cadastral"),
//                                                 myRS.getString("buildingGrade"),
//                                                 myRS.getString("area"),
//                                                 myRS.getString("zipcode"),
//                                                 myRS.getString("city"),
//                                                 myRS.getString("condition"),
//                                                 myRS.getString("service"),
//                                                 myRS.getString("extraText"),
//                                                 myRS.getDouble("buildingArea"),
//                                                 myRS.getInt("builtYear"),
//                                                 myRS.getInt("fk_idUser"),
//                                                 myRS.getInt("fk_MainPicture"),
//                                                 myRS.getInt("fk_idReport"),
//                                                 myRS.getDate("dateCreated")
//                                                );
//                buildingList.add(building);
            }
        } catch (SQLException | HeadlessException ex) {

        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
    }

    public String createMenu(String status) {
        DBUserHandler db = new DBUserHandler();
        String menu = "";
        if (status.equalsIgnoreCase("customer")) {
            return "<li><a href='overviewBuilding.jsp'>Estate</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='account.jsp'>Account Management</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>";
        } else if (status.equalsIgnoreCase("worker")) {
            return "<li><a href='overviewBuilding.jsp'>Estates</a></li>\n"
                    + "            <li><a href='service.jsp'>Service Overview</a></li>\n"
                    + "            <li><a href='overviewUsers.jsp'>Account Management(" + db.countUnConfirmed() + ")</a></li>\n"
                    + "            <li><a href='contact.jsp'>Contact Staff</a></li>";
        } else if (status == null) {
            return "Unspecified Login. Please retry!";
        }
        return "";
    }

    public String getReportField(int reportID, String fieldName) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "SELECT ? FROM report where ";
            PreparedStatement prepared = myConn.prepareStatement(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeBuilding(int id) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "DELETE FROM building WHERE idBuilding=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, id);
            prepared.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImageHTML(int id) {
        return "<img src=\"ImageServlet?id=" + id + "\"/>";
    }
    
    public String getImageHTML(int id, int width, int height) {
        return "<img src=\"ImageServlet?id=" + id + "\" height=\"" + height + "\" width=\"" + width+ "\"/>";
    }

    public Image getImage() {
        Connection myConn = DBConnection.getConnection();
        String sql = "SELECT * FROM picture WHERE idPicture=?";
        try {
            PreparedStatement prepared = myConn.prepareStatement(sql);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                BufferedImage image = ImageIO.read(inputStream);
                return image;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public int uploadMainImage(String description, String type, Part filePart, int buildingID, int fk_idMainPicture) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "DELETE FROM picture WHERE idPicture=?";
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, fk_idMainPicture);
            prepared.executeUpdate();
            int returnValue = uploadImage(description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE building SET fk_idMainPicture = ? WHERE idBuilding = ?";
                prepared = myConn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, buildingID);
                prepared.executeUpdate();
                return returnValue;
            } else {
                JOptionPane.showMessageDialog(null, "[DBBuildingHandler.uploadMainImage] Error, returnValue: " + returnValue);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[DBBuildingHandler.uploadMainImage] " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    public int uploadImage(String description, String type, Part filePart) {
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "INSERT INTO picture (description, type, image) VALUES (?, ?, ?)";
            PreparedStatement prepared = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InputStream inputStream = null;
            if (filePart != null) {
                inputStream = filePart.getInputStream();
            }
            if (inputStream != null) {
                prepared.setString(1, description);
                prepared.setString(2, type);
                prepared.setBlob(3, inputStream);
                prepared.executeUpdate();
                ResultSet rs = prepared.getGeneratedKeys();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
