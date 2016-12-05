package entities;

/**
 * Custom building object, used when storing or retrieving data from the database, as it is encapsulated properly.
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

    /**
     * Returns the address of the building you are calling the function on.
     * @return Building address.
     */
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
    
    /**
     * Checks whether the object o, has the same properties as the building, whose equals function is being called upon.
     * @param o Another building object.
     * @return True: if o's properties are the same as this buildings, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Building) {
            Building check = (Building) o;
            System.out.println("o is instanceof Building");
            System.out.println(check.getAddress() + " vs " + address + ", " + check.getFk_idUser() + " vs " + fk_idUser);
            return check.getAddress().equals(address) &&
                   check.getZipcode().equals(zipcode) &&
                   check.getFk_idUser() == fk_idUser;
        } else {
            System.out.println("Object is not instanceof Building");
        }
        return false;
    }
    
}
