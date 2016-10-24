/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import entities.Building;
import entities.User;
import java.awt.HeadlessException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DBBuildingHandler {

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
                String address = myRS.getString("address");
                String city = myRS.getString("city");
                String zipcode = myRS.getString("zipcode");
                tableData += "<tr><td><img src='http://cdn.technicpack.net/platform2/pack-icons/879003.png?1470098482'</img>" +
                             "<td>" + address + "</td><td>" + zipcode + "</td><td>" + city +
                             "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='editBuilding'><td><button class='editbtn' type='submit'>Show/Edit</button></td>" + 
                             "<input type='hidden' name='idBuilding' value='" + idBuilding + "'></td>" +
                             "</form>" +
                             "<form action='Front' method='POST'><input type='hidden' name='methodForm' value='deleteBuilding'><td><button class='dltbtn' type='submit'>Delete</button></td>" +
                             "<input type='hidden' name='idBuilding' value='" + idBuilding + "'></td>" +
                             "</form>" +
                             "</tr>";
                id++;
            }
        } catch (SQLException | HeadlessException ex) {

        }
        tableData += "</tbody>\n"
                + "  </table>";
        return tableData;
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
            return "<li><a href='overviewBuilding.jsp'>Estate</a></li>\n" +
"            <li><a href='service.jsp'>Service Overview</a></li>\n" +
"            <li><a href='account.jsp'>Account Management</a></li>\n" +
"            <li><a href='contact.jsp'>Contact Staff</a></li>";
        } else if (status.equalsIgnoreCase("worker")) {
            return "<li><a href='overviewBuilding.jsp'>Estates</a></li>\n" +
"            <li><a href='service.jsp'>Service Overview</a></li>\n" +
"            <li><a href='overviewUsers.jsp'>Account Management("+ db.countUnConfirmed() + ")</a></li>\n" +
"            <li><a href='contact.jsp'>Contact Staff</a></li>";
        }else if(status == null){
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
    
}