/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletHandler;

import DbHandler.DBBuildingHandler;
import DbHandler.DBUserHandler;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author William-PC
 */
@WebServlet(name = "Front", urlPatterns = {"/Front"})
public class Front extends HttpServlet {

    private final DBUserHandler db = new DBUserHandler();
    private final DBBuildingHandler dbB = new DBBuildingHandler();
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
        //String username = request.getParameter("username");
        String failure = "";
        String password = request.getParameter("password");
        String id = request.getParameter("userID");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName");
        String businessName = request.getParameter("businessName");
        String method = request.getParameter("methodForm");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datePre = new Date();
        String address = request.getParameter("address");
        String cadastral = request.getParameter("cadastral");
        String builtYear = request.getParameter("builtYear");
        String area = request.getParameter("area");
        String zipcode = request.getParameter("zipcode");
        String city = request.getParameter("city");
        String condition = request.getParameter("condition");
        String extraText = request.getParameter("extraText");
        
        
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
                    response.sendRedirect("workerPage.jsp");
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
                }else{
                db.registerUser(email, password, businessName, phone, "not", fullName, dateFormat.format(datePre));
                response.sendRedirect("awaitingApproval.jsp");
                }
                
                break;
            case "confirmUsers":
                db.confirmUser(id);
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
                /*
                
                Need to take up discussion whether building table should have as many foreign keys as it has.
                */
//                dbB.addBuilding();
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
