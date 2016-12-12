package entities;

public class Report {

    private int idReport;
    private String buildingUsage;
    private boolean roofRemarks;
    private int fk_idPictureRoof;
    private String roofText;
    private boolean outerWallRemarks;
    private int fk_idPictureOuterRoof;
    private String outerWallText;
    private int fk_idEmployee;
    private String buildingResponsible;

    public Report(int idReport, String buildingUsage, boolean roofRemarks, int fk_idPictureRoof, String roofText, boolean outerWallRemarks, int fk_idPictureOuterRoof, String outerWallText, int fk_idEmployee, String buildingResponsible) {
        this.idReport = idReport;
        this.buildingUsage = buildingUsage;
        this.roofRemarks = roofRemarks;
        this.fk_idPictureRoof = fk_idPictureRoof;
        this.roofText = roofText;
        this.outerWallRemarks = outerWallRemarks;
        this.fk_idPictureOuterRoof = fk_idPictureOuterRoof;
        this.outerWallText = outerWallText;
        this.fk_idEmployee = fk_idEmployee;
        this.buildingResponsible = buildingResponsible;
    }

    public int getIdReport() {
        return idReport;
    }

    public String getBuildingUsage() {
        return buildingUsage;
    }

    public boolean getRoofRemarks() {
        return roofRemarks;
    }

    public int getFk_idPictureRoof() {
        return fk_idPictureRoof;
    }

    public String getRoofText() {
        return roofText;
    }

    public boolean isOuterWallRemarks() {
        return outerWallRemarks;
    }

    public int getFk_idPictureOuterRoof() {
        return fk_idPictureOuterRoof;
    }

    public String getOuterWallText() {
        return outerWallText;
    }

    public int getFk_idEmployee() {
        return fk_idEmployee;
    }

    public String getBuildingResponsible() {
        return buildingResponsible;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public void setBuildingUsage(String buildingUsage) {
        this.buildingUsage = buildingUsage;
    }

    public void setRoofRemarks(boolean roofRemarks) {
        this.roofRemarks = roofRemarks;
    }

    public void setFk_idPictureRoof(int fk_idPictureRoof) {
        this.fk_idPictureRoof = fk_idPictureRoof;
    }

    public void setRoofText(String roofText) {
        this.roofText = roofText;
    }

    public void setOuterWallRemarks(boolean outerWallRemarks) {
        this.outerWallRemarks = outerWallRemarks;
    }

    public void setFk_idPictureOuterRoof(int fk_idPictureOuterRoof) {
        this.fk_idPictureOuterRoof = fk_idPictureOuterRoof;
    }

    public void setOuterWallText(String outerWallText) {
        this.outerWallText = outerWallText;
    }

    public void setFk_idEmployee(int fk_idEmployee) {
        this.fk_idEmployee = fk_idEmployee;
    }

    public void setBuildingResponsible(String buildingResponsible) {
        this.buildingResponsible = buildingResponsible;
    }
    
    
    
    
}