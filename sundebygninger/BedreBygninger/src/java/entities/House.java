/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author William-PC
 */
public class House {
    private int houseId,id;
    private String houseName,address,matrikelNr,buildingGrade;
    private double buildingArea;
    
    public House(int houseId, int id, String houseName, String address, String matrikelNr, String buildingGrade, double buildingArea){
        this.houseId = houseId;
        this.id = id;
        this.houseName = houseName;
        this.address = address;
        this.matrikelNr = matrikelNr;
        this.buildingGrade = buildingGrade;
        this.buildingArea = buildingArea;
        
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(String matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public String getBuildingGrade() {
        return buildingGrade;
    }

    public void setBuildingGrade(String buildingGrade) {
        this.buildingGrade = buildingGrade;
    }

    public double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(double buildingArea) {
        this.buildingArea = buildingArea;
    }
    
    
}
