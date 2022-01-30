/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src_code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author cchimbadzwa
 */
public class DBConnect {
    private static Connection con;
    private static String port, uname, pwd;
    private static boolean read;
    
    public static Connection getConnection(){
        if(con == null){
            JOptionPane.showMessageDialog(null, "No database connection found.", "ERROR", 0);
        }
        
        return con;
    }
    
    public static boolean readSettings() throws IOException {
        // loads properties from file
        File configFile = new File("config.properties");
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
           
            String host = props.getProperty("host");
            port = props.getProperty("port");
            uname = props.getProperty("user");
            if (props.getProperty("pass") == null) {
                pwd = "";
            } else {
                pwd = props.getProperty("pass");
            }

            // System.out.println( host + " " + port + " " + uname + " " + pwd);
            reader.close();
            
            if(port.equals("") || uname.equals("")){
                //re = true;
                return false;
            }
            else{
                return true; 
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found with Error: " + ex);
            return false;
        } catch (IOException ex) {
            System.out.print("Error is: " + ex);
            return false;
        }
    }
    
    public static boolean createCon() throws IOException {        
//        try{
//            read = readSettings();
//        } 
//        catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Error: " + ex);
//        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/pmshsm", uname, pwd);
            // System.out.println("Connection established......");
            return true;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return false;
        }
    }
    
}
