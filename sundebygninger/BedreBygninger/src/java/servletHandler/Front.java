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
        String hasFloorRemarks = request.getParameter("hasFloorRemarks");
        String floorRemarks = request.getParameter("floorRemarks");
        String hasMoistureRemark = request.getParameter("hasMoistureRemark");
        String moistureDesc = request.getParameter("moistureDesc");
        String moistureMeasure = request.getParameter("moistureMeasure");
        String conclusion = request.getParameter("conclusion");
        String idRoom = request.getParameter("idRoom");
        String newPassword = request.getParameter("newPass");
        String newEmail = request.getParameter("newEmail");
        String fk_idMainPicture = request.getParameter("fk_idMainPicture");

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
                dbB.addBuilding(address, cadastral, builtYear, area, zipcode, city, "", "", extraText, date, Integer.parseInt(id), 0, 0);
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
                int imageId = dbB.uploadImage("", filePart.getContentType(), filePart);
                String imageMessage = (imageId == -1 ? "Image failed to upload." : "Image uploaded to the database.");
                response.sendRedirect("FileConf.jsp");
                break;
                
            case "getService":
                request.getSession().setAttribute("idBuilding", idBuilding);
                response.sendRedirect("service.jsp");
                break;
                
            case "submitReport":
                Part filePart2 = request.getPart("picture");
                int imageId2 = dbB.uploadImage("", filePart2.getContentType(), filePart2);
                String imageMessage2 = (imageId2 == -1 ? "Image failed to upload." : "Image uploaded to the database.");
                request.getSession().setAttribute("imageMessage", "" + imageMessage2);
                request.getSession().setAttribute("imageId", "" + imageId2);
                break;
                
            case "serviceRoom":
                response.sendRedirect("service.jsp");
                break;
                
            case "editRoom":
                request.getSession().setAttribute("idRoom", idRoom);
                response.sendRedirect("editRoom.jsp");
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
                imageId = dbB.uploadMainImage("", filePart.getContentType(), filePart, newImageID, fk_mainImage);
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
