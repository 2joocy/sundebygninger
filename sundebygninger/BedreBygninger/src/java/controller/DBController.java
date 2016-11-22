package controller;

import DbHandler.*;
import entities.Building;
import entities.Report;
import entities.User;
import exceptions.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.Part;

public class DBController {
 
    Connection conn;
    
    DBBuildingHandler dbb;
    DBUserHandler dbu;
    DBHTMLPresenter dbhtml;
    ImageHandler img;
    
    public DBController(Connection conn) {
        this.conn = conn;
        dbb = new DBBuildingHandler(conn);
        dbu = new DBUserHandler(conn);
        dbhtml = new DBHTMLPresenter(conn);
        img = new ImageHandler(conn);
    }
    
    /**
     * DBBuildingHandler functions
     */
    
    public int addBuilding(String address, String cadastral, String builtYear,
            String area, String zipcode, String city, String conditionText,
            String service, String extraText, String dateCreated, int fk_idUser) throws SQLException {
        return dbb.addBuilding(address, cadastral, builtYear, area, zipcode, city, conditionText, service, extraText, dateCreated, fk_idUser);
    }
    
    public Building getBuilding(int idBuilding) {
        return dbb.getBuilding(idBuilding);
    }
    
    public Building getBuilding(String address) { 
        return dbb.getBuilding(address);
    }
    
    public int getFkIdReport(int BuildingId) {
        return dbb.getFkIdReport(BuildingId);
    }
    
    public Report getReport(int reportId) {
        return dbb.getReport(reportId);
    } 
    
    public int getReportCount(int idUser){
        return dbb.getReportCount(idUser);
    }
    
    public Report getReportFromBuildingId(int buildingId) {
        return dbb.getReportFromBuildingId(buildingId);
    }
    
    public void editReport(Report report) {
        dbb.editReport(report);
    }
    
    public void addRoomReport(boolean remarks, boolean damage, String damageDate,
                String damageWhere, String damageHappened,
                String damageRepaired, boolean damageWater, boolean damageRot, boolean damageMold,
                boolean damageFire, String damageOther, boolean wallRemark, String wallText,
                boolean roofRemark, String roofText, boolean floorRemark, String floorText,
                boolean moistureScan, String moistureScanText, String moistureScanMeasured,
                String conclusionText, int idReport) {
        dbb.addRoomReport(remarks, damage, damageDate, damageWhere, damageHappened, damageRepaired, damageWater, damageRot, damageMold, damageFire, damageOther, wallRemark, wallText, roofRemark, roofText, floorRemark, floorText, moistureScan, moistureScanText, moistureScanMeasured, conclusionText, idReport);
    }
    
    public int countRooms(int idReport) {
        return dbb.countRooms(idReport);
    }
    
    public void removeReport(int reportId) {
        dbb.removeReport(reportId);
    }
    
    public void requestService(int id) {
        dbb.requestService(id);
    }
    
    public void setReviewed(int idBuilding) {
        dbb.setReviewed(idBuilding);
    }
    
    public void editBuilding(String address, String cadastral, String builtYear,
                String area, String zipcode, String city, String condition,
                String extraText, int idBuilding) {
        dbb.editBuilding(address, cadastral, builtYear, area, zipcode, city, condition, extraText, idBuilding);
    }
    
    public int getBuildingCount(int idUser) {
        return dbb.getBuildingCount(idUser);
    }
    
    public int getBuildingCount() {
        return dbb.getBuildingCount();
    }
    
    public int getAwaitingService() {
        return dbb.getAwaitingService();
    }
    
    public int getEmployeeFromIdBuilding(int idBuilding) {
        return dbb.getEmployeeFromIdBuilding(idBuilding);
    }
    
    public int getAwaitingReview(int id) {
        return dbb.getAwaitingReview(id);
    }
    
    public void removeBuilding(int buildingId) {
        dbb.removeBuilding(buildingId);
    }
    
    public void removePicture(int pictureId) {
        dbb.removePicture(pictureId);
    }
    
    /**
     * DBUserHandler functions
     */
    
    public User checkLogin(String email, String password) {
        return dbu.checkLogin(email, password);
    }
    
