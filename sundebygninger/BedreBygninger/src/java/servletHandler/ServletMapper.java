/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletHandler;

import controller.DBController;
import entities.Report;
import entities.User;
import exceptions.UserExistsException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author William Pfaffe
 */
public class ServletMapper {

    private final DBController controller;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private String failure;

    public ServletMapper(HttpServletRequest request, HttpServletResponse response, DBController dbc) {
        this.controller = dbc;
        this.request = request;
        this.response = response;
    }

    public void login() throws IOException, SQLException {
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = controller.checkLogin(email, password);
        if (user == null) {
            failure = "Account was not found. Please check the entered data!";
            request.getSession().setAttribute("failure", failure);
            response.sendRedirect("index.jsp");
        } else if (user.getStatus().equals("not")) {
            failure = "Your membership is still under review. If this has been the case for more than 48 hours, please contact Polygon support!";
            request.getSession().setAttribute("failure", failure);
            response.sendRedirect("index.jsp");
        } else if (user.getStatus().equalsIgnoreCase("worker")) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("firstPage.jsp");
        } else if (user.getStatus().equalsIgnoreCase("denied")) {
            failure = "Your membership has been denied due to illegitimate info credidencials. If you are unsatisfied with these terms, please contact our support staff.";
            request.getSession().setAttribute("failure", failure);
            response.sendRedirect("index.jsp");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("firstPage.jsp");
        }
    }

    public void register() throws IOException, SQLException, UserExistsException {
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName");
        String businessName = request.getParameter("businessName");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();
        controller.registerUser(email, password, businessName, phone, "not", fullName, dateFormat.format(datePre));
        /*
        if (message.contains("Error, ")) {
            response.sendRedirect("register.jsp");
        } else {
            controller.registerUser(email, password, businessName, phone, "not", fullName, dateFormat.format(datePre));
            response.sendRedirect("awaitingApproval.jsp");
        }
        */
    }

    public void confirmUser() throws IOException, SQLException {
        String id = request.getParameter("userID");
        controller.confirmUser(Integer.parseInt(id));
        response.sendRedirect("overviewUsers.jsp");
    }

    public void denyUser() throws IOException, SQLException {
        String id = request.getParameter("userID");
        controller.denyUser(Integer.parseInt(id));
        response.sendRedirect("overviewUsers.jsp");
    }

    public void forgotPass() throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String businessName = request.getParameter("businessName");
        if (!controller.userExists(email)) {
            out.println("No such user exists.");
            return;
        }
        out.println("Email sent to " + email + " with new password.");
        controller.forgotPass(email, businessName);
    }

    public void registerBuilding() throws IOException {
        String id = request.getParameter("userID");
        String address = request.getParameter("address");
        String cadastral = request.getParameter("cadastral");
        String builtYear = request.getParameter("builtYear");
        String area = request.getParameter("area");
        String zipcode = request.getParameter("zipcode");
        String city = request.getParameter("city");
        String extraText = request.getParameter("extraText");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();
        String date = dateFormat.format(datePre);
        try {
            controller.addBuilding(address, cadastral, builtYear, area, zipcode, city, "", "", extraText, date, Integer.parseInt(id));
            response.sendRedirect("overviewBuilding.jsp");
        } catch (SQLException ex) {
            String msg = "Database connection error, please try again later."; //ex.getMessage();
            request.getSession().invalidate();
            request.getSession().setAttribute("failure", msg);
            response.sendRedirect("index.jsp");
        }

    }

    public void logout(String failure) throws IOException {
        request.getSession().invalidate();
        request.getSession().setAttribute("failure", failure);
        response.sendRedirect("index.jsp");
    }

    public void editBuilding() throws IOException {
        String idBuilding = request.getParameter("idBuilding");
        request.getSession().setAttribute("idBuilding", idBuilding);
        response.sendRedirect("editBuilding.jsp");
    }

    public void editBuildingFinal() throws IOException, SQLException {
        String id = request.getParameter("userID");
        String address = request.getParameter("address");
        String cadastral = request.getParameter("cadastral");
        String builtYear = request.getParameter("builtYear");
        String area = request.getParameter("area");
        String zipcode = request.getParameter("zipcode");
        String city = request.getParameter("city");
        String condition = request.getParameter("condition");
        String extraText = request.getParameter("extraText");
        String idBuilding2 = request.getParameter("buildingID");
        controller.editBuilding(address, cadastral, builtYear, area, zipcode, city, condition, extraText, Integer.parseInt(idBuilding2));
        response.sendRedirect("overviewBuilding.jsp");
    }

    public void deleteBuilding() throws IOException, SQLException {
        String idBuilding = request.getParameter("idBuilding");
        controller.removeBuilding(Integer.parseInt(idBuilding));
        response.sendRedirect("overviewBuilding.jsp");
    }

    public void uploadPicture() throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        int imageId = controller.uploadImage("", filePart.getContentType(), filePart);
        String imageMessage = (imageId == -1 ? "Image failed to upload." : "Image uploaded to the database.");
        request.getSession().setAttribute("imageMessage", "" + imageMessage);
        request.getSession().setAttribute("imageId", "" + imageId);
        response.sendRedirect("FileConf.jsp");
    }

    public void getService() throws IOException, SQLException {
        String idBuilding = request.getParameter("idBuilding");
        if (controller.getBuilding(Integer.parseInt(idBuilding)).getService().equalsIgnoreCase("awaiting")) {
            failure = "You have already requested service for this house!";
            request.getSession().setAttribute("failure", failure);
            response.sendRedirect("overviewBuilding.jsp");
        } else {
            controller.requestService(Integer.parseInt(idBuilding));
            response.sendRedirect("overviewBuilding.jsp");
        }
    }

    public void addReport() throws IOException, SQLException {
        String idBuilding = request.getParameter("idBuilding");
        request.getSession().setAttribute("idBuilding", idBuilding);
        request.getSession().setAttribute("idReport", controller.getFkIdReport(Integer.parseInt(idBuilding)));
        response.sendRedirect("addReport.jsp");
    }

    public void finalAddReport() throws IOException, SQLException {
        boolean outerWallRemark = false;
        String buildingUsage = request.getParameter("buildingUsage");
        String outerWallText = request.getParameter("outerWallText");
        String roofText = request.getParameter("roofText");
        String outerWallRemarks = request.getParameter("outerWallRemarks");
        String roofRemarks = request.getParameter("roofRemarks");
        boolean roofRemark1 = false;

        if (roofRemarks != null && roofRemarks.equalsIgnoreCase("1")) {
            roofRemark1 = true;
        }

        if (outerWallRemarks != null && outerWallRemarks.equalsIgnoreCase("1")) {
            outerWallRemark = true;
        }

        int idBuildingNew = Integer.parseInt((String) request.getSession().getAttribute("idBuilding"));
        int idNew = (Integer) request.getSession().getAttribute("userID");
        int reportedID = (Integer) request.getSession().getAttribute("idReport");
        Report newRep = new Report(reportedID, buildingUsage, roofRemark1, 0, roofText, outerWallRemark, 0, outerWallText, idNew, "");
        controller.editReport(newRep);
        controller.setReviewed(idBuildingNew);
        response.sendRedirect("addReport.jsp");
    }

    public void submitRoom() throws IOException, SQLException {
        boolean remark = false;
        boolean damage = false;
        boolean damageWater = false;
        boolean damageRot = false;
        boolean damageMold = false;
        boolean damageFire = false;
        boolean wallRemarks = false;
        boolean roofRemark = false;
        boolean floorRemark = false;
        boolean moistureScan = false;
        String roofRemarks = request.getParameter("roofRemarks");
        String typeDmg = request.getParameter("typeDmg");
        String remarks = request.getParameter("remarks");
        String dmg = request.getParameter("damage");
        String dateDmg = request.getParameter("dateOfDamage");
        String placeDmg = request.getParameter("placementOfDmg");
        String descDmg = request.getParameter("descDmg");
        String reasonDmg = request.getParameter("reasonDmg");
        String hasWallRemark = request.getParameter("hasWallRemarks");
        String wallRemark = request.getParameter("wallRemark");
        String hasRoofRemarks = request.getParameter("hasRoofRemarks");
        String hasFloorRemarks = request.getParameter("hasFloorRemark");
        String floorRemarks = request.getParameter("floorRemarks");
        String hasMoistureRemark = request.getParameter("hasMoistureRemark");
        String moistureDesc = request.getParameter("moistureDesc");
        String moistureMeasure = request.getParameter("moistureMeasure");
        String conclusion = request.getParameter("conclusion");

        if (remarks != null && remarks.equals("on")) {
            remark = true;
        }

        if (dmg != null && dmg.equals("on")) {
            damage = true;
        }

        if (hasWallRemark != null && hasWallRemark.equalsIgnoreCase("on")) {
            wallRemarks = true;
        }

        if (hasFloorRemarks != null && hasFloorRemarks.equals("on")) {
            floorRemark = true;
        }

        if (hasRoofRemarks != null && hasRoofRemarks.equalsIgnoreCase("on")) {
            roofRemark = true;
        }

        if (hasMoistureRemark != null && hasMoistureRemark.equalsIgnoreCase("on")) {
            moistureScan = true;
        }

        if (damage && typeDmg != null) {
            if (typeDmg.equals("Water Damage")) {
                damageWater = true;
            } else if (typeDmg.equals("Fire Damage")) {
                damageFire = true;
            } else if (typeDmg.equals("Mold Damage")) {
                damageMold = true;
            } else if (typeDmg.equals("Rot Damage")) {
                damageRot = true;
            }
        }

        if (hasMoistureRemark != null && hasMoistureRemark.equalsIgnoreCase("on")) {
            moistureScan = true;
        }
        int fk_idReport = (Integer) request.getSession().getAttribute("idReport");
        controller.addRoomReport(remark, damage, dateDmg, placeDmg, descDmg, "", damageWater,
                damageRot, damageMold, damageFire, reasonDmg, wallRemarks, wallRemark, roofRemark,
                roofRemarks, floorRemark, floorRemarks, moistureScan, moistureDesc, moistureMeasure, conclusion, fk_idReport);
        response.sendRedirect("addReport.jsp");
    }

    public void submitReport() throws IOException, ServletException {
        Part filePart2 = request.getPart("picture");
        int imageId2 = controller.uploadImage("", filePart2.getContentType(), filePart2);
        String imageMessage2 = (imageId2 == -1 ? "Image failed to upload." : "Image uploaded to the database.");
        request.getSession().setAttribute("imageMessage", "" + imageMessage2);
        request.getSession().setAttribute("imageId", "" + imageId2);
    }

    public void overviewReport() throws IOException {
        String idBuilding = request.getParameter("idBuilding");
        String idReport1 = request.getParameter("idReport");
        request.getSession().setAttribute("idReport", idReport1);
        request.getSession().setAttribute("idBuilding", idBuilding);
        response.sendRedirect("showReportCustomer.jsp");
    }

    public void showRoomReport() throws IOException, SQLException {
        String idReport1 = request.getParameter("idReport");
        request.getSession().setAttribute("report", controller.getReport(Integer.parseInt(idReport1)));
        response.sendRedirect("showRoomReport.jsp");
    }

    public void serviceRoom() throws IOException {
        response.sendRedirect("service.jsp");
    }

    public void editRoom() throws IOException {
        String idRoom = request.getParameter("idRoom");
        request.getSession().setAttribute("idRoom", idRoom);
        response.sendRedirect("editRoom.jsp");
    }

    public void reviewReport() throws IOException {
        String idBuilding = request.getParameter("idBuilding");
        request.getSession().setAttribute("idBuilding", idBuilding);
        response.sendRedirect("reviewReport.jsp");
    }

    public void changeEmail() throws IOException, SQLException {
        String newEmail = request.getParameter("newEmail");
        User user = (User) request.getSession().getAttribute("user");
        controller.updateEmail(newEmail, user.getIdUser());
        response.sendRedirect("account.jsp");
    }

    public void changePass() throws IOException, SQLException {
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPass");
        String email = request.getParameter("email");
        if (controller.correctPass(password, email) == true) {
            controller.updatePassword(email, newPassword);
            request.getSession().setAttribute("failure", "Password has been successfully changed!");
            response.sendRedirect("account.jsp");
        } else {
            request.getSession().setAttribute("failure", "Password has not been changed due to incorrect password. Please double check your info before typing!");
            response.sendRedirect("account.jsp");
        }
    }

    public void filterCustomer() throws IOException {
        String typeSearch = request.getParameter("typesearch");
        request.getSession().setAttribute("searchParameter", typeSearch);
        response.sendRedirect("service.jsp");
    }

    public void reviewReviewedService() throws IOException, SQLException {
        String idBuilding = request.getParameter("idBuilding");
        String idReport1 = request.getParameter("idReport");
        request.getSession().setAttribute("idReport", controller.getBuilding(Integer.parseInt(idReport1)));
        request.getSession().setAttribute("idBuilding", idBuilding);
        response.sendRedirect("reviewBuildingDetail.jsp");
    }

    public void newMainImage() throws IOException, ServletException {
        String redirect = request.getParameter("redirect");
        String fk_idMainPicture = request.getParameter("fk_idMainPicture");
        String idBuilding = request.getParameter("idBuilding");
        Part filePart = request.getPart("picture");
        Part filePart2 = request.getPart("picture");
        Image imageNon = controller.getImageFromPart(filePart2).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        bufferedThumbnail.getGraphics().drawImage(imageNon, 0, 0, null);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(bufferedThumbnail, filePart.getContentType(), bOut);
        controller.uploadThumbnail(99, filePart.getContentType(), bOut);
        int newImageID = Integer.parseInt(idBuilding);
        int fk_mainImage = Integer.parseInt(fk_idMainPicture);
        int imageId = controller.uploadMainImage("", filePart.getContentType(), filePart, newImageID, fk_mainImage);
        //controller.uploadThumbnail(99, filePart.getContentType(), buffered);
        if (redirect != null) {
            response.sendRedirect(redirect);
            return;
        }
        response.sendRedirect("overviewBuilding.jsp");
    }

    public void uploadReportOuterRoofImage() throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        int idReport = (Integer) request.getSession().getAttribute("idReport");
        String insertColumn = "fk_idPictureOuterRoof";
        response.sendRedirect("overviewBuilding.jsp");
    }

    public void uploadReportRoofImage() throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        String fk_idMainPicture = request.getParameter("fk_idMainPicture");
        filePart = request.getPart("picture");
        //2) Get which report we must insert it into
        int idReport = (Integer) request.getSession().getAttribute("idReport");
        //3) Which field we must insert it into
        String insertColumn = "fk_idPictureRoof";

        int fk_mainImage = Integer.parseInt(fk_idMainPicture);
        response.sendRedirect("overviewBuilding.jsp");
    }
}