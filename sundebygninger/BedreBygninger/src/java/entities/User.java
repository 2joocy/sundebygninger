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
    private String businessName, email;
    private boolean confirmed;
    
    public User(String strName, String strEmail, boolean tempCon){
        this.businessName = strName;
        this.email = strEmail;
        this.confirmed = tempCon;
    }
    
    public User(){
        this.businessName = "";
        this.email = "";
        this.confirmed = false;
    }
}
