/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletHandler;

import DbHandler.DBBuildingHandler;
import DbHandler.DBUserHandler;
import entities.Building;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;
import DbHandler.*;

/**
 *
 * @author William-PC
 */
@WebServlet(name = "Front", urlPatterns = {"/Front"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Front extends HttpServlet {

    public static boolean test;

    private DBUserHandler db;
    private DBBuildingHandler dbB;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        db = new DBUserHandler(test ? DBConnection.getTestConnection() : DBConnection.getConnection());
        dbB = new DBBuildingHandler(test ? DBConnection.getTestConnection() : DBConnection.getConnection());

        //String username = request.getParameter("username");
        String failure = "";
        String action = request.getParameter("action");
        String password = request.getParameter("password");
        String id = request.getParameter("userID");
        String idUser = request.getParameter("idUser");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName");
        String businessName = request.getParameter("businessName");
        String method = request.getParameter("methodForm");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();
        String date = dateFormat.format(datePre);
        String address = request.getParameter("address");
        String cadastral = request.getParameter("cadastral");
        String builtYear = request.getParameter("builtYear");
        String area = request.getParameter("area");
        String zipcode = request.getParameter("zipcode");
        String city = request.getParameter("city");
        String condition = request.getParameter("condition");
        String extraText = request.getParameter("extraText");
        String idBuilding = request.getParameter("idBuilding");
        String idBuilding2 = request.getParameter("buildingID");
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
        String roofRemarks = request.getParameter("roofRemarks");
        String hasFloorRemarks = request.getParameter("hasFloorRemark");
        String floorRemarks = request.getParameter("floorRemarks");
        String hasMoistureRemark = request.getParameter("hasMoistureRemark");
        String moistureDesc = request.getParameter("moistureDesc");
        String moistureMeasure = request.getParameter("moistureMeasure");
        String conclusion = request.getParameter("conclusion");
        String buildingUsage = request.getParameter("buildingUsage");
        String outerWallText = request.getParameter("outerWallText");
        String roofText = request.getParameter("roofText");
        String outerWallRemarks = request.getParameter("outerWallRemarks");
        String reportID = request.getParameter("reportID");
        String idRoom = request.getParameter("idRoom");
        String newPassword = request.getParameter("newPass");
        String newEmail = request.getParameter("newEmail");
        String fk_idMainPicture = request.getParameter("fk_idMainPicture");
        boolean outerWallRemark = false;
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
        PrintWriter out = response.getWriter();

        switch (method) {
            case "login":
                User user = db.checkLogin(email, password);
                if (user == null) {
                    //Try to make a pop-up declaring the error (user login incorrect).
                    //After confirmation from user on the pop-up, redirect to login page, again.
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
                //out.print(user == null ? "User == null" : user.getEmail());
                break;
            case "register":
                String message = db.registerUser(email, password, businessName, phone, "not", fullName, dateFormat.format(datePre));
                if (message.contains("Error, ")) {
                    //request.getSession().setAttribute("failure", message);
                    response.sendRedirect("register.jsp");
                } else {
                    db.registerUser(email, password, businessName, phone, "not", fullName, dateFormat.format(datePre));
                    response.sendRedirect("awaitingApproval.jsp");
                }

                break;
            case "confirmUsers":
                db.confirmUser(Integer.parseInt(id));
                response.sendRedirect("overviewUsers.jsp");
                break;
            case "denyUsers":
                db.denyUser(Integer.parseInt(id));
                response.sendRedirect("overviewUsers.jsp");
                break;
            case "forgotPass":
                if (!db.userExists(email)) {
                    out.println("No such user exists.");
                    return;
                }
                out.println("Email sent to " + email + " with new password.");
                db.forgotPass(email, businessName);
                break;
            case "registerBuilding":
                int idB = dbB.addBuilding(address, cadastral, builtYear, area, zipcode, city, "", "", extraText, date, Integer.parseInt(id), 0, 0);
                int idF = dbB.submitReport("", false, 0, "", false, 0, "", 0, "");
                dbB.insertFkReport(idB, idF);
                response.sendRedirect("overviewBuilding.jsp");
                break;
            case "logout":
                request.getSession().invalidate();
                failure = "You have successfully been logged out!";
                request.getSession().setAttribute("failure", failure);
                response.sendRedirect("index.jsp");
                break;
            case "editBuilding":
                request.getSession().setAttribute("building", dbB.getBuilding(Integer.parseInt(idBuilding)));
                response.sendRedirect("editBuilding.jsp");
                break;
            case "editBuildingFinal":
                dbB.editBuilding(address, cadastral, builtYear, area, zipcode, city, condition, extraText, Integer.parseInt(idBuilding2));
                response.sendRedirect("overviewBuilding.jsp");
                break;
            case "deleteBuilding":
                dbB.removeBuilding(Integer.parseInt(idBuilding));
                response.sendRedirect("overviewBuilding.jsp");
                break;
            case "uploadPicture":
                Part filePart = request.getPart("picture");
                Building building = (Building) request.getSession().getAttribute("building");
                int imageId = ImageHandler.uploadImage(DBConnection.getConnection(), "", filePart.getContentType(), filePart);
                String imageMessage = (imageId == -1 ? "Image failed to upload." : "Image uploaded to the database.");
                request.getSession().setAttribute("imageMessage", "" + imageMessage);
                request.getSession().setAttribute("imageId", "" + imageId);
                response.sendRedirect("FileConf.jsp");
                break;
            case "getService":
                if (dbB.getBuilding(Integer.parseInt(idBuilding)).getService().equalsIgnoreCase("awaiting")) {
                    failure = "You have already requested service for this house!";
                    request.getSession().setAttribute("failure", failure);
                    response.sendRedirect("overviewBuilding.jsp");
                } else {
                    dbB.requestService(Integer.parseInt(idBuilding));
                    response.sendRedirect("overviewBuilding.jsp");
                }
                break;
            case "addReport":
                request.getSession().setAttribute("idBuilding", idBuilding);
                request.getSession().setAttribute("idReport", dbB.getFkIdReport(Integer.parseInt(idBuilding)));
                request.getSession().setAttribute("hasReport", "yes");
                response.sendRedirect("addReport.jsp");
                break;
            case "finalAddReport":
                boolean roofRemark1 = false;
                if (roofRemarks.equalsIgnoreCase("1")) {
                    roofRemark1 = true;
                }

                if (outerWallRemarks.equalsIgnoreCase("1")) {
                    outerWallRemark = true;
                }

                int idBuildingNew = Integer.parseInt((String) request.getSession().getAttribute("idBuilding"));
                int idNew = (Integer) request.getSession().getAttribute("userID");
                if(dbB.getFkIdReport(idBuildingNew) == -1){
                    dbB.insertFkReport(idBuildingNew, dbB.submitReport(buildingUsage, roofRemark1, 0, roofText, outerWallRemark, 0, outerWallText, idNew, "hej"));
                    response.sendRedirect("overviewBuilding.jsp");
                }else{
                    request.getSession().setAttribute("failure", "Please refrain from creating multiple reports under one building! Please add room reports, if this is the case!");
                    response.sendRedirect("overviewBuilding.jsp");
                }
                
                break;
            case "submitRoom":
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
                dbB.addRoomReport(remark, damage, dateDmg, placeDmg, descDmg, "", damageWater,
                        damageRot, damageMold, damageFire, reasonDmg, wallRemarks, wallRemark, roofRemark,
                        roofRemarks, floorRemark, floorRemarks, moistureScan, moistureDesc, moistureMeasure, conclusion, fk_idReport);
                response.sendRedirect("service.jsp");
                break;
            case "submitReport":
                Part filePart2 = request.getPart("picture");
                int imageId2 = ImageHandler.uploadImage(DBConnection.getConnection(), "", filePart2.getContentType(), filePart2);
                String imageMessage2 = (imageId2 == -1 ? "Image failed to upload." : "Image uploaded to the database.");
                request.getSession().setAttribute("imageMessage", "" + imageMessage2);
                request.getSession().setAttribute("imageId", "" + imageId2);
                break;
            case "overviewReport":
                out.print(reportID);
                break;
            case "serviceRoom":
                response.sendRedirect("service.jsp");
                break;
            case "editRoom":
                request.getSession().setAttribute("idRoom", idRoom);
                response.sendRedirect("editRoom.jsp");
                break;
            case "reviewReport":
                request.getSession().setAttribute("idBuilding", idBuilding);
                response.sendRedirect("reviewReport.jsp");
                break;
            case "changeEmail":
                user = (User) request.getSession().getAttribute("user");
                db.updateEmail(newEmail, user.getIdUser());
                response.sendRedirect("account.jsp");
                break;
            case "changePass":
                if (db.correctPass(password, email) == true) {
                    db.updatePassword(email, newPassword);
                    request.getSession().setAttribute("failure", "Password has been successfully changed!");
                    response.sendRedirect("account.jsp");
                } else {
                    request.getSession().setAttribute("failure", "Password has not been changed due to incorrect password. Please double check your info before typing!");
                    response.sendRedirect("account.jsp");
                }
                break;
            case "newMainImage":
                filePart = request.getPart("picture");
                int newImageID = Integer.parseInt(idBuilding);
                int fk_mainImage = Integer.parseInt(fk_idMainPicture);
                imageId = ImageHandler.uploadMainImage(DBConnection.getConnection(), "", filePart.getContentType(), filePart, newImageID, fk_mainImage);
                response.sendRedirect("overviewBuilding.jsp");
                break;
            case "uploadReportOuterRoofImage":
                //Steps
                
                //1) Get picture into filePart
                filePart = request.getPart("picture");
                //2) Get which report we must insert it into
                int idReport = (Integer) request.getSession().getAttribute("idReport");
                //3) Which field we must insert it into
                String insertColumn = "fk_idPictureOuterRoof";
                
                
                
                //imageId = dbB.uploadMainImage("", filePart.getContentType(), filePart, newImageID, fk_mainImage);
                response.sendRedirect("overviewBuilding.jsp");
                break;
            case "uploadReportRoofImage":
                //Steps
                
                //1) Get picture into filePart
                filePart = request.getPart("picture");
                //2) Get which report we must insert it into
                idReport = (Integer) request.getSession().getAttribute("idReport");
                //3) Which field we must insert it into
                insertColumn = "fk_idPictureRoof";
                
                
                fk_mainImage = Integer.parseInt(fk_idMainPicture);
                //imageId = dbB.uploadMainImage("", filePart.getContentType(), filePart, newImageID, fk_mainImage);
                response.sendRedirect("overviewBuilding.jsp");
                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
