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
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.io.File;
import javax.swing.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author GameDev
 */
public class Payment extends javax.swing.JInternalFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
     public static final String ACCOUNT_SID = "AC9cccf2c67541dab6eaef384e516d388a";
     public static final String AUTH_TOKEN = "ecfd96d905a7d2e4a42118f669dbec3e";
    /**
     * Creates new form Payment
     */
    public Payment() {
        initComponents();
        conn = javaconnect.connectdb();
        removetitlebar();
        TenantsTable();
        Building();
        WaterListDate();
        RentalListDate();
        Date();
        
    }
 public void removetitlebar(){
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }
     private void TenantsTable(){
        try{
            if(searchcombo.getSelectedItem().toString().equals("Show All")){           
            String sql ="Select tenantsID as TenantsID,buildingName as Building,unitNumber as Unit, fname as FirstName,mname as MiddleName,lname as LastName,contact as Contact,dob as DateOfBirth,cs as CivilStatus,occup as Occupation from tenantsinformation ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            tenantstable.setModel(DbUtils.resultSetToTableModel(rs));    
            }else{
            String buildingcombo = searchcombo.getSelectedItem().toString();
            String sql ="Select tenantsID as TenantsID,buildingName as Building,unitNumber as Unit, fname as FirstName,mname as MiddleName,lname as LastName,contact as Contact,dob as DateOfBirth,cs as CivilStatus,occup as Occupation from tenantsinformation where buildingName ='"+buildingcombo+"' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            tenantstable.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void Building(){
        try{
            String sql = "Select * from buildings";
            pst = conn.prepareStatement(sql);
            rs =  pst.executeQuery();
            while(rs.next()){
                String buildingname = rs.getString("buildingName");
                searchcombo.addItem(buildingname);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void Twomant(){
        try{
            String tenanttid = tenantid1.getText();
            String sql = "Select * from advpay where tenantsID = '"+tenanttid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String advamounts = rs.getString("advAmount");
                twomonth.setText(advamounts);
                twostat.setText("Active");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            String tenanttid = tenantid1.getText();
            String sql = "Select * from advpay where tenantsID = '"+tenanttid+"' AND advAmount = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String advamounts = rs.getString("advAmount");
                twomonth.setText(advamounts);
                twostat.setText("Used");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Onemant(){
        try{
            String tenanttid = tenantid1.getText();
            String sql = "Select * from deposit where tenantsID = '"+tenanttid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String dptamounts = rs.getString("dptAmount");
                onemonth.setText(dptamounts);
                onestat.setText("Active");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            String tenanttid = tenantid1.getText();
            String sql = "Select * from deposit where tenantsID = '"+tenanttid+"' AND dptAmount ='0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String dptamounts = rs.getString("dptAmount");
                onemonth.setText(dptamounts);
                onestat.setText("Used");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void WaterForTenant(){
        try{
            String tenanttid = tenantid1.getText();
            String sql = "Select prevDateRead as DateStart,psentDateRead as DateEnd,prevRead as PreviousReading,psentRead as PresentReading,consumptions as Consumptions, charge as Charge, uwStatus as Status,amountdue as AmountDue,DATE_ADD(psentDateread, INTERVAL 5 DAY) as DueDate from unitwatershareexpense where tenantsID = '"+tenanttid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            watertenants.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void RentalForTenant(){
        try{
            String id = tenantid1.getText();
            String sql = "Select rentaAmount as Rental,penalty as Penalty,prevBal as PreviousBalance,amountDue as AmountDue,urStatus as Status,monthlyend as DueDate from unitrentalexpenses where tenantsID='"+id+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rentaltenants.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void WaterListDate(){        
        try{
            String tenantsid123 = tenantid2.getText();
            String sql ="Select Distinct psentDateread from unitwatershareexpense where uwStatus = 'NotPaid' and tenantsID = '"+tenantsid123+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String datess = rs.getString("psentDateread");
                dategago.addItem(datess);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void RentalListDate(){
        try{
            String tid = tenantid1.getText();
            String sql ="Select Distinct monthlystart from unitrentalexpenses where tenantsID ='"+tid+"' and urStatus ='NotPaid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String rdates = rs.getString("monthlystart");
                rdates1.addItem(rdates);
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        public void Date(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dates1.setText(year+"-"+(month+1)+"-"+day);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        searchcombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenantstable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fname2 = new javax.swing.JTextField();
        building2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mname2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        unitroom3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lname2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tenantid2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        date1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        watertenants = new javax.swing.JTable();
        dategago = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        duedate1 = new javax.swing.JTextField();
        consumo1 = new javax.swing.JTextField();
        charge1 = new javax.swing.JTextField();
        amount1 = new javax.swing.JTextField();
        status1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        fname1 = new javax.swing.JTextField();
        building1 = new javax.swing.JTextField();
        mnames1 = new javax.swing.JTextField();
        unitroom2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lname1 = new javax.swing.JTextField();
        tenantid1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        rdates1 = new javax.swing.JComboBox<>();
        dates1 = new javax.swing.JTextField();
        rmonth = new javax.swing.JTextField();
        rrental = new javax.swing.JTextField();
        rpenalty = new javax.swing.JTextField();
        rbal = new javax.swing.JTextField();
        rtotal = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rentaltenants = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        twomonth = new javax.swing.JTextField();
        onemonth = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        twostat = new javax.swing.JLabel();
        onestat = new javax.swing.JLabel();
        rstatus = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();

        tab.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Payments");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel2.setText("Building:");

        searchcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        searchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building", "Show All" }));
        searchcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchcomboActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(541, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(433, 433, 433))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab.addTab("Tenants", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel3.setText("First Name:");

        fname2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        fname2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fname2.setEnabled(false);

        building2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        building2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        building2.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel4.setText("Building:");

        mname2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        mname2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mname2.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel5.setText("Middle Name:");

        unitroom3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        unitroom3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitroom3.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel6.setText("Unit Room:");

        lname2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lname2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname2.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel7.setText("Last Name:");

        tenantid2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tenantid2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantid2.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel8.setText("Tenants ID:");

        date1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        date1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        date1.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel9.setText("Date:");

        watertenants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(watertenants);

        dategago.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        dategago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Date Balance", "Show All" }));
        dategago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dategagoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel10.setText("Date:");

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel11.setText("Due Date:");

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel12.setText("Consumption:");

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel13.setText("Charge/Cum:");

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel14.setText("Amount:");

        jLabel15.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel15.setText("Status:");

        duedate1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        duedate1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        duedate1.setEnabled(false);

        consumo1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        consumo1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        consumo1.setEnabled(false);

        charge1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        charge1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        charge1.setEnabled(false);

        amount1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        amount1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        amount1.setEnabled(false);

        status1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        status1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        status1.setEnabled(false);

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/money.png"))); // NOI18N
        jButton1.setText("Paid");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(building2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(fname2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(unitroom3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(mname2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lname2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(tenantid2)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(duedate1))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(charge1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(consumo1)
                                            .addComponent(amount1)
                                            .addComponent(status1)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addGap(210, 210, 210))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dategago, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(mname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(building2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(unitroom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(tenantid2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(dategago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(duedate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(consumo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(charge1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(amount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(status1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab.addTab("Water Payment", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        fname1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        fname1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fname1.setEnabled(false);

        building1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        building1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        building1.setEnabled(false);

        mnames1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        mnames1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mnames1.setEnabled(false);

        unitroom2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        unitroom2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitroom2.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel16.setText("Last Name:");

        jLabel17.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel17.setText("Tenants ID:");

        lname1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lname1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname1.setEnabled(false);

        tenantid1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tenantid1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantid1.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel18.setText("Date:");

        jLabel19.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel19.setText("Date:");

        rdates1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rdates1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Balance Date", "Show All" }));
        rdates1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdates1ActionPerformed(evt);
            }
        });

        dates1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        dates1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dates1.setEnabled(false);

        rmonth.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rmonth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rmonth.setEnabled(false);

        rrental.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rrental.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rrental.setEnabled(false);

        rpenalty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rpenalty.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rpenalty.setEnabled(false);

        rbal.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rbal.setEnabled(false);

        rtotal.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rtotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rtotal.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/money.png"))); // NOI18N
        jButton2.setText("Paid");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel20.setText("Amount Due:");

        jLabel21.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel21.setText("Previous Balance:");

        jLabel22.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel22.setText("Penalty:");

        jLabel23.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel23.setText("Rental:");

        jLabel24.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel24.setText("Month:");

        rentaltenants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(rentaltenants);

        jLabel25.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel25.setText("Building:");

        jLabel26.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel26.setText("First Name:");

        jLabel27.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel27.setText("Unit Room:");

        jLabel28.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel28.setText("Middle Name:");

        jLabel29.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel29.setText("Two Months Deposit:");

        twomonth.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        twomonth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        twomonth.setEnabled(false);

        onemonth.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        onemonth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        onemonth.setEnabled(false);

        jLabel30.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel30.setText("One Month Advance");

        twostat.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        twostat.setText("Status");

        onestat.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        onestat.setText("Status");

        rstatus.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rstatus.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rstatus.setEnabled(false);

        jLabel33.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel33.setText("Status:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(building1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(fname1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(unitroom2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(mnames1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lname1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(tenantid1)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dates1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdates1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rtotal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rbal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rpenalty, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rmonth)
                                    .addComponent(rrental)
                                    .addComponent(rstatus)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(twomonth)
                                    .addComponent(onemonth, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton2)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(twostat)
                            .addComponent(onestat))
                        .addGap(130, 130, 130))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(fname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(mnames1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(lname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(dates1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(building1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(unitroom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(tenantid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(rdates1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(twomonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(twostat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(onemonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(onestat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rmonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rpenalty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab.addTab("Rental Payment", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchcomboActionPerformed
             TenantsTable();
    }//GEN-LAST:event_searchcomboActionPerformed

    private void rdates1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdates1ActionPerformed
        try{
            String id = tenantid1.getText();
            String rdates2 = rdates1.getSelectedItem().toString();
            String sql = "Select * from unitrentalexpenses where tenantsID ='"+id+"' and monthlystart ='"+rdates2+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String add1 = rs.getString("rentaAmount");
                rrental.setText(add1);
                String add2 = rs.getString("penalty");
                rpenalty.setText(add2);
                String add3 = rs.getString("monthlystart");
                rmonth.setText(add3);
                String add4 = rs.getString("prevBal");
                rbal.setText(add4);
                String add5 = rs.getString("amountDue");
                rtotal.setText(add5);
                String add6 = rs.getString("urStatus");
                rstatus.setText(add6);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        //specific date
        try{
            String id = tenantid1.getText();
            String rdates2 = rdates1.getSelectedItem().toString();
            String sql ="Select rentaAmount as Rental,penalty as Penalty,prevBal as PreviousBalance,amountDue as AmountDue,urStatus as Status,monthlyend as DueDate from unitrentalexpenses where tenantsID='"+id+"' AND monthlystart = '"+rdates2+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rentaltenants.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         //show all
            if(rdates1.getSelectedItem().toString().equals("Show All")){
            try{
            String tenantsids1 = tenantid1.getText();
            String dategagos = dategago.getSelectedItem().toString();
            String sql = "Select rentaAmount as Rental,penalty as Penalty,prevBal as PreviousBalance,amountDue as AmountDue,urStatus as Status,monthlyend as DueDate from unitrentalexpenses where tenantsID='"+tenantsids1+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rentaltenants.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_rdates1ActionPerformed

    private void dategagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dategagoActionPerformed
        try{
            String tenantsids1 = tenantid2.getText();
            String dategagos = dategago.getSelectedItem().toString();
            String sql = "Select prevRead as PreviousReading,psentRead as PresentReading,consumptions as Consumptions,charge as Charge,amountdue as AmountDue,DATE_ADD(psentDateread, INTERVAL 5 DAY) as DueDate,uwStatus as Status from unitwatershareexpense where tenantsID = '"+tenantsids1+"' AND psentDateread ='"+dategagos+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next() == true){
                String dud1 = rs.getString("DueDate");
                duedate1.setText(dud1);
                String dud2 = rs.getString("consumptions");
                consumo1.setText(dud2);
                String dud3= rs.getString("charge");
                charge1.setText(dud3);
                String dud4 = rs.getString("amountdue");
                amount1.setText(dud4);
                String dud5 = rs.getString("Status");
                status1.setText(dud5);                   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //specify the data in table using the date
        try{
            String tenantsids1 = tenantid2.getText();
            String dategagos = dategago.getSelectedItem().toString();
            String sql = "Select prevRead as PreviousReading,psentRead as PresentReading,consumptions as Consumptions,charge as Charge,amountdue as AmountDue,DATE_ADD(psentDateread, INTERVAL 5 DAY) as DueDate,uwStatus as Status from unitwatershareexpense where tenantsID = '"+tenantsids1+"' AND psentDateread ='"+dategagos+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            watertenants.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //show all
        if(dategago.getSelectedItem().toString().equals("Show All")){
            try{
            String tenantsids1 = tenantid2.getText();
            String dategagos = dategago.getSelectedItem().toString();
            String sql = "Select prevRead as PreviousReading,psentRead as PresentReading,consumptions as Consumptions,charge as Charge,amountdue as AmountDue,DATE_ADD(psentDateread, INTERVAL 5 DAY) as DueDate,uwStatus as Status from unitwatershareexpense where tenantsID = '"+tenantsids1+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            watertenants.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        
    }//GEN-LAST:event_dategagoActionPerformed

    private void tenantstableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenantstableMouseClicked
       
        try{
            int row = tenantstable.getSelectedRow();
            String click = (tenantstable.getModel().getValueAt(row, 0).toString());
            String sql ="Select * from tenantsinformation where tenantsID = '"+click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next() == true){
                String add1 = rs.getString("tenantsID");
                String add2 = rs.getString("buildingName");
                String add3 = rs.getString("unitNumber");
                String add4 = rs.getString("fname");
                String add5 = rs.getString("mname");
                String add6 = rs.getString("lname");
                String add7 = rs.getString("contact");
                String add8 = rs.getString("dob");
                String add9 = rs.getString("gender");
                String add10 = rs.getString("cs");
                String add11 = rs.getString("occup");
                String add12 = rs.getString("noc");
                String add13 = rs.getString("soe");
                String add14 = rs.getString("osoi");
                String add15 = rs.getString("nos");
                String add16 = rs.getString("occups");
                String add17 = rs.getString("nocs");
                String add18 = rs.getString("contacts");
                String add19 = rs.getString("soes");
                String add20 = rs.getString("osois");
                String add21 = rs.getString("nod");
                String add22 = rs.getString("dateEnded");
                String add24 = rs.getString("dateStarted");
                fname1.setText(add4);
                mnames1.setText(add5);
                lname1.setText(add6);
                building1.setText(add2);
                unitroom2.setText(add3);
                tenantid1.setText(add1);
                fname2.setText(add4);
                mname2.setText(add5);
                lname2.setText(add6);
                building2.setText(add2);
                unitroom3.setText(add3);
                tenantid2.setText(add1);
                tab.setSelectedIndex(1);
                Twomant();
                Onemant();
                WaterForTenant();              
                RentalListDate();
                WaterListDate();
                RentalForTenant();
            }
            
        }catch(Exception e){
                JOptionPane.showConfirmDialog(null, e);
        }
        
    }//GEN-LAST:event_tenantstableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          try{
            String id = tenantid1.getText();
            String rdates11 = rdates1.getSelectedItem().toString();
            String dates101 = dates1.getText();
            String sql = "Update unitrentalexpenses SET datePaid ='"+dates101+"' , urStatus='Paid' where tenantsID = '"+id+"' and monthlystart ='"+rdates11+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Tenant Rental Successfully Paid","Rental Payment",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
           String fnames1 = fname1.getText();
           String mnames1 = mname2.getText();
           String lnames1 = lname1.getText();
           String tid = tenantid1.getText();
           String rdates11 = rdates1.getSelectedItem().toString();
           File file = new File("C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Rental Payment Reciept\\"+fnames1+" "+mnames1+" "+lnames1+"");
           if(!file.exists()){
               if(file.mkdir()){
                   
               }else{
                   
               }
           }
           String fnames = fname1.getText();
           String mnames = mname2.getText();
           String lnames = lname1.getText();
           String destination = "C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Rental Payment Reciept\\"+fnames1+" "+mnames1+" "+lnames1+"\\"+fnames+" "+mnames+" "+lnames+".pdf";
           String destination2 ="\\\\192.168.254.108\\Users\\Public\\Sample Reports\\"+fnames+" "+mnames+" "+lnames+".pdf";
           JasperDesign jd = JRXmlLoader.load("C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Rental Jsp\\RentalPayment.jrxml");
           String sql="SELECT\n" +
"     unitrentalexpenses.`fname` AS unitrentalexpenses_fname,\n" +
"     unitrentalexpenses.`mname` AS unitrentalexpenses_mname,\n" +
"     unitrentalexpenses.`lname` AS unitrentalexpenses_lname,\n" +
"     unitrentalexpenses.`unitID` AS unitrentalexpenses_unitID,\n" +
"     unitrentalexpenses.`buildingID` AS unitrentalexpenses_buildingID,\n" +
"     unitrentalexpenses.`penalty` AS unitrentalexpenses_penalty,\n" +
"     unitrentalexpenses.`rentaAmount` AS unitrentalexpenses_rentaAmount,\n" +
"     unitrentalexpenses.`amountDue` AS unitrentalexpenses_amountDue,\n" +
"     unitrentalexpenses.`prevBal` AS unitrentalexpenses_prevBal,\n" +
"     unitrentalexpenses.`urStatus` AS unitrentalexpenses_urStatus,\n" +
"     unitrentalexpenses.`datePaid` AS unitrentalexpenses_datePaid,\n" +
"     unitrentalexpenses.`monthlystart` AS unitrentalexpenses_monthlystart,\n" +
"     unitrentalexpenses.`monthlyend` AS unitrentalexpenses_monthlyend,\n" +
"     buildingunits.`buildingID` AS buildingunits_buildingID,\n" +
"     buildingunits.`buildingName` AS buildingunits_buildingName,\n" +
"     buildingunits.`unitNumber` AS buildingunits_unitNumber\n" +
"FROM\n" +
"     `buildingunits` buildingunits INNER JOIN `unitrentalexpenses` unitrentalexpenses ON buildingunits.`unitID` = unitrentalexpenses.`unitID` where tenantsID='"+tid+"' AND monthlystart ='"+rdates11+"'";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);     
           JasperViewer.viewReport(jp,false);
           JasperExportManager.exportReportToPdfFile(jp, destination);
           JasperExportManager.exportReportToPdfFile(jp, destination2);
              
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        //twilio sms
        try{
            String tenantsids = tenantid1.getText();
            String sql = "Select * from tenantsinformation where tenantsID='"+tenantsids+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String cpnumber = rs.getString("contact");
                String datesss = dates1.getText();
                String fnamee = fname1.getText();
                String mnamee = mnames1.getText();
                String lnamee = lname1.getText();
                String rmants = rmonth.getText();
                String rentals = rrental.getText();
                String penaltys = rpenalty.getText();
                String amounts = rtotal.getText();
                String buildings = building1.getText();
                String unitrooms = unitroom2.getText();
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
                Message message = Message.creator(new PhoneNumber(cpnumber),//reciever
                new PhoneNumber("+18563676862"), //sender
                "Date:"+datesss+" Name:"+fnamee+" "+mnamee+" "+lnamee+" Building:"+buildings+" UnitRoom:"+unitrooms+" you have paid the rental this month "+rmants+",Rental Information: Rental:"+rentals+" Penalty:"+penaltys+" Total Amount:"+amounts+" Total Amount Paid:"+amounts+"  ").create();
                System.out.println(message.getSid());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
               String dategagos = dategago.getSelectedItem().toString();
               String tid = tenantid2.getText();
               String sql ="Update unitwatershareexpense SET uwStatus = 'Paid' where tenantsID = '"+tid+"' and psentDateread = '"+dategagos+"'";
               pst = conn.prepareStatement(sql);
               pst.execute();
               JOptionPane.showMessageDialog(null, "Tenant Successfully Paid","Water Payment",JOptionPane.INFORMATION_MESSAGE);
           }catch(Exception e){
               JOptionPane.showMessageDialog(null, e);
           }
           try{
           String fnames1 = fname1.getText();
           String mnames1 = mname2.getText();
           String lnames1 = lname1.getText();
           String tid = tenantid2.getText();
           String dategagos = dategago.getSelectedItem().toString();
           File file = new File("C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Water Payment Reciept\\"+fnames1+" "+mnames1+" "+lnames1+"");
           if(!file.exists()){
               if(file.mkdir()){
                   
               }else{
                   
               }
           }
           String fnames = fname1.getText();
           String mnames = mname2.getText();
           String lnames = lname1.getText();
           String destination = "C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Water Payment Reciept\\"+fnames1+" "+mnames1+" "+lnames1+"\\"+fnames+" "+mnames+" "+lnames+".pdf";
           
           JasperDesign jd = JRXmlLoader.load("C:\\Users\\GameDev\\Documents\\NetBeansProjects\\Makatihomes2\\Reports\\Water Reading\\WaterPayment.jrxml");
           String sql ="SELECT\n" +
"     unitwatershareexpense.`tenantsID` AS unitwatershareexpense_tenantsID,\n" +
"     unitwatershareexpense.`fname` AS unitwatershareexpense_fname,\n" +
"     unitwatershareexpense.`mname` AS unitwatershareexpense_mname,\n" +
"     unitwatershareexpense.`lname` AS unitwatershareexpense_lname,\n" +
"     unitwatershareexpense.`unitID` AS unitwatershareexpense_unitID,\n" +
"     unitwatershareexpense.`buildingID` AS unitwatershareexpense_buildingID,\n" +
"     unitwatershareexpense.`unitNumber` AS unitwatershareexpense_unitNumber,\n" +
"     unitwatershareexpense.`buildingName` AS unitwatershareexpense_buildingName,\n" +
"     unitwatershareexpense.`prevRead` AS unitwatershareexpense_prevRead,\n" +
"     unitwatershareexpense.`psentRead` AS unitwatershareexpense_psentRead,\n" +
"     unitwatershareexpense.`prevDateread` AS unitwatershareexpense_prevDateread,\n" +
"     unitwatershareexpense.`psentDateread` AS unitwatershareexpense_psentDateread,\n" +
"     unitwatershareexpense.`consumptions` AS unitwatershareexpense_consumptions,\n" +
"     unitwatershareexpense.`charge` AS unitwatershareexpense_charge,\n" +
"     unitwatershareexpense.`amountdue` AS unitwatershareexpense_amountdue,\n" +
"     unitwatershareexpense.`uwStatus` AS unitwatershareexpense_uwStatus,DATE_ADD(psentDateread, INTERVAL 5 DAY) as DueDate\n" +
"FROM\n" +
"     `unitwatershareexpense` unitwatershareexpense where tenantsID ='"+tid+"' and psentDateread = '"+dategagos+"'";
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
           //twilio sms
            try{
            String tenantsids = tenantid1.getText();
            String sql = "Select * from tenantsinformation where tenantsID='"+tenantsids+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String cpnumber = rs.getString("contact");
                String datesss = dates1.getText();
                String fnamee = fname1.getText();
                String mnamee = mnames1.getText();
                String lnamee = lname1.getText();
                String buildings = building1.getText();
                String unitrooms = unitroom2.getText();
                String consumptions = consumo1.getText();
                String charges = charge1.getText();
                String amountss = amount1.getText();
                
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
                Message message = Message.creator(new PhoneNumber(cpnumber),//reciever
                new PhoneNumber("+18563676862"), //sender
                "Date:"+datesss+" Name:"+fnamee+" "+mnamee+" "+lnamee+" Building:"+buildings+" UnitRoom:"+unitrooms+" You have paid Water bills for this month Water Bills Information: Consumption:"+consumptions+" Charges:"+charges+" Amount Paid:"+amountss+"").create();
                System.out.println(message.getSid());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amount1;
    private javax.swing.JTextField building1;
    private javax.swing.JTextField building2;
    private javax.swing.JTextField charge1;
    private javax.swing.JTextField consumo1;
    private javax.swing.JTextField date1;
    private javax.swing.JComboBox<String> dategago;
    private javax.swing.JTextField dates1;
    private javax.swing.JTextField duedate1;
    private javax.swing.JTextField fname1;
    private javax.swing.JTextField fname2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lname1;
    private javax.swing.JTextField lname2;
    private javax.swing.JTextField mname2;
    private javax.swing.JTextField mnames1;
    private javax.swing.JTextField onemonth;
    private javax.swing.JLabel onestat;
    private javax.swing.JTextField rbal;
    private javax.swing.JComboBox<String> rdates1;
    private javax.swing.JTable rentaltenants;
    private javax.swing.JTextField rmonth;
    private javax.swing.JTextField rpenalty;
    private javax.swing.JTextField rrental;
    private javax.swing.JTextField rstatus;
    private javax.swing.JTextField rtotal;
    private javax.swing.JComboBox<String> searchcombo;
    private javax.swing.JTextField status1;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextField tenantid1;
    private javax.swing.JTextField tenantid2;
    private javax.swing.JTable tenantstable;
    private javax.swing.JTextField twomonth;
    private javax.swing.JLabel twostat;
    private javax.swing.JTextField unitroom2;
    private javax.swing.JTextField unitroom3;
    private javax.swing.JTable watertenants;
    // End of variables declaration//GEN-END:variables
}
