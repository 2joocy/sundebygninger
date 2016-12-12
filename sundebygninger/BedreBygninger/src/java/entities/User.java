package entities;

import java.util.Date;

public class User {
    private final String date;
    private final String businessName, fullName, phone, email, status;
    private final int idUser;
    
    public User(int idUser, String email, String businessName, String phone, String status, String fullName, String dateCreated){
       this.idUser = idUser;
       this.email = email;
       this.businessName = businessName;
       this.phone = phone;
       this.status = status;
       this.fullName = fullName;
       this.date = dateCreated;
    }
    
    @Override
    public String toString(){
        return this.email + " : "+ this.businessName + " : " + this.fullName;
    }
    
    public String getDate() {
        return date;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public int getIdUser() {
        return idUser;
    }
    
    
}