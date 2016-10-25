
package servletHandler;

import DbHandler.DBConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;


@WebServlet(name = "imageServlet", urlPatterns = {"/imageServlet"})
public class ImageServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Connection myConn = DBConnection.getConnection();
        String sql = "SELECT * FROM picture WHERE idPicture=?";
        try{
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()){
                response.setContentType(rs.getString("type"));
            }
            InputStream input = rs.getBinaryStream("image");
            JOptionPane.showMessageDialog(null, input.available());
            byte[] bArr = new byte[input.available()];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) input.read();
            }
            input.close();
            OutputStream output = response.getOutputStream();
            output.write(bArr);
            output.close();
        }
        catch(Exception e){
            e.printStackTrace();
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
