/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src_code;

import java.util.Random;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author cchimbadzwa
 */
public class controller {
    
    public static String unique_id() {
        String id;
        Random rng = new Random();
        int len = 4;
        boolean nonUnique = false;

        do {
            id = "";
            for (int c = 0; c < len; c++) {
                id += ((Integer) rng.nextInt(10)).toString();
            }

        }
        while (nonUnique);
        System.out.println("Unique User ID is : " + id);
        return id;
    }
    
    public static String getDate(){
        LocalDate myObj = LocalDate.now(); // Create a date object
//        System.out.println(myObj); // Display the current date
        
        return myObj.toString();
    }
    
    public static boolean addUser(
            String uname, String name, String sname, String natid, 
            String email, String dptmnt, String phone, String role, String gender, String pwd) {
        Connection con = null;
        CallableStatement csmt = null;
        boolean t = true;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into users VALUES (0,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            csmt.setString(1, name);
            csmt.setString(2, sname);
            csmt.setString(3, natid);
            csmt.setString(4, email);
            csmt.setString(5, uname);
            csmt.setString(6, gender);
            csmt.setString(7, phone);
            csmt.setString(8, role);
            csmt.setString(9, pwd);
            csmt.setString(10, "SU" + unique_id());
            csmt.setString(11, dptmnt);
            csmt.setString(12, "active");
            csmt.setString(13, getDate());

            t = csmt.execute();
            System.out.println("User added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("User not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            t = false;
        }

        return t;
    }
    
}
