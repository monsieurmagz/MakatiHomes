/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;

import javax.swing.JRootPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.*;
import java.awt.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import net.proteanit.sql.DbUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.applet.*;  
import java.awt.*;  
import java.util.*;  
import java.text.*;  
import java.util.Date;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author GameDev
 */
public class WaterReading extends javax.swing.JInternalFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
     public static final String ACCOUNT_SID = "AC9cccf2c67541dab6eaef384e516d388a";
     public static final String AUTH_TOKEN = "ecfd96d905a7d2e4a42118f669dbec3e";
    /**
     * Creates new form WaterReading
     */
    public WaterReading() {
        initComponents();
        conn =  javaconnect.connectdb();
        removetitlebar();
        BuildingCombo();
        WaterReadingTable();
        TenantsTable();
        Date();
        DateSelect();
    }
    public void removetitlebar(){
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }
     private void BuildingCombo(){
        try{
            String sql = "select * from  buildings";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                String building = rs.getString("buildingName");
                buildingcombo.addItem(building);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    private void TenantsTable(){
        try{     
            String buildingcombobo = buildingcombo.getSelectedItem().toString();
            String sql = "Select tenantsID,buildingName,unitNumber,fname,mname,lname from tenantsinformation where buildingName = '"+buildingcombobo+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            tenants.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void DateSelect(){
        try{
            
            String sql = "Select DISTINCT prevDateread from unitwatershareexpense ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String prevreaddate = rs.getString("prevDateread");
                reportprev.addItem(prevreaddate);
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void DateSelect2(){
        try{
            String reportprevs = reportprev.getSelectedItem().toString();
            String sql1 = "Select * from unitwatershareexpense where prevDateread = '"+reportprevs+"'";
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                while(rs.next()){
                    String psentddate = rs.getString("psentDateread");
                    reportpsent.setText(psentddate);
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void WaterReadingTable(){
        try{
            String buildingcombos = buildingcombo.getSelectedItem().toString();
            String sql = "Select tenantsID,buildingName as BuildingName,unitNumber as UnitNumber,prevRead,psentRead,(psentRead-prevRead) as Consumptions,charge,((psentRead-prevRead)* charge) as AmountDue from unitwatershareexpense where buildingName = '"+buildingcombos+"' AND"
                    + " MONTH(psentDateread) = MONTH(Current_date())";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            watertable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void WaterReadingTable2(){
        try{
            String buildingcombos = buildingcombo.getSelectedItem().toString();
            String reportsprevs = reportprev.getSelectedItem().toString();
            String reportspsents = reportpsent.getText();
            String sql = "Select tenantsID,buildingName as BuildingName,unitNumber as UnitNumber,prevRead,psentRead,(psentRead-prevRead) as Consumptions,charge,((psentRead-prevRead)* charge) as AmountDue from unitwatershareexpense where buildingName = '"+buildingcombos+"' AND"
                    + "  prevDateread ='"+reportsprevs+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            watertable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public double getSum(){
        int rowsCount = watertable.getRowCount();
        double sum = 0;
        for(int i = 0; i<rowsCount; i++){
            sum = sum+Double.parseDouble(watertable.getValueAt(i, 4).toString());
        }
        return sum;
    }
    public double getSumCon(){
        int rowsCount = watertable.getRowCount();
        double sumcon = 0;
        double prevreadd;
        double psentreadd;
        for(int i = 0; i<rowsCount; i++){
            sumcon = sumcon+Double.parseDouble(watertable.getValueAt(i, 5).toString());
            
        }
        return sumcon;
    }
    public double getMulti(){
        double chargecum = 0;
        double tcc1;
        double tac1;
        tcc1 = Double.parseDouble(tcc.getText().toString());
        tac1 = Double.parseDouble(tac.getText().toString());
        chargecum = tcc1 / tac1;
        return chargecum;
    }
         public void Date(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date1.setText(year+"-"+(month+1)+"-"+day);
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
        tab = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        building = new javax.swing.JTextField();
        tenantsid = new javax.swing.JTextField();
        unitnumber = new javax.swing.JTextField();
        fname = new javax.swing.JTextField();
        mname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        date1 = new javax.swing.JTextField();
        prevread = new javax.swing.JTextField();
        psentread = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenants = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        buildingcombo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        reportprev = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        reportpsent = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        watertable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        tcc = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tac = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ccc = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tab.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Water Reading");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Building:");

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tenants ID:");

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Unit Number:");

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("First Name:");

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Middle Name:");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Last Name:");

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Date:");

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Previous Reading:");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Present Reading:");

        building.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        building.setEnabled(false);

        tenantsid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantsid.setEnabled(false);

        unitnumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitnumber.setEnabled(false);

        fname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fname.setEnabled(false);

        mname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mname.setEnabled(false);

        lname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname.setEnabled(false);

        date1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        date1.setEnabled(false);

        prevread.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        psentread.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        tenants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tenants.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenantsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tenants);

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/enable.png"))); // NOI18N
        jButton1.setText("Enable");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Save.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel11.setText("Select Building:");

        buildingcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        buildingcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));
        buildingcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingcomboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(403, 403, 403)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(unitnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(prevread, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(psentread, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(Save)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(unitnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(prevread, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(psentread, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(Save)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        tab.addTab("Tenants", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel12.setText("Date:");

        reportprev.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        reportprev.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Date" }));
        reportprev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportprevActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel13.setText("To:");

        reportpsent.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        reportpsent.setEnabled(false);

        jButton4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Reports.png"))); // NOI18N
        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        watertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(watertable);

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel14.setText("Total Current Charge:");

        tcc.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tcc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tcc.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel15.setText("Total Actual Consumptions:");

        tac.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tac.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tac.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel16.setText("Charge per Cubic Meter:");

        ccc.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        ccc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ccc.setEnabled(false);

        jButton5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/compute.png"))); // NOI18N
        jButton5.setText("Compute");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 102, 51));
        jLabel17.setText("*Press Update First Before Pessing Compute Button");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tcc, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tac, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ccc, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reportprev, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reportpsent, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(31, 31, 31)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(reportprev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(reportpsent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(tac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(ccc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab.addTab("Water Reading ", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buildingcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingcomboActionPerformed
        TenantsTable();
        WaterReadingTable();
    }//GEN-LAST:event_buildingcomboActionPerformed

    private void tenantsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenantsMouseClicked
       try{
            int row = tenants.getSelectedRow();
            String tableclick = (tenants.getModel().getValueAt(row, 0).toString());
            String sql = "select * from tenantsinformation where tenantsID='"+tableclick+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                String add1 = rs.getString("tenantsID");
                String add2 = rs.getString("buildingName");
                String add3 = rs.getString("unitNumber");
                String add4 = rs.getString("fname");
                String add5 = rs.getString("mname");
                String add6 = rs.getString("lname");
                tenantsid.setText(add1);
                building.setText(add2);
                unitnumber.setText(add3);
                fname.setText(add4);
                mname.setText(add5);
                lname.setText(add6);
                WaterReadingTable();
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         //prevread
         try{
             int row = tenants.getSelectedRow();
             String tableclick = (tenants.getModel().getValueAt(row, 0).toString());
             String sql = "select * from unitwatershareexpense where tenantsID='"+tableclick+"' AND MONTH(Date(psentDateread)) = MONTH(CURRENT_DATE()) AND prevRead IS NOT NULL";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 String add1 = rs.getString("prevRead");
                 prevread .setText(add1);
                 prevread.setEnabled(false);
             }else if (rs.next() == false){
                 prevread .setText("");
                 prevread.setEnabled(true);
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
             
         }
         //psent
         try{
             int row = tenants.getSelectedRow();
             String tableclick = (tenants.getModel().getValueAt(row, 0).toString());
             String sql = "select * from unitwatershareexpense where tenantsID='"+tableclick+"' AND MONTH(psentDateread) = MONTH(Current_date()) AND psentRead IS NOT NULL";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 String add1 = rs.getString("psentRead");
                 psentread .setText(add1);
                 psentread.setEnabled(false);
             }else if (rs.next()==false){
                 psentread .setText("");
                 psentread.setEnabled(true);
                 
             }
            
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
         //date now is greater thand psentread
         try{
            String tenantsids = tenantsid.getText();
            String sql = "Select * from unitwatershareexpense where tenantsID= '"+tenantsids+"' and MONTH(Current_date()) > MONTH(Date(psentDateread))";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String prevreads = rs.getString("psentRead");
                String psentDatess = rs.getString("psentDateread");
                prevread.setText(prevreads);
                prevread.setEnabled(false);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         
         
    }//GEN-LAST:event_tenantsMouseClicked

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
         try{
            String tenantsid1 = tenantsid.getText();
            String sql ="Update tenantsinformation set wrStatus ='Read' where tenantsID ='"+tenantsid1+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
        String prevreads = prevread.getText();
        String psentreads = psentread.getText(); 
        int prvread = Integer.parseInt(prevreads);
        int psentreads1 = Integer.parseInt(psentreads);
                
            if(prevread.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Error fill up Fields ","Error",JOptionPane.ERROR_MESSAGE);
            }else if(prvread > psentreads1){
                JOptionPane.showMessageDialog(null, "Present read must not be lower than previous read","ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
            String tenantsidd = tenantsid.getText();
            String sql = "Select * from tenantsinformation where tenantsID ='"+tenantsidd+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String tenantsiddd = rs.getString("tenantsID");
                String unitsidd = rs.getString("unitID");
                String buildingiddd = rs.getString("buildingID");
                String buildingnamee = rs.getString("buildingName");
                String unitnumberr = rs.getString("unitNumber");
                String fname1 = rs.getString("fname");
                String mname2 = rs.getString("mname");
                String lname1 = rs.getString("lname");
                String insertwater = "Insert into unitwatershareexpense (tenantsID,fname,mname,lname,unitID,buildingID,prevRead,psentRead,psentDateread,prevDateread,unitNumber,buildingName,consumptions,uwStatus) values (?,?,?,?,?,?,?,?,?,?,?,?,(psentRead-prevRead),'NotPaid')";
                pst = conn.prepareStatement(insertwater);
                pst.setString(1, tenantsiddd);
                pst.setString(2, fname1);
                pst.setString(3, mname2);
                pst.setString(4, lname1);
                pst.setString(5, unitsidd);
                pst.setString(6, buildingiddd);
                pst.setString(7, prevread.getText());
                pst.setString(8, psentread.getText());
                pst.setString(9, date1.getText());
                pst.setString(10, date1.getText());
                pst.setString(11, unitnumberr);
                pst.setString(12, buildingnamee);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Added Successfuly");
                WaterReadingTable();
                
                tcc.setText(Double.toString(getSum()));
                tac.setText(Double.toString(getSumCon()));
                ccc.setText(Double.toString((getMulti())));
                prevread.setText("");
                psentread.setText("");
                
            }
           }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         try{
            String tenantid = tenantsid.getText();
            String psent = psentread.getText();
            String sql = "update unitwatershareexpense set psentRead = '"+psent+"' where tenantsID = '"+tenantid+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Sucess","Present Reading Added",JOptionPane.INFORMATION_MESSAGE);
            WaterReadingTable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         psentread.setText("");
        psentread.setEnabled(true);
        Save.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        tcc.setText(Double.toString(getSum()));
        tac.setText(Double.toString(getSumCon()));
        ccc.setText(Double.toString(getMulti()));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        
        //Validation for all tenants
        try{
           String Buildingnames = building.getText();
           String sql = "Select * from tenantsinformation where wrStatus ='NotRead' AND buildingName = '"+Buildingnames+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
           if(rs.next() == false){
               try{
            String buildingc = buildingcombo.getSelectedItem().toString();
            String charge1 = ccc.getText();
            String sql1 = "update unitwatershareexpense set charge = '"+charge1+"' ,amountdue = (consumptions * charge),consumptions = (psentRead-prevRead) where buildingName = '"+buildingc+"' AND Date(now()) BETWEEN Date(prevDateread) AND Date(psentDateread)";
            pst = conn.prepareStatement(sql1);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Charge per Cubic meter updated");
            WaterReadingTable();
            
            String sql4 = "Select * from unitwatershareexpense where MONTH(CURRENT_DATE()) = MONTH(psentDateread) and buildingName = '"+buildingc+"'";
               pst = conn.prepareStatement(sql4);
               rs = pst.executeQuery();
               if(rs.next() == true){
                   String sql5 = "Select * from tenantsinformation where buildingName ='"+buildingc+"'";
                   pst = conn.prepareStatement(sql5);
                   rs = pst.executeQuery();
                   while(rs.next()){
                       String kontak = rs.getString("contact");
                       
                       String sql6="Select * from unitwatershareexpense where MONTH(CURRENT_DATE()) = MONTH(psentDateread) and buildingName = '"+buildingc+"'";
                       pst = conn.prepareStatement(sql6);
                       rs = pst.executeQuery();
                       while(rs.next()){
                           String efname = rs.getString("fname");
                           String emname = rs.getString("mname");
                           String elname = rs.getString("lname");
                           String bilding = rs.getString("buildingName");
                           String yunit = rs.getString("unitNumber");
                           String konsamption = rs.getString("consumptions");
                           String tyarge = rs.getString("charge");
                           String amawntju = rs.getString("amountDue");
                           Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
                Message message = Message.creator(new PhoneNumber(kontak),//reciever
                new PhoneNumber("+18563676862"), //sender
                "Makati Homes - II\n Water Billing\n Name: "+efname+" "+emname+" "+elname+"\n Building: "+bilding+"\n Unit: "+yunit+"\n Consumptions: "+konsamption+"\n Chagre: "+tyarge+"\n AmountDue: "+amawntju+"   ").create();
                System.out.println(message.getSid());
                
                       }
                   }
               }else{
                   
               }
               
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
               try{
            String buildingcombos = buildingcombo.getSelectedItem().toString();
            String sql2 = "Update tenantsinformation set wrStatus='NotRead' where buildingName='"+buildingcombos+"'";
            pst = conn.prepareStatement(sql2);
            pst.execute();
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
               
           }else{
               String bname = buildingcombo.getSelectedItem().toString();
               String sql3 = "Select * from tenantsinformation where wrStatus ='NotRead' and buildingName='"+bname+"'";
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               while(rs.next()){
                   String fname1 = rs.getString("fname");
                   String mname1 = rs.getString("mname");
                   String lname1 = rs.getString("lname");
                   JOptionPane.showMessageDialog(null, "Please input -"+fname1+" "+mname1+" "+lname1+"- water readings!  ","ERROR WATER READING",JOptionPane.ERROR_MESSAGE);
               }
               
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            String buildingcmb1 = buildingcombo.getSelectedItem().toString();
            String prevydate = date1.getText();
            String datesss1 = date1.getText();
            String destination = "C:\\Users\\Magz\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Water Readings\\"+datesss1+".pdf";
             JasperDesign jd = JRXmlLoader.load("C:\\Users\\Magz\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Water Reading\\waters.jrxml");
           String sql = "SELECT\n" +
"     unitwatershareexpense.`tenantsID` AS unitwatershareexpense_tenantsID,\n" +
"     unitwatershareexpense.`fname` AS unitwatershareexpense_fname,\n" +
"     unitwatershareexpense.`mname` AS unitwatershareexpense_mname,\n" +
"     unitwatershareexpense.`lname` AS unitwatershareexpense_lname,\n" +
"     unitwatershareexpense.`unitNumber` AS unitwatershareexpense_unitNumber,\n" +
"     unitwatershareexpense.`buildingName` AS unitwatershareexpense_buildingName,\n" +
"     unitwatershareexpense.`prevRead` AS unitwatershareexpense_prevRead,\n" +
"     unitwatershareexpense.`psentRead` AS unitwatershareexpense_psentRead,\n" +
"     unitwatershareexpense.`prevDateread` AS unitwatershareexpense_prevDateread,\n" +
"     unitwatershareexpense.`psentDateread` AS unitwatershareexpense_psentDateread,\n" +
"     unitwatershareexpense.`consumptions` AS unitwatershareexpense_consumptions,\n" +
"     unitwatershareexpense.`charge` AS unitwatershareexpense_charge,\n" +
"     unitwatershareexpense.`amountdue` AS unitwatershareexpense_amountdue\n" +
"FROM\n" +
"     `unitwatershareexpense` unitwatershareexpense where buildingName ='"+buildingcmb1+"' AND Date(now()) BETWEEN(prevDateread) AND Date(psentDateread)";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);     
           JasperViewer.viewReport(jp,false);
           JasperExportManager.exportReportToPdfFile(jp, destination);
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void reportprevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportprevActionPerformed
        DateSelect2();
         WaterReadingTable2();
    }//GEN-LAST:event_reportprevActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JTextField building;
    private javax.swing.JComboBox<String> buildingcombo;
    private javax.swing.JTextField ccc;
    private javax.swing.JTextField date1;
    private javax.swing.JTextField fname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField mname;
    private javax.swing.JTextField prevread;
    private javax.swing.JTextField psentread;
    private javax.swing.JComboBox<String> reportprev;
    private javax.swing.JTextField reportpsent;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextField tac;
    private javax.swing.JTextField tcc;
    private javax.swing.JTable tenants;
    private javax.swing.JTextField tenantsid;
    private javax.swing.JTextField unitnumber;
    private javax.swing.JTable watertable;
    // End of variables declaration//GEN-END:variables
}
