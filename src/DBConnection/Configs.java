/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Configs 
{
    static PreparedStatement pst=null;
    private final String url="";
    private static Statement stmt=null;
    private static Configs handler;
    public static Connection con=null;
    
    public static Connection getConnection()
    {
        return con;
    }
    
    private Configs()
    {
        createConnection();
    }
    public static Configs getInstance()
    {
        if(handler==null)
        {
            handler=new Configs();
        }
        return handler;
    }
    void createConnection()
    {
        try 
        {       
            Class.forName("com.mysql.jdbc.Driver");       
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            if(con!=null)
                System.out.println("Connected");
        }
        catch (SQLException ex) 
        {
            System.out.println(ex.toString());
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Class.forName("com.mysql.jdbc.Driver");
        
    }
   
    
    public PreparedStatement execQueryPrep(String query){
        try {
            
            pst=con.prepareStatement(query);
            //pst.executeUpdate();
            return pst;
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            return pst;
        }
    }
    public ResultSet execQuery(String query){
        ResultSet rs=null;
        
        try {
            stmt=con.createStatement();
            rs=stmt.executeQuery(query);
            //return rs;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }finally{
            
        }
        
        return rs;
    }
    public boolean execAction(String query) {
        System.out.println(query);
        try {
            stmt=con.createStatement();
             stmt.execute(query);
             System.out.println("AFter execution");
             return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }finally{
            
        }
       
    }
}
