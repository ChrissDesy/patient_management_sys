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
    public static String empId, userRole, patId, loggedUser, visitId;
    public static int prescId;
    
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
                loggedUser = rs.getString("uname");
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
            csmt.setString(4, natid);
            csmt.setString(3, email);
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
            csmt = con.prepareCall("INSERT into visits VALUES (0,?,?,?,?)");
            csmt.setString(1, patId);
            csmt.setString(3, "active");
            csmt.setString(2, getDate());
            csmt.setString(4, "admission");

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
    
    public static boolean addPrecheck(
            String patid, String visitid, String temp, String blood, String weight, String other) {
        Connection con = null;
        CallableStatement csmt = null;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into precheck VALUES (0,?,?,?,?,?,?,?,?,?)");
            csmt.setString(1, patid);
            csmt.setString(2, visitid);
            csmt.setString(3, temp);
            csmt.setString(4, blood);
            csmt.setString(5, weight);
            csmt.setString(6, other);
            csmt.setString(8, "active");
            csmt.setString(7, getDate());
            csmt.setString(9, getLoggedUser());

            csmt.execute();
            System.out.println("Precheck added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Precheck not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static String getLoggedUser() {
        try{
            return loggedUser.equals("") ? "-" : loggedUser;
        }
        catch(Exception ex){
            System.out.println(ex);
            return "-";
        }
    }
    
    public static boolean addConsultation(
            String patid, String visitid, String description, int prescription) {
        Connection con = null;
        CallableStatement csmt = null;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into consultation VALUES (0,?,?,?,?,?,?,?)");
            csmt.setString(1, patid);
            csmt.setString(2, visitid);
            csmt.setString(5, description);
            csmt.setInt(6, prescription);
            csmt.setString(7, "active");
            csmt.setString(3, getDate());
            csmt.setString(4, getLoggedUser());

            csmt.execute();
            System.out.println("Consultation added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Consultation not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static boolean addPrescription(
            String patid, String visitid, String desc) {
        Connection con = null;
        CallableStatement csmt = null;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into prescription VALUES (0,?,?,?,?,?,?,?)");
            csmt.setString(1, patid);
            csmt.setString(2, visitid);
            csmt.setString(5, desc);
            csmt.setString(7, getLoggedUser());
            csmt.setString(4, "active");
            csmt.setString(3, getDate());
            csmt.setString(6, getLoggedUser());

            csmt.execute();
            
            System.out.println("Prescription added successfully...!!");
            
            prescId = determinePrescriptionId();
            
            return true;

        } catch (Exception e) {
            System.out.println("Prescription not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static boolean addProcedure(
            String patid, String visitid, String description, String dpt, int presc) {
        Connection con = null;
        CallableStatement csmt = null;
        
        try {
            con = DBConnect.getConnection();
            csmt = con.prepareCall("INSERT into procedures VALUES (0,?,?,?,?,?,?,?)");
            csmt.setString(1, patid);
            csmt.setString(2, visitid);
            csmt.setString(7, description);
            csmt.setString(4, dpt);
            csmt.setString(6, "active");
            csmt.setString(3, getDate());
            csmt.setString(5, getLoggedUser());
            csmt.setInt(8, presc);

            csmt.execute();
            System.out.println("Procedure added successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Procedure not Added.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }
    }
    
    public static int determinePrescriptionId(){
        Connection con = null;
        Statement st = null;
        int theId = 0;
        
        try {
            con= DBConnect.getConnection();
            st=con.createStatement();
            String query = "select max(id) as myId from prescription";
            ResultSet rst=st.executeQuery(query);
            while(rst.next()){               
                theId = rst.getInt("myId");
            }
            
            return theId;

        } catch (Exception e) {
            System.out.println("Failed to get prescription.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return theId;
        }
    }
    
    public static int getPrescId() {
        return prescId;
    }
    
    public static void setVisId(String em) {
        visitId = em;
    }

    public static String getVisId() {
        return visitId;
    }
    
    public static void setPrescId(String em){
        prescId = Integer.parseInt(em);
    }
    
    public static boolean approvePrescription(
            String status, String presc) {
        Connection con = null;
        Statement st = null;
        
        try {
            con = DBConnect.getConnection();
            st = con.createStatement();
            
            String query = "UPDATE prescription SET status='"+status+"', approved_by='"+getLoggedUser()+"' "
                    + "WHERE id='"+presc+"'";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();
            
            System.out.println("Prescription updated successfully...!!");
            return true;

        } catch (Exception e) {
            System.out.println("Prescription not updated.\n\tError: " + e);
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
            return false;
        }

    }
    
    
    
}
