package servlet;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Esli
 */
public class Conexion {
    private String USERNAME ="root";
    private String PASSWORD ="";
    private String HOST ="localhost";
    private String PORT ="3306";
    private String DATABASE="consultoria";
    private String  CLASSNAME="com.mysql.jdbc.Driver";
    private String URL ="jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
    private Connection con;
            
    
    public Conexion (){
        try {
            Class.forName(CLASSNAME);
            con= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR" +e);
        } catch (SQLException ex) {
            System.out.println("ERROR"+ ex);
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Connection getConnection() {
        return con;
    }
    
    public static void main(String[] args) {
        Conexion con= new Conexion();
    }
    

   
    
}
