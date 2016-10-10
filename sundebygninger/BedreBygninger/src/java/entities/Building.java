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

    private int idBuilding, fk_idUser, fk_idMainPicture, fk_idReport, builtYear;
    private String address, cadastral, buildingGrade, area, zipcode, city, condition, service, extraText;
    private Date dateCreated;
    private double buildingArea;

    public Building(int idBuilding, String address, String cadastral, String buildingGrade, 
                    String area, String zipcode, String city, String condition, String service, 
                    String extraText, double buildingArea, int builtYear, int fk_idUser, 
                    int fk_MainPicture, int fk_idReport, Date dateCreated) {
           
        this.idBuilding = idBuilding;
        this.address = address;
        this.cadastral = cadastral;
        this.buildingGrade = buildingGrade;
        this.area = area;
        this.zipcode = zipcode;
        this.city = city;
        this.condition = condition;
        this.service = service;
        this.extraText = extraText;
        this.buildingArea = buildingArea;
        this.builtYear = builtYear;
        this.fk_idUser = fk_idUser;
        this.fk_idReport = fk_idReport;
        this.fk_idMainPicture = fk_idMainPicture;
        this.dateCreated = dateCreated;
        
        
    }

}
