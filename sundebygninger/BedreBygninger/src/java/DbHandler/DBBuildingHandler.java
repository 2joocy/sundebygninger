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


public class DBBuildingHandler {
    
    public void addBuilding( String address, String cadastral, String buildingGrade, 
                    String area, String zipcode, String city, String condition, String service, 
                    String extraText, double buildingArea, int builtYear, int fk_idUser, 
                    int fk_MainPicture, int fk_idReport, Date dateCreated) {
       
        try {
            Connection myConn = DBConnection.getConnection();
            String sql = "INSERT INTO building (address, cadastral, builtYear, area, zipcode, city, condition, service, extraText, dateCreated, fk_idUser, fk_idMainPicture, fk_idReport)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,)";
            PreparedStatement prepared = myConn.prepareStatement(sql);
           
            prepared.setString(1, address);
            prepared.setString(2, cadastral);
            prepared.setInt(3, builtYear);
            prepared.setString(4, area);
            prepared.setString(5, zipcode);
            prepared.setString(6, city);
            prepared.setString(7, condition);
            prepared.setString(8, service);
            prepared.setString(9, cadastral);
            prepared.setString(10, extraText);
            prepared.setDate(11, dateCreated);
            prepared.setInt(12, fk_idUser);
            prepared.setInt(13, fk_MainPicture);
            prepared.setInt(14, fk_idReport);
            
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException ex) {
            
        }
        
    }
    
    public String getBuildings(int idUser){
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
    
    
    
    public String createMenu(String status){
        String menu = "";
        if(status.equalsIgnoreCase("customer")){
            menu = "<div id='mySidenav' class='sidenav'>Welcome <%user.getBusinessName();%><a href='javascript:void(0)' class='closebtn' onclick='closeNav()'>&times;</a><a href='overviewBuilding.jsp'>Estates</a><a href='service.jsp'>Apply Service</a><a href='orderHistory.jsp'>Order History</a><a href='overviewAccount.jsp'>Account Management</a><a href='contact.jsp'>Help</a></div>"; 
        }else if(status.equalsIgnoreCase("worker")){
            menu = "<div id='mySidenav' class='sidenav'>Welcome <%user.getBusinessName();%><a href='javascript:void(0)' class='closebtn' onclick='closeNav()'>&times;</a><a href='overviewBuilding.jsp'>Estates</a><a href='overviewUsers.jsp'>Account Management(<% out.print(db.countUnConfirmed());%>)</a><a href='orderHistory.jsp'>Order History</a><a href='overviewAccount.jsp'>Account Management</a><a href='contact.jsp'>Help</a></div>"; 
        }
        return menu;
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
}
