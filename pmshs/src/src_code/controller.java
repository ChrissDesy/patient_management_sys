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
    public static String empId, userRole, patId;
    
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
    
    public static boolean editUser(
            String name, String sname, String type,
            String email, String dptmnt, String phone, String uname) {
        Connection con = null;
        Statement st = null;
        
        try {
            con = DBConnect.getConnection();
            st = con.createStatement();
            
            String query = "UPDATE users SET fname='"+name+"',"
                    + "lname='"+sname+"', email='"+email+"', department='"+dptmnt+"',"
                    + "phone='"+phone+"', type='"+type+"' "
                    + "WHERE uname='"+uname+"'";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();
            
            System.out.println("User updated successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("User not updated.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static void setEmpId(String em) {
        empId = em;
    }

    public static String getEmpId() {
        return empId;
    }
    
    public static boolean login(String userid, String pin) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM users WHERE uname = ? and password = ?");
            ps.setString(1, userid);
            ps.setString(2, pin);
            rs = ps.executeQuery();
            if (rs.next()) {
                userRole = rs.getString("type");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error is:\t" + e.toString());
            return false;
        }
    }
    
    public static String getEmpRole() {
        return userRole;
    }
    
    public static boolean addPatient(
            String kname, String name, String sname, String natid, String addr, 
            String email, String kemail, String phone, String kphone, String gender) {
        Connection con = null;
        CallableStatement csmt = null;
        boolean t = true;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into patients VALUES (0,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            csmt.setString(1, name);
            csmt.setString(2, sname);
            csmt.setString(3, natid);
            csmt.setString(4, email);
            csmt.setString(5, gender);
            csmt.setString(6, phone);
            csmt.setString(7, addr);
            csmt.setString(12, kemail);
            csmt.setString(13, kphone);
            csmt.setString(8, "PT" + unique_id());
            csmt.setString(11, kname);
            csmt.setString(10, "active");
            csmt.setString(9, getDate());

            t = csmt.execute();
            System.out.println("Patient added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Patient not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            t = false;
        }

        return t;
    }
    
    public static boolean addVisit(String patId){
        Connection con = null;
        CallableStatement csmt = null;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into visits VALUES (0,?,?,?)");
            csmt.setString(1, patId);
            csmt.setString(3, "active");
            csmt.setString(2, getDate());

            csmt.execute();
            System.out.println("Visit added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Visit not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static void setPatId(String em) {
        patId = em;
    }

    public static String getPatId() {
        return patId;
    }
    
    public static boolean editPatient(
            String kname, String name, String sname, String natid, String addr, 
            String email, String kemail, String phone, String kphone, String gender, String pt) {
        Connection con = null;
        Statement st = null;
        
        try {
            con = DBConnect.getConnection();
            st = con.createStatement();
            
            String query = "UPDATE patients SET fname='"+name+"', gender='"+gender+"',"
                    + "lname='"+sname+"', email='"+email+"', natid='"+natid+"', kin_name='"+kname+"',"
                    + "phone='"+phone+"', address='"+addr+"', kin_email='"+kemail+"', kin_phone='"+kphone+"' "
                    + "WHERE patid='"+pt+"'";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();
            
            System.out.println("Patient updated successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Patient not updated.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }

    }
    
    
    
}
