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
    
    private final String businessName, email, status;
    private final int id;
    
    public User(int id, String username, String businessName, String status){
        this.id = id;
        this.businessName = businessName;
        this.email = username;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
    
}