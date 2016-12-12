/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DbHandler.*;
import controller.DBController;
import exceptions.UserExistsException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

@WebServlet(name = "Front", urlPatterns = {"/Front"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Front extends HttpServlet {

    public static boolean test;
    private ServletMapper serv;
    private DBController controller;

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
        try {
            controller = new DBController(test ? DBConnection.getTestConnection() : DBConnection.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            String msg = "Error connecting to database, please try again later";
            request.getSession().invalidate();
            request.getSession().setAttribute("failure", msg);
            response.sendRedirect("index.jsp");
            return;
        }
        serv = new ServletMapper(request, response, controller);

        String method = request.getParameter("methodForm");
       
        switch (method) {
            case "login":
                try {
                    serv.login();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while logging in. Please try again.");
                }
                break;
            case "register":
                try {
                    serv.register();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while registering. Please try again.");
                    serv.logout(ex.getMessage());
                } catch (UserExistsException ex) {
                    serv.logout("A user already exists with that email.  Please try again.");
                }
                break;
            case "confirmUsers":
                try {
                    serv.confirmUser();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while confirming the user. Please try again.");
                }
                break;
            case "denyUsers":
                try {
                    serv.denyUser();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while denying the user. Please try again.");
                }
                break;
            case "forgotPass":
                try {
                    serv.forgotPass();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while resetting password. Please try again.");
                }
                break;
            case "registerBuilding":
                serv.registerBuilding();
                break;
            case "logout":
                serv.logout("Successfully logged out!");
                break;
            case "editBuilding":
                serv.editBuilding();
                break;
            case "editBuildingFinal":
                try {
                    serv.editBuildingFinal();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while editing the building. Please try again.");
                }
                break;
            case "deleteBuilding":
                try {
                    serv.deleteBuilding();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while deleting the building. Please try again.");
                }
                break;
            case "uploadPicture":
                serv.uploadPicture();
                break;
            case "getService": {
                try {
                    serv.getService();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while retrieving service data. Please try again.");
                }
            }
            break;
            case "addReport":
                try {
                    serv.addReport();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while adding report data. Please try again.");
                }
                break;
            case "finalAddReport":
                try {
                    serv.finalAddReport();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while adding report data. Please try again.");
                }
                break;
            case "submitRoom":
                try {
                    serv.submitRoom();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while submitting new room. Please try again.");
                }
                break;
            case "submitReport":
                serv.submitReport();
                break;
            case "overviewReport":
                serv.overviewReport();
                break;
            case "showRoomReport":
                try {
                    serv.showRoomReport();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while showing room report. Please try again.");
                }
                break;
            case "serviceRoom":
                response.sendRedirect("service.jsp");
                break;
            case "editRoom":
                serv.editRoom();
                break;
            case "reviewReport":
                serv.reviewReport();
                break;
            case "changeEmail":
                try {
                    serv.changeEmail();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while changing email. Please try again.");
                }
                break;
            case "changePass":
                try {
                    serv.changePass();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while changing password. Please try again.");
                }
                break;
            case "filterServiceCustomer":
                serv.filterCustomer();
                break;
            case "reviewReviewedService":
                try {
                    serv.reviewReviewedService();
                } catch (SQLException ex) {
                    serv.logout("An error ocurred while viewing service. Please try again.");
                }
            break;
            case "newMainImage":
                serv.newMainImage();
                break;
            case "uploadReportOuterRoofImage":
                serv.uploadReportOuterRoofImage();
                break;
            case "uploadReportRoofImage":
                serv.uploadReportRoofImage();
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