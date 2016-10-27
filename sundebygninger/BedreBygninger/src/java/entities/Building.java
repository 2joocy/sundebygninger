/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 *
 * @author William-PC
 */
public class Building {

    private int idBuilding, fk_idUser, fk_idMainPicture, fk_idReport;
    private String address, cadastral, area, zipcode, city, condition, service, extraText,builtYear;
    private String dateCreated;
    private double buildingArea;

    public Building(int idBuilding, String address, String cadastral,
                    String area, String zipcode, String city, String condition, String service, 
                    String extraText, String builtYear, int fk_idUser, 
                    int fk_idMainPicture, int fk_idReport, String dateCreated) {
           
        this.idBuilding = idBuilding;
        this.address = address;
        this.cadastral = cadastral;
        this.area = area;
        this.zipcode = zipcode;
        this.city = city;
        this.condition = condition;
        this.service = service;
        this.extraText = extraText;
        this.builtYear = builtYear;
        this.fk_idUser = fk_idUser;
        this.fk_idReport = fk_idReport;
        this.fk_idMainPicture = fk_idMainPicture;
        this.dateCreated = dateCreated;
        
        
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public int getFk_idUser() {
        return fk_idUser;
    }

    public int getFk_idMainPicture() {
        return fk_idMainPicture;
    }

    public int getFk_idReport() {
        return fk_idReport;
    }

    public String getAddress() {
        return address;
    }

    public String getCadastral() {
        return cadastral;
    }

    public String getArea() {
        return area;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getCondition() {
        return condition;
    }

    public String getService() {
        return service;
    }

    public String getExtraText() {
        return extraText;
    }

    public String getBuiltYear() {
        return builtYear;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public double getBuildingArea() {
        return buildingArea;
    }
    
    
}
