/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHandler;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

/**
 *
 * @author CHRIS
 */
public class ImageHandler {
 
    
    public static String getImageHTML(int id) {
        return "<img src=\"ImageServlet?id=" + id + "\"/>";
    }

    public static String getImageHTML(int id, int width, int height) {
        return "<img src=\"ImageServlet?id=" + id + "\" height=\"" + height + "\" width=\"" + width + "\"/>";
    }

    public static int uploadImage(Connection conn, String description, String type, Part filePart) {
        try {
            String sql = "INSERT INTO picture (description, type, image) VALUES (?, ?, ?)";
            PreparedStatement prepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InputStream inputStream = null;
            if (filePart != null) {
                inputStream = filePart.getInputStream();
            }
            if (inputStream != null) {
                prepared.setString(1, description);
                prepared.setString(2, type);
                prepared.setBlob(3, inputStream);
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
    
    public static int uploadMainImage(Connection conn, String description, String type, Part filePart, int buildingID, int fk_idMainPicture) {
        try {
            String sql = "DELETE FROM picture WHERE idPicture=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, fk_idMainPicture);
            prepared.executeUpdate();
            int returnValue = uploadImage(conn, description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE building SET fk_idMainPicture = ? WHERE idBuilding = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, buildingID);
                prepared.executeUpdate();
                return returnValue;
            } else {
                JOptionPane.showMessageDialog(null, "[ImageHandler.uploadMainImage] Error, returnValue: " + returnValue);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "[ImageHandler.uploadMainImage] " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static int uploadRoofImage(Connection conn, String description, String type, Part filePart, int reportID) {
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
            int returnValue = uploadImage(conn, description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE report SET fk_idPictureRoof = ? WHERE idReport = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, reportID);
                prepared.executeUpdate();
                return returnValue;
            } else {
                JOptionPane.showMessageDialog(null, "[ImageHandler.uploadRoofImage] Error, returnValue: " + returnValue);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "[ImageHandler.uploadRoofImage] " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static int uploadOuterRoofImage(Connection conn, String description, String type, Part filePart, int reportID) {
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
            int returnValue = uploadImage(conn, description, type, filePart);
            if (returnValue >= 0) {
                sql = "UPDATE report SET fk_idPictureOuterRoof = ? WHERE idReport = ?";
                prepared = conn.prepareStatement(sql);
                prepared.setInt(1, returnValue);
                prepared.setInt(2, reportID);
                prepared.executeUpdate();
                return returnValue;
            } else {
                JOptionPane.showMessageDialog(null, "[ImageHandler.uploadOuterRoofImage] Error, returnValue: " + returnValue);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "[ImageHandler.uploadOuterRoofImage] " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

}