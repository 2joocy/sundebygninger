package DbHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

/**
 * This class contains methods and functions that upload image data to our database.
 */
public class ImageHandler {
 
    Connection conn;
    
    public ImageHandler() throws ClassNotFoundException, SQLException {
        conn = DBConnection.getConnection();
    }
    
    public ImageHandler(Connection conn) {
        this.conn = conn;
    }
   
    /**
     * Retrieves a default image if no database connection can be found, or the picture ID requested does not exist.
     * @return Default "error-handling" image.
     * @throws IOException Throws IOException if the file cannot be found on the sysstem.
     */
    public BufferedImage getDefaultImage() throws IOException {
        URL path = getClass().getClassLoader().getResource("resources\\images\\questionmark.jpg");
        return ImageIO.read(path);
    }
    
    /**
     * Uploads an image to the database with specified data.
     * @param description A small description of the image.
     * @param type Which file format the image is.
     * @param filePart The image data.
     * @return Returns the image ID when it is uploaded to the database.
     */
    public int uploadImage(String description, String type, Part filePart) {
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
    
    /*
    public int uploadImage(String description, String type, Part filePart) {
        try {
            String sql = "INSERT INTO picture (description, type, image) VALUES (?, ?, ?)";
            PreparedStatement prepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InputStream inputStream = null;
            InputStreamReader inReader = null;
            BufferedReader bufReader = null;
            //InputStream inputThumb = null;
            if (filePart != null) {
                JOptionPane.showMessageDialog(null, "Filepart != null");
                inputStream = filePart.getInputStream();
                inReader = new InputStreamReader(inputStream);
                bufReader = new BufferedReader(inReader);
                bufReader.mark(16100000);
                JOptionPane.showMessageDialog(null, "Inputstream supports mark: " + inputStream.markSupported());

                if (inputStream != null) {
                    JOptionPane.showMessageDialog(null, "inputStream != null");
                    prepared.setString(1, description);
                    prepared.setString(2, type);
                    prepared.setBlob(3, getInputStream(bufReader));
                    prepared.executeUpdate();
                    ResultSet rs = prepared.getGeneratedKeys();
                    if (rs.next()) {
                        int fkKey = rs.getInt(1);

                        JOptionPane.showMessageDialog(null, "Uploaded image, fkKey: " + fkKey);

                        sql = "UPDATE picture SET thumbnail = ? WHERE idPicture = ?";
                        bufReader.reset();
                        
                        for (int i = 0; i < 5; i++) {
                            int length = 10 + 10*i;
                            String data = getData(getInputStream(bufReader), length);
                            JOptionPane.showMessageDialog(null, data);
                        }
                        bufReader.reset();
                        prepared = conn.prepareStatement(sql);

                        JOptionPane.showMessageDialog(null, "Reset the inputstream");

                        BufferedImage image = ImageIO.read(getInputStream(bufReader));

                        JOptionPane.showMessageDialog(null, "Image is null? " + (image == null));
                        
                        BufferedImage thumbnail = resize(image, 50, 50);
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

                        ImageIO.write(thumbnail, type, byteOut);
                        InputStream is = new ByteArrayInputStream(byteOut.toByteArray());
                        JOptionPane.showMessageDialog(null, "New inputStream created");

                        prepared.setBlob(1, is);
                        prepared.setInt(2, fkKey);
                        prepared.executeUpdate();

                        return fkKey;
                    }

                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    */
    
    public String getData(InputStream in, int len) throws IOException {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) in.read();
        }
        return new String(bytes);
    }
    
    public InputStream getInputStream(BufferedReader buffered) throws IOException {
        
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = buffered.readLine()) != null) {
            builder.append(line);
        }
        return new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
    }
    
    public void testInputStream(InputStream in) throws IOException {
        char c = (char) in.read();
        while (c != -1) {
            System.out.print("" + c);
            c = (char) in.read();
        }
        System.out.println("");
    }
    
    public byte[] getData(InputStream input) throws IOException {
        byte[] bytes = new byte[input.available()];
        int count = 0;
        byte b = (byte) input.read();
        while (b > 0) {
            bytes[count] = b;
        }
        return bytes;
    }
    
    public BufferedImage scale(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }  
    
    public static BufferedImage resize(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
    
    public BufferedImage getImageFromPart(Part imagePart) throws IOException {
        InputStream in = imagePart.getInputStream();
        return ImageIO.read(in);
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
    
    public void uploadThumbnail(int pictureID, String type, ByteArrayOutputStream os) throws IOException {
        try {
            String sql = "UPDATE picture SET thumbnail=? WHERE idPicture=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            for (int i = 0; i < is.available(); i++) {
                String newS = "";
                int count = 0;
                if (count > 10) {
                    JOptionPane.showMessageDialog(null, newS);
                }
                newS += (char) + is.read();
            }
            prepared.setBlob(1, is);
            prepared.setInt(2, pictureID);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "[uploadThumbnail] Error ocurred! " + e.getMessage());
        }
    }
 
    public void uploadThumbnail(int pictureID, String type, BufferedImage img) throws IOException {
        try {
            String sql = "UPDATE picture SET thumbnail=? WHERE idPicture=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) img, type, os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            for (int i = 0; i < is.available(); i++) {
                String newS = "";
                int count = 0;
                if (count > 10) {
                    JOptionPane.showMessageDialog(null, newS);
                }
                newS += (char) + is.read();
            }
            prepared.setBlob(1, is);
            prepared.setInt(2, pictureID);
            prepared.executeUpdate();
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "[uploadThumbnail] Error ocurred! " + e.getMessage());
        }
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