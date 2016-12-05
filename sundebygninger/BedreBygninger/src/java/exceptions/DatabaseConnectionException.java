package exceptions;

/**
 * Is used to show a database connection exception has ocurred.
 */
public class DatabaseConnectionException extends Exception {
 
    public DatabaseConnectionException(String message) {
        super(message);
    }
    
}