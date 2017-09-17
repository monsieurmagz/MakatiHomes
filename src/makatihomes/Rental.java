/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;

import javax.swing.JRootPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.io.File;
import javax.swing.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.proteanit.sql.DbUtils;
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
/**
 *
 * @author GameDev
 */
public class Rental extends javax.swing.JInternalFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    /**
     * Creates new form Rental
     */
    public Rental() {
        initComponents();
        conn = javaconnect.connectdb();
        removetitlebar();
        Building();
        Date();
        GetThePrice();
        PenaltyCounter();
    }
 public void removetitlebar(){
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }
     public void Date() {
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date1.setText(year + "-" + (month + 1) + "-" + day);
    }
     public void Building() {
        try {
            String sql = "Select * from buildings";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String buildingname = rs.getString("buildingName");
                buildingcombo.addItem(buildingname);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
         private void TenantsTable() {
        try {
            String buildingcombos = buildingcombo.getSelectedItem().toString();
            String sql = "Select tenantsID as TenantsID,buildingName as Building,unitNumber as Unit, fname as FirstName,mname as MiddleName,lname as LastName from tenantsinformation where buildingName ='" + buildingcombos + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            tenanttable.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      private void Total(){
        int rent = Integer.parseInt(rental.getText());
        int penaltys = Integer.parseInt(penalty.getText());
        int balances = Integer.parseInt(balance1.getText());
        int result = rent + penaltys + balances;
        total.setText(Integer.toString(result));
   
    }
          private void GetThePrice(){
        try{
            String sql = "Select * from monthlyrentals";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String amount = rs.getString("monthlyamount");
                rental.setText(amount);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
           private void PenaltyCounter(){
        try{
            String tangina = tenantsid.getText();
            String sql = "Select DATEDIFF(Date(now()) , monthlyend) as Difference from unitrentalexpenses where tenantsID = '"+tangina+"' and urStatus='NotPaid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String differences = rs.getString("Difference");
                int diffe;
                diffe = Integer.parseInt(differences.toString());
                String sql1 = "Select * from monthlypenalty";
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                while(rs.next()){
                    String pnlty = rs.getString("penaltyAmount");
                    int pnelty;
                    pnelty = Integer.parseInt(pnlty.toString());
                    int total;
                    total = pnelty * diffe;     
                    Math.max(0, total);
                    penalty.setText(Integer.toString(Math.max(0, total)));
                    
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
               private void ShowBills(){
        try{
            String tid = tenantsid.getText();
            String sql = "Select fname as FirstName,mname as MiddleName,lname as LastName,penalty as Penalty,prevBal as Balance,amountDue as TotalAmountDue,monthlystart as MonthStart,monthlyend as DueDate from unitrentalexpenses where tenantsID = '"+tid+"' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rentals.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
         private void AlreadyHaveRentMonth(){
             try{
                 String tid = tenantsid.getText();
                 String sql ="Select * from unitrentalexpense where tenantsID ='"+tid+"' and MONTH(CURRENT_DATE()) = monthlystart";
                 pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                 if(rs.next() == true){
                     JOptionPane.showMessageDialog(null, "","",JOptionPane.ERROR_MESSAGE);
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

        tabpane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buildingcombo = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenanttable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        building = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        unitnumber = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tenantsid = new javax.swing.JTextField();
        balchk = new javax.swing.JCheckBox();
        balance = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        date1 = new javax.swing.JLabel();
        rental = new javax.swing.JLabel();
        penalty = new javax.swing.JLabel();
        balance1 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        rentals = new javax.swing.JTable();

        tabpane.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Rentals");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel2.setText("Select Building:");

        buildingcombo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        buildingcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));
        buildingcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingcomboActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jTextField1.setText("Search");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/search.png"))); // NOI18N

        tenanttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tenanttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenanttableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tenanttable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buildingcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabpane.addTab("Tenants", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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

        lname.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lname.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel6.setText("Last Name:");

        unitnumber.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        unitnumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitnumber.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel7.setText("Unit Room:");

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel8.setText("Tenants ID:");

        tenantsid.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        tenantsid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenantsid.setEnabled(false);

        balchk.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        balchk.setText("Enter Previous Balance");
        balchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balchkActionPerformed(evt);
            }
        });

        balance.setText("0");
        balance.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/compute.png"))); // NOI18N
        jButton2.setText("Compute");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel9.setText("Date");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel10.setText("Rental ");

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel11.setText("Penalty");

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel12.setText("Balance");

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel13.setText("Total");

        date1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        date1.setText("Date");

        rental.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rental.setText("Rental ");

        penalty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        penalty.setText("0");

        balance1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        balance1.setText("0");

        total.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        total.setText("0");

        save.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/save.png"))); // NOI18N
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/update_1.png"))); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        rentals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(rentals);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(unitnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(balchk)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(date1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(95, 95, 95)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(rental))))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(penalty)
                                            .addComponent(jLabel11))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(balance1))
                                        .addGap(87, 87, 87)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(total)
                                            .addComponent(jLabel13))
                                        .addGap(62, 62, 62)
                                        .addComponent(save)
                                        .addGap(34, 34, 34)
                                        .addComponent(update)))))
                        .addGap(0, 55, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(unitnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(tenantsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(balchk)
                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(save, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(update))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rental))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(penalty))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(balance1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabpane.addTab("Rental", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buildingcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingcomboActionPerformed
        TenantsTable();
    }//GEN-LAST:event_buildingcomboActionPerformed

    private void balchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balchkActionPerformed
          if(balchk.isSelected() == true){
            balance.setEnabled(true);
        }else{
            balance.setEnabled(false);
    }
    }//GEN-LAST:event_balchkActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try{
             String tid = tenantsid.getText();
                 String sql3 ="Select * from unitrentalexpenses where tenantsID ='"+tid+"' and MONTH(CURRENT_DATE()) = MONTH(monthlystart)";
                 pst = conn.prepareStatement(sql3);
                 rs = pst.executeQuery();
                 if(rs.next() == false){
                     String tenantsid1 = tenantsid.getText();
                     String sql = "Select * from tenantsinformation where tenantsID = '"+tenantsid1+"'";
                     pst = conn.prepareStatement(sql);
                     rs  = pst.executeQuery();
                     while(rs.next()){
                String tenants2 = rs.getString("tenantsID");
                String buildingid2 = rs.getString("buildingID");
                String unitid2 = rs.getString("unitID");
            String sql1 = "Insert into unitrentalexpenses (tenantsID,fname,mname,lname,unitID,buildingID,rentaAmount,penalty,amountDue,prevBal,urStatus,monthlystart) values (?,?,?,?,?,?,?,?,?,?,'NotPaid',?)";
            pst = conn.prepareStatement(sql1);
            pst.setString(1, tenants2);
            pst.setString(2, fname.getText());
            pst.setString(3, mname.getText());
            pst.setString(4, lname.getText());
            pst.setString(5, unitid2);
            pst.setString(6, buildingid2);
            pst.setString(7, rental.getText());
            pst.setString(8, penalty.getText());
            pst.setString(9, total.getText());
            pst.setString(10, balance1.getText());
            pst.setString(11, date1.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Rental Bill Added","Rental Billings",JOptionPane.INFORMATION_MESSAGE);
            ShowBills();
            }
                 }else{
                     JOptionPane.showMessageDialog(null, "This Tenants has already have rentals for this month","Error Rental Billing",JOptionPane.ERROR_MESSAGE);
                 }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
         try{
            String penaltis = penalty.getText();
            String amountDue = total.getText();
            String tenantids = tenantsid.getText();
            String sql = "Update unitrentalexpenses SET penalty = '"+penaltis+"' , amountDue = '"+amountDue+"' where tenantsID = '"+tenantids+"'";
            pst = conn.prepareStatement(sql);          
            pst.execute();
            ShowBills();
            
            JOptionPane.showMessageDialog(null, "Penalty Bills Updated","Rental Billings",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
      
    
    }//GEN-LAST:event_updateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String bal = balance.getText();
        balance1.setText(bal);
        Total();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tenanttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenanttableMouseClicked
       try{
            int row = tenanttable.getSelectedRow();
            String click = (tenanttable.getModel().getValueAt(row, 0).toString());
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
                
                tenantsid.setText(add1);
                building.setText(add2);
                unitnumber.setText(add3);
                fname.setText(add4);
                mname.setText(add5);
                lname.setText(add6);
                ShowBills();
                PenaltyCounter();
                tabpane.setSelectedIndex(1);
              
            }
            
        }catch(Exception e){
                JOptionPane.showConfirmDialog(null, e);
        }
    }//GEN-LAST:event_tenanttableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField balance;
    private javax.swing.JLabel balance1;
    private javax.swing.JCheckBox balchk;
    private javax.swing.JTextField building;
    private javax.swing.JComboBox<String> buildingcombo;
    private javax.swing.JLabel date1;
    private javax.swing.JTextField fname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField mname;
    private javax.swing.JLabel penalty;
    private javax.swing.JLabel rental;
    private javax.swing.JTable rentals;
    private javax.swing.JButton save;
    private javax.swing.JTabbedPane tabpane;
    private javax.swing.JTextField tenantsid;
    private javax.swing.JTable tenanttable;
    private javax.swing.JLabel total;
    private javax.swing.JTextField unitnumber;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
