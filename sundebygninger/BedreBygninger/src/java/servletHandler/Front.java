/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletHandler;

import DbHandler.DBHandler;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
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

    private DBHandler db;

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
        String username = request.getParameter("username");
        String failure = "";
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String businessName = request.getParameter("businessName");
        String method = request.getParameter("methodForm");
        User user = null;
        PrintWriter out = response.getWriter();       
        switch (method) {
           
          
            case "login":
                user = db.checkLogin(email, password);
                if (user == null) {
                    //Try to make a pop-up declaring the error (user login incorrect).
                    //After confirmation from user on the pop-up, redirect to login page, again.
                    failure = "Account was not found. Please check the entered data!";
                    request.getSession().setAttribute("failure", failure);
                    response.sendRedirect("index.jsp");
                } else if (user.getConfirmed().equals("not")) {
                    failure = "Your membership is still under review. If this has been the case for more than 48 hours, please contact Polygon support!";
                    request.getSession().setAttribute("failure", failure);
                    response.sendRedirect("index.jsp");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("firstPage.jsp");

                }
                out.print(email + password);
                break;
            case "register":
                db.registerUser(businessName, password, email, false);
                response.sendRedirect("index.jsp");
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
