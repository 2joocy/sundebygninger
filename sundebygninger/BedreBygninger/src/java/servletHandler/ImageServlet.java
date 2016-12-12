
package servletHandler;

import DbHandler.DBConnection;
import DbHandler.ImageHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

    ImageHandler handler;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        int id = Integer.parseInt(request.getParameter("id"));
        boolean thumbnail = Boolean.parseBoolean(request.getParameter("isThumbnail"));
        request.setAttribute("isThumbnail", false);
        Connection myConn = null;
        try {
            myConn = DBConnection.getConnection();
        } catch (Exception e) {
            try {
                test(response);
                return;
            } catch (Exception ex) {
                throw new RuntimeException("Unable to access default img");
            }
        }
       
        String sql = "SELECT * FROM picture WHERE idPicture=?";
       
        try {
            PreparedStatement prepared = myConn.prepareStatement(sql);
            prepared.setInt(1, id);
            ResultSet rs = prepared.executeQuery();
            if (rs.next()){
                String type = rs.getString("type");
                response.setContentType(type);
            }
            byte[] bArr;
            InputStream input = null;
            if (thumbnail) {
                input = rs.getBinaryStream("thumbnail");
            } else {
                input = rs.getBinaryStream("image");
            }
            bArr = new byte[input.available()];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) input.read();
            }
            try (OutputStream output = response.getOutputStream()) {
                output.write(bArr);
            }
        } catch(SQLException | IOException e){
            try {
                test(response);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to access default img");
            }
        }
    }

    public void test(HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
        byte[] bArr;
        handler = new ImageHandler();
        BufferedImage img = handler.getDefaultImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", os);
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        bArr = new byte[input.available()];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) input.read();
        }
        try (OutputStream output = response.getOutputStream()) {
            output.write(bArr);
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
