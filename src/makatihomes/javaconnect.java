/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author GameDev
 */
public class javaconnect {
    Connection conn = null;
    public static Connection connectdb(){
       try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.254.101:3306/makatihomes2", "root", "pass");
            return conn;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
      
    }
    
}
