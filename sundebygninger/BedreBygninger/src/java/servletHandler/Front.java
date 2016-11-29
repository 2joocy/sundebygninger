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
import controller.DBController;
import entities.Report;
import exceptions.DatabaseConnectionException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William-PC
 */
@WebServlet(name = "Front", urlPatterns = {"/Front"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Front extends HttpServlet {

    public static boolean test;
    private servletMapper serv;
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
            return;
        }
        serv = new servletMapper(request, response, controller);

        String method = request.getParameter("methodForm");
       
        switch (method) {
            case "login":
                serv.login();
                break;
            case "register":
                serv.register();
                break;
            case "confirmUsers":
                serv.confirmUser();
                break;
            case "denyUsers":
                serv.denyUser();
                break;
            case "forgotPass":
                serv.forgotPass();
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
                serv.editBuildingFinal();
                break;
            case "deleteBuilding":
                serv.deleteBuilding();
                break;
            case "uploadPicture":
                serv.uploadPicture();
                break;
            case "getService": {
                try {
                    serv.getService();
                } catch (SQLException ex) {
                    serv.logout(ex.getMessage());
                }
            }
            break;
            case "addReport":
                serv.addReport();
                break;
            case "finalAddReport":
                serv.finalAddReport();
                break;
            case "submitRoom":
                serv.submitRoom();
                break;
            case "submitReport":
                serv.submitReport();
                break;
            case "overviewReport":
                serv.overviewReport();
                break;
            case "showRoomReport":
                serv.showRoomReport();
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
                serv.changeEmail();
                break;
            case "changePass":
                serv.changePass();
                break;
            case "filterServiceCustomer":
                serv.filterCustomer();
                break;
            case "reviewReviewedService": {
                try {
                    serv.reviewReviewedService();
                } catch (SQLException ex) {
                    serv.logout(ex.getMessage());
                }
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
