package DbHandler;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

public class ImageHandler {
 
    Connection conn;
    
    public ImageHandler() {
        conn = DBConnection.getConnection();
    }
    
    public ImageHandler(Connection conn) {
        this.conn = conn;
    }
   
    public int uploadImage(String description, String type, Part filePart) {
        try {
            String sql = "INSERT INTO picture (description, type, image, thumbnail) VALUES (?, ?, ?, ?)";
            PreparedStatement prepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InputStream inputStream = null;
            InputStream inputThumb = null;
            if (filePart != null) {
                inputStream = filePart.getInputStream();
                inputThumb = filePart.getInputStream();
            }
            if (inputStream != null) {
                prepared.setString(1, description);
                prepared.setString(2, type);
                //Thumbnail
                prepared.setBlob(3, inputStream);
                BufferedImage image = ImageIO.read(inputThumb);
                BufferedImage thumbnail = resize(image, 50, 50);
                JOptionPane.showMessageDialog(null, thumbnail.getHeight());
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                
                // Hvor bliver  det skrevet?
                ImageIO.write(thumbnail, type, byteOut);
                InputStream is = new ByteArrayInputStream(byteOut.toByteArray());

                prepared.setBlob(4, is);
                prepared.executeUpdate();
                ResultSet rs = prepared.getGeneratedKeys();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }  
    
        
    public int uploadMainImage(String description, String type, Part filePart, int buildingID, int fk_idMainPicture) {
        try {
            String sql = "DELETE FROM picture WHERE idPicture=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, fk_idMainPicture);
            prepared.executeUpdate();
            int returnValue = uploadImage(description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE building SET fk_idMainPicture = ? WHERE idBuilding = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, buildingID);
                prepared.executeUpdate();
                return returnValue;
            }
        } catch (SQLException | HeadlessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int uploadRoofImage(String description, String type, Part filePart, int reportID) {
        try {
            String sql = "SELECT fk_idPictureRoof FROM picture WHERE idReport=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, reportID);
            ResultSet result = prepared.executeQuery();
            int pictureID = -1;
            if (result.next()) {
                pictureID = result.getInt("fk_idPictureRoof");
            }
            if (pictureID != -1) {
                sql = "DELETE FROM picture WHERE idPicture=?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, pictureID);
                prepared.executeUpdate();
            }
            int returnValue = uploadImage(description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE report SET fk_idPictureRoof = ? WHERE idReport = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, reportID);
                prepared.executeUpdate();
                return returnValue;
            }
        } catch (SQLException | HeadlessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int uploadOuterRoofImage(String description, String type, Part filePart, int reportID) {
        try {
            String sql = "SELECT fk_idPictureOuterRoof FROM picture WHERE idReport=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, reportID);
            ResultSet result = prepared.executeQuery();
            int pictureID = -1;
            if (result.next()) {
                pictureID = result.getInt("fk_idPictureOuterRoof");
            }
            if (pictureID != -1) {
                sql = "DELETE FROM picture WHERE idPicture=?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, pictureID);
                prepared.executeUpdate();
            }
            int returnValue = uploadImage(description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE report SET fk_idPictureOuterRoof = ? WHERE idReport = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, reportID);
                prepared.executeUpdate();
                return returnValue;
            }
        } catch (SQLException | HeadlessException e) {
            e.printStackTrace();
        }
        return -1;
    }

}