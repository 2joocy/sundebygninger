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
public class User {
    
    private final String businessName, email, confirmed;
    private final int id;
    
    public User(int id, String strName, String strEmail, String tempCon){
        this.id = id;
        this.businessName = strName;
        this.email = strEmail;
        this.confirmed = tempCon;
    }

    public int getID(){
        return id;
    }
    
    public String getBusinessName() {
        return businessName;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmed() {
        return confirmed;
    }
    
}