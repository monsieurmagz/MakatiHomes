/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;

import javax.swing.JRootPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.io.FileOutputStream;
import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.applet.*;  
import java.awt.*;  
import java.util.*;  
import java.text.*;  
import java.util.Date;
import net.proteanit.sql.DbUtils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.FileOutputStream;
import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.applet.*;  
import java.awt.*;  
import java.util.*;  
import java.text.*;  
import java.util.Date;
import net.proteanit.sql.DbUtils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author GameDev
 */
public class Complaint extends javax.swing.JInternalFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public static final String ACCOUNT_SID = "AC9cccf2c67541dab6eaef384e516d388a";
    public static final String AUTH_TOKEN = "ecfd96d905a7d2e4a42118f669dbec3e";
    
    /**
     * Creates new form Complaint
     */
    public Complaint() {
        initComponents();
        conn=javaconnect.connectdb();
        removetitlebar();
        Date();
        Building();
        TenantsTable();
        listEmployee();
        showCategory();
        Table();
    }
  public void removetitlebar(){
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }
  public void Date(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datess = year+"-"+(month+1)+"-"+day;
    }
          private void TenantsTable(){
        try{
            String buildingcombos = buildingcombo.getSelectedItem().toString();
            String sql ="Select buildingName as Building,unitNumber as Unit, fname as FirstName,mname as MiddleName,lname as LastName,contact as Contact from tenantsinformation where buildingName ='"+buildingcombos+"' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            tenantstable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        public void Building(){
            try{
                String sql = "Select * from buildings";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String building = rs.getString("buildingName");
                    buildingcombo.addItem(building);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void listEmployee(){
            try{
                String sql = "Select * from emaintenance";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String fullnames = rs.getString("fullName");
                    emaintenancecombo.addItem(fullnames);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void showCategory(){
            try{
                String sql = "Select * from complaintcategory";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String catname = rs.getString("categoryName");
                    categorycombo.addItem(catname);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void showComplaint(){
            try{
                complaintcombo.removeAllItems();
                String catname = categorycombo.getSelectedItem().toString();
                String sql = "Select * from complaintmaintenance where categoryName = '"+catname+"'";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String complaintsub = rs.getString("complaintsubName");
                    complaintcombo.addItem(complaintsub);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
            public void Table(){
        try{
            String sql = "Select  complaintID as ComplaintID,tenantsID as TenantsID,unitID as UnitID,complaint as Complaint from complaints where remarks = 'Incomplete'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();         
            complaintt.setModel(DbUtils.resultSetToTableModel(rs));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Main = new javax.swing.JPanel();
        FC = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buildingcombo = new javax.swing.JComboBox<>();
        search = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenantstable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        building = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        unitnumber = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tenantsid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        categorycombo = new javax.swing.JComboBox<>();
        complaintcombo = new javax.swing.JComboBox<>();
        emaintenancecombo = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        complainttxt = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        Complete = new javax.swing.JCheckBox();
        Incomplete = new javax.swing.JCheckBox();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        LOC = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        complaintt = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        fname1 = new javax.swing.JTextField();
        mname1 = new javax.swing.JTextField();
        lname1 = new javax.swing.JTextField();
        building1 = new javax.swing.JTextField();
        unit1 = new javax.swing.JTextField();
        tenantsid1 = new javax.swing.JTextField();
        complaintz = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        complainttxt1 = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        Complete1 = new javax.swing.JCheckBox();
        jButton5 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/file.png"))); // NOI18N
        jButton1.setText("File Complaint");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/list.png"))); // NOI18N
        jButton2.setText("List of Complaint");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main.setBackground(new java.awt.Color(255, 255, 255));
        Main.setLayout(new java.awt.CardLayout());

        tab.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Complaints");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel2.setText("Select Building:");

        buildingcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        buildingcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));
        buildingcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingcomboActionPerformed(evt);
            }
        });

        search.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        search.setText("Search");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/search.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tenantstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tenantstable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenantstableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tenantstable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab.addTab("Select Tenants", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel3.setText("First Name:");

        fname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        fname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fname.setEnabled(false);

        building.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        building.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        building.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel4.setText("Building:");

        mname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        mname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mname.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel5.setText("Middle Name:");

        unitnumber.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        unitnumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitnumber.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel6.setText("Unit Number:");

        lname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel7.setText("Last Name:");

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel8.setText("Tenants ID:");

        tenantsid.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tenantsid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantsid.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel9.setText("Select Category:");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel10.setText("Select Complaint:");

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel11.setText("Select Maintenance:");

        categorycombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        categorycombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));
        categorycombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorycomboActionPerformed(evt);
            }
        });

        complaintcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        complaintcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Complaint" }));
        complaintcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                complaintcomboActionPerformed(evt);
            }
        });

        emaintenancecombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        emaintenancecombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Maintenance" }));

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel12.setText("Additional:");

        complainttxt.setColumns(20);
        complainttxt.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        complainttxt.setRows(5);
        jScrollPane2.setViewportView(complainttxt);

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel13.setText("Remarks:");

        buttonGroup1.add(Complete);
        Complete.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        Complete.setText("Complete");
        Complete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompleteActionPerformed(evt);
            }
        });

        buttonGroup1.add(Incomplete);
        Incomplete.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        Incomplete.setText("Incomplete");
        Incomplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IncompleteActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        jButton4.setText("Save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/back.png"))); // NOI18N
        jButton6.setText("Back");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Complete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Incomplete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(categorycombo, 0, 150, Short.MAX_VALUE)
                                            .addComponent(complaintcombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(emaintenancecombo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(mname)
                                            .addComponent(unitnumber, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(unitnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(categorycombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(emaintenancecombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(complaintcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(Complete)
                        .addComponent(Incomplete))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        tab.addTab("Complaints", jPanel4);

        javax.swing.GroupLayout FCLayout = new javax.swing.GroupLayout(FC);
        FC.setLayout(FCLayout);
        FCLayout.setHorizontalGroup(
            FCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        FCLayout.setVerticalGroup(
            FCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );

        Main.add(FC, "card2");

        complaintt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        complaintt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                complainttMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(complaintt);

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("First Name:");

        jLabel15.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Middle Name:");

        jLabel16.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Last Name:");

        jLabel17.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Building:");

        jLabel18.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Unit Number:");

        jLabel19.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Tenants ID:");

        jLabel20.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Complaints");

        jLabel21.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Additionals");

        fname1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        fname1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fname1.setEnabled(false);

        mname1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        mname1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mname1.setEnabled(false);

        lname1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lname1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname1.setEnabled(false);

        building1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        building1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        building1.setEnabled(false);

        unit1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        unit1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unit1.setEnabled(false);

        tenantsid1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tenantsid1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantsid1.setEnabled(false);

        complaintz.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        complaintz.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        complaintz.setEnabled(false);

        complainttxt1.setColumns(20);
        complainttxt1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        complainttxt1.setRows(5);
        complainttxt1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        complainttxt1.setEnabled(false);
        jScrollPane4.setViewportView(complainttxt1);

        jLabel22.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel22.setText("Remarks:");

        buttonGroup2.add(Complete1);
        Complete1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        Complete1.setText("Complete");
        Complete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Complete1ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LOCLayout = new javax.swing.GroupLayout(LOC);
        LOC.setLayout(LOCLayout);
        LOCLayout.setHorizontalGroup(
            LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LOCLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LOCLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Complete1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(LOCLayout.createSequentialGroup()
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fname1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mname1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lname1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(building1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(unit1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tenantsid1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(complaintz, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        LOCLayout.setVerticalGroup(
            LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LOCLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LOCLayout.createSequentialGroup()
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(fname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(mname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(building1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(unit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(tenantsid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(complaintz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(Complete1)
                            .addComponent(jButton5)))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        Main.add(LOC, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void categorycomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorycomboActionPerformed
        showComplaint();
    }//GEN-LAST:event_categorycomboActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(FC);        
        Main.repaint();
        Main.revalidate();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(LOC);        
        Main.repaint();
        Main.revalidate();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void CompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompleteActionPerformed
        checkuncheck= "Complete";
    }//GEN-LAST:event_CompleteActionPerformed

    private void IncompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IncompleteActionPerformed
        checkuncheck= "Incomplete";
    }//GEN-LAST:event_IncompleteActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       tab.setSelectedIndex(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         try{
            if(complainttxt.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Error","Please Enter Complaint!",JOptionPane.ERROR_MESSAGE);
                complainttxt.setText("");
            }else if(Complete.isSelected()== false && Incomplete.isSelected() == false){
                JOptionPane.showMessageDialog(null, "Error","Please Check Remarks!",JOptionPane.ERROR_MESSAGE);
                buttonGroup1.clearSelection();
            }else{
            String tenant = tenantsid.getText();
            String query = "Select * from tenantsinformation where tenantsID='"+tenant+"'";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                String tenantsidd = rs.getString("tenantsID");
                String unitidd = rs.getString("unitID");
                String sql = "Insert into complaints (tenantsID,unitID,Date,service,serviceType,serviceBy,complaint,remarks,dateFiled) values (?,?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, tenantsidd);
                pst.setString(2, unitidd);
                pst.setString(3, datess);
                pst.setString(4, categorycombo.getSelectedItem().toString());
                pst.setString(5, complaintcombo.getSelectedItem().toString());
                pst.setString(6, emaintenancecombo.getSelectedItem().toString());
                pst.setString(7, complainttxt.getText());
                pst.setString(8, checkuncheck);
                pst.setString(9, datess);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Success","Complaint Added",JOptionPane.INFORMATION_MESSAGE);                
                buttonGroup1.clearSelection();
                complainttxt.setText("");
                Table();
            }
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            String fullname = emaintenancecombo.getSelectedItem().toString();
            String sql = "Select * from emaintenance where fullName = '"+fullname+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String contact = rs.getString("Contact");
                String dates = datess;
                String fnames = fname.getText();
                String mnames = mname.getText();
                String lnames = lname.getText();
                String buildings = building.getText();
                String unitnumbers = unitnumber.getText();
                String categorys = categorycombo.getSelectedItem().toString();
                String complaint1 = complaintcombo.getSelectedItem().toString();
                String complaint2 = complainttxt.getText();
                String serviceby = emaintenancecombo.getSelectedItem().toString();
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(new PhoneNumber(contact),//reciever
        new PhoneNumber("+18563676862"), //sender
        "*Complaint Job Order* Date:"+dates+" Name:"+fnames+" "+mnames+" "+lnames+"\n Building:"+buildings+"\n Unit Number:"+unitnumbers+"\n Service:"+categorys+"\n Complaint:"+complaint1+"\n Additional:"+complaint2+"\n Service By:"+serviceby+" ").create();
        System.out.println(message.getSid());
        JOptionPane.showMessageDialog(null, "Job Order Was sent to '"+serviceby+"'","Job Order Message Sent",JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tenantstableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenantstableMouseClicked
             try{
            int row = tenantstable.getSelectedRow();
            String click = (tenantstable.getModel().getValueAt(row, 0).toString());
            String sql ="Select * from tenantsinformation where tenantsID = '"+click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            if(rs.next()){
                String add2 = rs.getString("buildingName");
                String add3 = rs.getString("unitNumber");
                String add4 = rs.getString("fname");
                String add5 = rs.getString("mname");
                String add6 = rs.getString("lname");
                String add7 = rs.getString("contact");   
                building.setText(add2);
                unitnumber.setText(add3);
                fname.setText(add4);
                mname.setText(add5);
                lname.setText(add6);
                tab.setSelectedIndex(1);
              
            }
            
        }catch(Exception e){
                JOptionPane.showConfirmDialog(null, e);
        }
    }//GEN-LAST:event_tenantstableMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
              try{
            if(buildingcombo.getSelectedItem().equals("Select Building")){
                JOptionPane.showMessageDialog(null, "Error","Select Building!",JOptionPane.ERROR_MESSAGE);
            }else{
                String buildingcomboss = buildingcombo.getSelectedItem().toString();
                String searchs = search.getText();
                String sql ="Select * from tenantsinformation where unitNumber = '"+searchs+"' and buildingName = '"+buildingcomboss+"'";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next() == true){
                    String add1 = rs.getString("fname");
                    String add2 = rs.getString("mname");
                    String add3 = rs.getString("lname");
                    String add4 = rs.getString("buildingName");
                    String add5 = rs.getString("unitNumber");
                    String add6 = rs.getString("tenantsID");
                    fname.setText(add1);
                    mname.setText(add2);
                    lname.setText(add3);
                    building.setText(add4);
                    unitnumber.setText(add5);
                    tenantsid.setText(add6);
                    tab.setSelectedIndex(1);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buildingcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingcomboActionPerformed
            TenantsTable();
    }//GEN-LAST:event_buildingcomboActionPerformed

    private void complaintcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_complaintcomboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_complaintcomboActionPerformed

    private void Complete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Complete1ActionPerformed
         checkuncheck = "Complete";
    }//GEN-LAST:event_Complete1ActionPerformed

    private void complainttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_complainttMouseClicked
         try{
           int row = complaintt.getSelectedRow();
           String click = (complaintt.getModel().getValueAt(row, 1).toString()); 
           String sql ="Select * from tenantsinformation where tenantsID = '"+click+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery(sql);
           while(rs.next()){
                String add1 = rs.getString("buildingName");
                String add2 = rs.getString("unitNumber");
                String add3 = rs.getString("fname");
                String add4 = rs.getString("mname");
                String add5 = rs.getString("lname");
                String add6 = rs.getString("tenantsID");   
                building1.setText(add1);
                unit1.setText(add2);
                fname1.setText(add3);
                mname1.setText(add4);
                lname1.setText(add5);
                tenantsid1.setText(add6);  
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
                String tenantsIDD = tenantsid1.getText();
                String sql = "Select * from complaints where tenantsID = '"+tenantsIDD+"'";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    String addd1 = rs.getString("complaint");
                    String addd2 = rs.getString("complaintID");
                    String addd3 = rs.getString("serviceType");
                    complainttxt1.setText(addd1);
                    complaintz.setText(addd2);
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_complainttMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         try{
          String tenantsidds = tenantsid1.getText();
          String sql1 = "Select * from complaints where tenantsID = '"+tenantsidds+"'";
          pst = conn.prepareStatement(sql1);
          rs = pst.executeQuery();
          while(rs.next()){
          String tenantsidd = tenantsid1.getText();
          String complaintsid = rs.getString("complaintID");
          String sql = "update complaints set remarks = '"+checkuncheck+"' where complaintID='"+complaintsid+"'";
          pst = conn.prepareStatement(sql);
          pst.execute();
          JOptionPane.showMessageDialog(null, "Success","Remarks Updated",JOptionPane.INFORMATION_MESSAGE);
          Table();
          fname1.setText("");
          mname1.setText("");
          lname1.setText("");
          building1.setText("");
          unit1.setText("");
          tenantsid1.setText("");
          complainttxt1.setText("");
          complaintz.setText("");
          buttonGroup2.clearSelection();
          }
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Complete;
    private javax.swing.JCheckBox Complete1;
    private javax.swing.JPanel FC;
    private javax.swing.JCheckBox Incomplete;
    private javax.swing.JPanel LOC;
    private javax.swing.JPanel Main;
    private javax.swing.JTextField building;
    private javax.swing.JTextField building1;
    private javax.swing.JComboBox<String> buildingcombo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> categorycombo;
    private javax.swing.JComboBox<String> complaintcombo;
    private javax.swing.JTable complaintt;
    private javax.swing.JTextArea complainttxt;
    private javax.swing.JTextArea complainttxt1;
    private javax.swing.JTextField complaintz;
    private javax.swing.JComboBox<String> emaintenancecombo;
    private javax.swing.JTextField fname;
    private javax.swing.JTextField fname1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField lname1;
    private javax.swing.JTextField mname;
    private javax.swing.JTextField mname1;
    private javax.swing.JTextField search;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextField tenantsid;
    private javax.swing.JTextField tenantsid1;
    private javax.swing.JTable tenantstable;
    private javax.swing.JTextField unit1;
    private javax.swing.JTextField unitnumber;
    // End of variables declaration//GEN-END:variables
    private String checkuncheck;
    private String datess;
}

