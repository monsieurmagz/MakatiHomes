/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.io.FileOutputStream;
import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.applet.*;  
import java.awt.*;  
import static java.lang.Thread.sleep;
import java.util.*;  
import java.text.*;  
import java.util.Date;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author GameDev
 */
public class Maintenance extends javax.swing.JInternalFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    /**
     * Creates new form Maitenance
     */
    public Maintenance() {
        initComponents();
        conn = javaconnect.connectdb();
        ShowRentalPrice();
        ShowPenaltyPrice();
        DisableRentalSave();
        DisabledPenaltySave();
        removetitlebar();
        ShowEMaintenance();
        ShowCategory();
        ShowCategoryCombo();
    }
    public void removetitlebar(){
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }
    private void ShowRentalPrice(){
        try{
            String sql="Select * from monthlyrentals";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String rentalpresyo = rs.getString("monthlyamount");
                renta.setText(rentalpresyo);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void DisableRentalSave(){
        try{
            String sql ="Select * from monthlyrentals where monthlyamount IS NOT NULL";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                rentalsave.setEnabled(false);
            }else{
                rentalsave.setEnabled(true);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void ShowPenaltyPrice(){
        try{
            String sql ="Select * from monthlypenalty";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String penalti = rs.getString("penaltyAmount");
                penalty.setText(penalti);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void DisabledPenaltySave(){
        try{
            String sql = "Select * from monthlypenalty where penaltyAmount IS NOT NULL";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                penaltysave.setEnabled(false);
            }else{
                penaltysave.setEnabled(true);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void ShowEMaintenance(){
        try{
            String sql ="Select fname as FirstName, mname as MiddleName, lname as LastName,contact as Contact,dob as DateOfBirth,address as Address from emaintenance";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            etable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ShowCategory(){
        try{
            String sql = "Select categoryName as CategoryName from complaintcategory";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cattable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ShowCategoryCombo(){
        try{
            String sql = "Select * from complaintcategory";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String cate = rs.getString("categoryName");
                catcombo.addItem(cate);
                selectcat.addItem(cate);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void showCategoryCombo2(){
        try{
            if(selectcat.getSelectedItem().toString().equals("Show All")){
                
            String sql = "Select categoryName as CategoryName, complaintsubName as Complaint from complaintmaintenance ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            complainttable.setModel(DbUtils.resultSetToTableModel(rs));
            }else{
            String categorycombos = selectcat.getSelectedItem().toString();
            String sql = "Select categoryName as CategoryName, complaintsubName as Complaint from complaintmaintenance where categoryName = '"+categorycombos+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            complainttable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Main = new javax.swing.JPanel();
        RM = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        rentalupdate = new javax.swing.JButton();
        rentalsave = new javax.swing.JButton();
        rentalprice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        renta = new javax.swing.JLabel();
        PM = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        penalty = new javax.swing.JLabel();
        penaltyprice = new javax.swing.JTextField();
        penaltysave = new javax.swing.JButton();
        penaltyupdate = new javax.swing.JButton();
        EM = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        mname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        contact = new javax.swing.JTextField();
        dob = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        etable = new javax.swing.JTable();
        emaintenancesave = new javax.swing.JButton();
        CM = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        category = new javax.swing.JTextField();
        addcat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        cattable = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        catcombo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        complain = new javax.swing.JTextField();
        addcomplaint = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        complainttable = new javax.swing.JTable();
        selectcat = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/maintenance.png"))); // NOI18N
        jButton1.setText("Rental Maintenance");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/maintenance.png"))); // NOI18N
        jButton2.setText("Penalty Maintenance");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/maintenance.png"))); // NOI18N
        jButton3.setText("Employee Maintenance");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/maintenance.png"))); // NOI18N
        jButton4.setText("Complaint Maintenance");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main.setBackground(new java.awt.Color(255, 255, 255));
        Main.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        rentalupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        rentalupdate.setText("Update");
        rentalupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalupdateActionPerformed(evt);
            }
        });

        rentalsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        rentalsave.setText("Save");
        rentalsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalsaveActionPerformed(evt);
            }
        });

        rentalprice.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel4.setText("Rental Price:");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel2.setText("Current Rental Price:");

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Rental Maintenance");

        renta.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        renta.setText("No Current Price");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(354, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(185, 185, 185))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rentalupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rentalsave))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rentalprice, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(renta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(renta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rentalprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rentalsave)
                    .addComponent(rentalupdate))
                .addContainerGap(272, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RMLayout = new javax.swing.GroupLayout(RM);
        RM.setLayout(RMLayout);
        RMLayout.setHorizontalGroup(
            RMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RMLayout.setVerticalGroup(
            RMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Main.add(RM, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("Penalty Maintenance");

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel6.setText("Current Penalty Price:");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel7.setText("Penalty Price:");

        penalty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        penalty.setText("No Current Penalty Price");

        penaltyprice.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        penaltysave.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        penaltysave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        penaltysave.setText("Save");
        penaltysave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                penaltysaveActionPerformed(evt);
            }
        });

        penaltyupdate.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        penaltyupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        penaltyupdate.setText("Update");
        penaltyupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                penaltyupdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(178, 178, 178))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(penaltyupdate)
                        .addGap(18, 18, 18)
                        .addComponent(penaltysave))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(penalty, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(penaltyprice)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(91, 91, 91)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(penalty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(penaltyprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(penaltysave)
                    .addComponent(penaltyupdate))
                .addContainerGap(279, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PMLayout = new javax.swing.GroupLayout(PM);
        PM.setLayout(PMLayout);
        PMLayout.setHorizontalGroup(
            PMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PMLayout.setVerticalGroup(
            PMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Main.add(PM, "card3");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("Employee Maintenance");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("First Name:");

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Middle Name:");

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Last Name:");

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Contact:");

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Date of Birth:");

        jLabel15.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Address:");

        fname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        mname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        lname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        contact.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        contact.setText("+63");

        dob.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        dob.setText("yyyy-MM-dd");

        address.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        etable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(etable);

        emaintenancesave.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        emaintenancesave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        emaintenancesave.setText("Save");
        emaintenancesave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emaintenancesaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(162, 162, 162))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(emaintenancesave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emaintenancesave))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout EMLayout = new javax.swing.GroupLayout(EM);
        EM.setLayout(EMLayout);
        EMLayout.setHorizontalGroup(
            EMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        EMLayout.setVerticalGroup(
            EMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Main.add(EM, "card4");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("Complaint Maintenance");

        jLabel17.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel17.setText("Category:");

        category.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        addcat.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        addcat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        addcat.setText("Add Category");
        addcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addcatActionPerformed(evt);
            }
        });

        cattable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(cattable);

        jLabel18.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel18.setText("Select Category:");

        catcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        catcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));

        jLabel19.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel19.setText("Complaint:");

        complain.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        addcomplaint.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        addcomplaint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        addcomplaint.setText("Add Complaint");
        addcomplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addcomplaintActionPerformed(evt);
            }
        });

        complainttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(complainttable);

        selectcat.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        selectcat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category", "Show All" }));
        selectcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectcatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(204, 204, 204))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addcat)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addcomplaint)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(catcombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(complain, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectcat, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addcat))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(selectcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(catcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(complain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addcomplaint))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout CMLayout = new javax.swing.GroupLayout(CM);
        CM.setLayout(CMLayout);
        CMLayout.setHorizontalGroup(
            CMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CMLayout.setVerticalGroup(
            CMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Main.add(CM, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(RM);        
        Main.repaint();
        Main.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(PM);        
        Main.repaint();
        Main.revalidate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(EM);        
        Main.repaint();
        Main.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(CM);        
        Main.repaint();
        Main.revalidate();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void rentalsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalsaveActionPerformed
        try{
            if(rentalprice.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please enter Monthly Rental Price","Monthyly Rental Price",JOptionPane.ERROR_MESSAGE);
            }else{
                String sql ="Insert into monthlyrentals (monthlyamount) values (?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, rentalprice.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Monthly Rental Price Added","Monthly Rental Price",JOptionPane.INFORMATION_MESSAGE);
                ShowRentalPrice();
                rentalprice.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_rentalsaveActionPerformed

    private void rentalupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalupdateActionPerformed
        try{
            if(rentalprice.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Monthly Rental Price","Error",JOptionPane.ERROR_MESSAGE);
            }else if(renta.getText().equals("No Current Price")){
                JOptionPane.showMessageDialog(null, "Please Enter Monthly Rental Price","Error",JOptionPane.ERROR_MESSAGE);
            }else{
            String curprice = renta.getText();
            String newprice = rentalprice.getText();
            String sql="Update monthlyrentals set monthlyamount ='"+newprice+"' where monthlyamount ='"+curprice+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Monthly Rental Price Updated","Montly Rental Price",JOptionPane.INFORMATION_MESSAGE);
            ShowRentalPrice();
            rentalprice.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_rentalupdateActionPerformed

    private void penaltysaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penaltysaveActionPerformed
        try{
            if(penaltyprice.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Rental Penalty","Penalty Price",JOptionPane.ERROR_MESSAGE);
            }else{
                String sql = "Insert into monthlypenalty (penaltyAmount) values (?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, penaltyprice.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Rental Penalty Price Added","Penalty Price",JOptionPane.INFORMATION_MESSAGE);
                ShowPenaltyPrice();
                penaltyprice.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_penaltysaveActionPerformed

    private void penaltyupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penaltyupdateActionPerformed
        try{
            if(penalty.getText().equals("No Current Penalty Price")){
                JOptionPane.showMessageDialog(null, "Please Enter Penalty Price","Error",JOptionPane.ERROR_MESSAGE);
            }else if(penaltyprice.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Penalty Price","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                String curpenalty = penalty.getText();
                String newpenalty = penaltyprice.getText();
                String sql="Update monthlypenalty set penaltyAmount ='"+newpenalty+"' where penaltyAmount='"+curpenalty+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();        
                JOptionPane.showMessageDialog(null, "Rental Penalty Price Updated","Penalty Price",JOptionPane.INFORMATION_MESSAGE);
                ShowPenaltyPrice();
                penaltyprice.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       
    }//GEN-LAST:event_penaltyupdateActionPerformed

    private void emaintenancesaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emaintenancesaveActionPerformed
        try{
            String dateFormat = "\\d{4}-\\d{2}-\\d{2}";
            String dobb = dob.getText();
            if(fname.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter First Name","Error First Name",JOptionPane.ERROR_MESSAGE);
            }else if(mname.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Middle Name","Error Middle Name",JOptionPane.ERROR_MESSAGE);
            }else if(lname.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Last Name","Error Last Name",JOptionPane.ERROR_MESSAGE);
            }else if(contact.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Contact Number","Error Contact Number",JOptionPane.ERROR_MESSAGE);
            }else if(dob.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Birth Date","Error Birth Date",JOptionPane.ERROR_MESSAGE);
            }else if(!dobb.matches(dateFormat)){
                JOptionPane.showMessageDialog(null, "Error Birth Date Format","Error Birth Date Format",JOptionPane.ERROR_MESSAGE);
            }else if(address.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Address","Error Address",JOptionPane.ERROR_MESSAGE);
            }else{
                String fullname = ""+fname.getText()+" "+mname.getText()+" "+lname.getText()+"";
                String sql ="Insert into emaintenance (fname,mname,lname,fullName,contact,dob,address) values (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, fname.getText());
                pst.setString(2, mname.getText());
                pst.setString(3, lname.getText());
                pst.setString(4, fullname);
                pst.setString(5, contact.getText());
                pst.setString(6, dob.getText());
                pst.setString(7, address.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Employee Maintenance Added","Employee Maintenance",JOptionPane.INFORMATION_MESSAGE);
                ShowEMaintenance();
                fname.setText("");
                mname.setText("");
                lname.setText("");
                contact.setText("+63");
                dob.setText("yyyy-MM-dd");
                address.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_emaintenancesaveActionPerformed

    private void addcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addcatActionPerformed
        try{
            if(category.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Category Name","Category Name",JOptionPane.ERROR_MESSAGE);
            }else{
                String sql ="Insert into complaintcategory (categoryName) values (?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, category.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Complaint Category Added","Complaint Added",JOptionPane.INFORMATION_MESSAGE);
                ShowCategory();
                category.setText("");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_addcatActionPerformed

    private void addcomplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addcomplaintActionPerformed
        try{
            if(catcombo.getSelectedItem().toString().equals("Select Category")){
                JOptionPane.showMessageDialog(null, "Please Select Category","Error",JOptionPane.ERROR_MESSAGE);
            }else if(complain.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Enter Complaint","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                String categor = catcombo.getSelectedItem().toString();
                String sql ="Select * from complaintcategory where categoryName ='"+categor+"'";
                pst =conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String categoryid = rs.getString("catcomplaintID");
                    String categorname = rs.getString("categoryName");
                    String sql1 = "Insert into complaintmaintenance (catcomplaintID,categoryName,complaintsubName) values (?,?,?)";
                    pst = conn.prepareStatement(sql1);
                    pst.setString(1, categoryid);
                    pst.setString(2, categorname);
                    pst.setString(3, complain.getText());
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "complaint added to category '"+categor+"'","Complaint",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_addcomplaintActionPerformed

    private void selectcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectcatActionPerformed
        showCategoryCombo2();
    }//GEN-LAST:event_selectcatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CM;
    private javax.swing.JPanel EM;
    private javax.swing.JPanel Main;
    private javax.swing.JPanel PM;
    private javax.swing.JPanel RM;
    private javax.swing.JButton addcat;
    private javax.swing.JButton addcomplaint;
    private javax.swing.JTextField address;
    private javax.swing.JComboBox<String> catcombo;
    private javax.swing.JTextField category;
    private javax.swing.JTable cattable;
    private javax.swing.JTextField complain;
    private javax.swing.JTable complainttable;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField dob;
    private javax.swing.JButton emaintenancesave;
    private javax.swing.JTable etable;
    private javax.swing.JTextField fname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField mname;
    private javax.swing.JLabel penalty;
    private javax.swing.JTextField penaltyprice;
    private javax.swing.JButton penaltysave;
    private javax.swing.JButton penaltyupdate;
    private javax.swing.JLabel renta;
    private javax.swing.JTextField rentalprice;
    private javax.swing.JButton rentalsave;
    private javax.swing.JButton rentalupdate;
    private javax.swing.JComboBox<String> selectcat;
    // End of variables declaration//GEN-END:variables
}
