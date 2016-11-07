
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

@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Connection myConn = DBConnection.getConnection();
        String sql = "SELECT * FROM picture WHERE idPicture=?";
        
        try{
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()){
                String type = rs.getString("type");
                response.setContentType(type);
                break;
            }
            InputStream input = rs.getBinaryStream("image");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