    public String registerUser(String email, String password, String businessName, String phone, String status, String fullName, String createdDate) {
        return dbu.registerUser(email, password, businessName, phone, status, fullName, createdDate);
    }
    
    public boolean userExists(String username) {
        return dbu.userExists(username);
    }
    
    public String getUserFromDB(int id){
        return dbu.getUserFromDB(id);
    }
    
    public int countUnConfirmed() {
        return dbu.countUnConfirmed();
    }
    
    public void confirmUser(int id) {
        dbu.confirmUser(id);
    }
    
    public void confirmUser(String email) {
        dbu.confirmUser(email);
    }
    
    public void removeUser(int id){
        dbu.removeUser(id);
    }
    
    public void removeUser(String email){
        dbu.removeUser(email);
    }
    
    public void denyUser(int id) {
        dbu.denyUser(id);
    }
    
    public void denyUser(String email) {
        dbu.denyUser(email);
    }
    
    public void updatePassword(String username, String password) {
        dbu.updatePassword(username, password);
    }
    
    public String forgotPass(String email, String businessName) {
        return dbu.forgotPass(email, businessName);
    }
    
    public String encryptPassword(String password) {
        return dbu.encryptPassword(password);
    }
    
    public boolean correctPass(String password, String email) {
        return dbu.correctPass(password, email);
    }
    
    public void updateEmail(String email, int id) {
        dbu.updateEmail(email, id);
    }
    
    /**
     * DBHTMLPresenter functions
     */
    
    public String getBuildings(int idUser) throws SQLException, DatabaseConnectionException {
        return dbhtml.getBuildings(idUser);
    }
    
    public String getReportOverview(int idBuilding) throws DatabaseConnectionException, SQLException {
        return dbhtml.getReportOverview(idBuilding);
    }
    
    public String getReportOverviewWithReportID(int idReport) throws DatabaseConnectionException, SQLException {
        return dbhtml.getReportOverviewWithReportID(idReport);
    }
    
    public String getRooms(int id) throws DatabaseConnectionException, SQLException {
        return dbhtml.getRooms(id);
    }
    
    public String showRoomReport(int id) throws SQLException, DatabaseConnectionException {
        return dbhtml.showRoomReport(id);
    }
    
    public String getService(int idUser) throws SQLException, DatabaseConnectionException {
        return dbhtml.getService(idUser);
    }
    
    public String createMenu(String status) throws DatabaseConnectionException {
        return dbhtml.createMenu(dbb, dbu, status);
    }
    
    public String getAwaitingServiceCustomer(String parameter, int id, String userRole) throws DatabaseConnectionException, SQLException {
        return dbhtml.getAwaitingServiceCustomer(dbb, parameter, id, userRole);
    }
    
    public String printAwaitingService() throws DatabaseConnectionException, SQLException {
        return dbhtml.printAwaitingService();
    }
    
    public String printAwaitingReview(int id) throws DatabaseConnectionException, SQLException {
        return dbhtml.printAwaitingReview(id);
    }
    
    public String getUnConfirmed() throws DatabaseConnectionException, SQLException {
        return dbhtml.getUnConfirmed();
    }
    
    public String getImageHTML(int id) {
        return dbhtml.getImageHTML(id);
    }
    
    public String getImageHTML(int id, int width, int height) {
        return dbhtml.getImageHTML(id, width, height);
    }
    
    /**
     * ImageHandler functions
     */
    
    public int uploadImage(String description, String type, Part filePart) {
        return img.uploadImage(description, type, filePart);
    }
    
    public int uploadMainImage(String description, String type, Part filePart, int buildingID, int fk_idMainPicture) {
        return img.uploadMainImage(description, type, filePart, buildingID, fk_idMainPicture);
    }
    
    public int uploadRoofImage(String description, String type, Part filePart, int reportID) {
        return img.uploadRoofImage(description, type, filePart, reportID);
    }
    
    public int uploadOuterRoofImage(String description, String type, Part filePart, int reportID) {
        return img.uploadOuterRoofImage(description, type, filePart, reportID);
    }
    
}