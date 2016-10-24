/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author CHRIS
 */
public class Report {
 
    private int idReport, idPictureRoof, idPictureOuterRoof, idEmployee;
    private String buildingUsage, roofText, outerWallText, buildingResponsible;
    private boolean roofRemarks, outerWallRemarks;

    public Report(int idReport, int idPictureRoof, int idPictureOuterRoof, int idEmployee, String buildingUsage, String roofText, String outerWallText, String buildingResponsible, boolean roofRemarks, boolean outerWallRemarks) {
        this.idReport = idReport;
        this.idPictureRoof = idPictureRoof;
        this.idPictureOuterRoof = idPictureOuterRoof;
        this.idEmployee = idEmployee;
        this.buildingUsage = buildingUsage;
        this.roofText = roofText;
        this.outerWallText = outerWallText;
        this.buildingResponsible = buildingResponsible;
        this.roofRemarks = roofRemarks;
        this.outerWallRemarks = outerWallRemarks;
    }

    public int getIdReport() {
        return idReport;
    }

    public int getIdPictureRoof() {
        return idPictureRoof;
    }

    public int getIdPictureOuterRoof() {
        return idPictureOuterRoof;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getBuildingUsage() {
        return buildingUsage;
    }

    public String getRoofText() {
        return roofText;
    }

    public String getOuterWallText() {
        return outerWallText;
    }

    public String getBuildingResponsible() {
        return buildingResponsible;
    }

    public boolean isRoofRemarks() {
        return roofRemarks;
    }

    public boolean isOuterWallRemarks() {
        return outerWallRemarks;
    }
    
}