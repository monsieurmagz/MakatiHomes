/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makatihomes;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.applet.*;  
import static java.lang.Thread.sleep;
import java.util.*;  
import java.text.*;  
import java.util.Date;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author GameDev
 */
public class MainMenu extends javax.swing.JFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        conn=javaconnect.connectdb();
        fullscreen();
        Date();
        Clock();
        PresentEqualNow();
        Building();
        WaterListDate();
        RentalListDate();
        DisplayRentalPrice();
        DisplayPenaltyPrice();
    }
private void fullscreen(){
    MainMenu.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        MainMenu.this.setMaximizedBounds(env.getMaximumWindowBounds());
}
public void Date(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date.setText(year+"-"+(month+1)+"-"+day);
    }
    public void Clock(){
        
        Thread clock = new Thread(){
            public void run(){               
                for(;;){
                 Date date = new Date();
                 String ampm = "hh:mm:ss a";
                 SimpleDateFormat sdf = new SimpleDateFormat(ampm);
                 time.setText(sdf.format(date));
                    try{ 
                        sleep(1000);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        };
        clock.start();
    }
         private void PresentEqualNow(){
        try{
            String sql = "Select *,DATE_ADD(Max(prevDateread), INTERVAL 30 DAY) as badjao from unitwatershareexpense where DATE(Now()) <= psentDateread ORDER BY psentDateread DESC LIMIT 1  ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String bdjao = rs.getString("badjao");
                if(bdjao == null){
                    notifywaters.setText("Check the Present Water Reading before end of the month");
                }else{
                    notifywaters.setText("Water Readings must be Updated Until " + bdjao);
                }            
            }
            
       }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
             private void ListRentalBal(){
        try{
            String rdate1 = rdate.getSelectedItem().toString();
            String sql = "Select fname as FirstName,lname as LastName,rentaAmount as Rental,penalty as Penalty,prevBal as PreviousBalance,amountDue as AmountDue,urStatus as Status,monthlyend as DueDate from unitrentalexpenses where monthlystart ='"+rdate1+"' AND urStatus ='NotPaid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rtable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void ListWaterBal(){
        try{
            String waterdate = wdate.getSelectedItem().toString();
            String sql = "Select fname as FirstName,mname as MiddleName,lname as LastName,amountdue as Balance,uwStatus as Status from "
                    + "unitwatershareexpense where prevDateread ='"+waterdate+"' and uwStatus = 'NotPaid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            wtable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
            private void Building(){
        try{
            String sql = "Select * from buildings";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
            String building1 = rs.getString("buildingName");
            wbuilding.addItem(building1);
            rbuilding.addItem(building1);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
   private void WaterListDate(){
        try{
            String sql ="Select Distinct prevDateread from unitwatershareexpense";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String datess = rs.getString("prevDateread");
                wdate.addItem(datess);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void RentalListDate(){
        try{
            String sql ="Select Distinct monthlystart from unitrentalexpenses";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String rdates101 = rs.getString("monthlystart");
                rdate.addItem(rdates101);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ListRentalPaid(){
        try{
            String rpaiddate = rdate.getSelectedItem().toString();
            String sql = "Select fname as FirstName,lname as LastName,rentaAmount as Rental,penalty as Penalty,prevBal as PreviousBalance,amountDue as AmountDue,urStatus as Status,monthlyend as DueDate from unitrentalexpenses where monthlystart ='"+rpaiddate+"' AND urStatus ='Paid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rtable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ListWaterPaid(){
        try{
            String wpaiddate = wdate.getSelectedItem().toString();
            String sql = "Select fname as FirstName,mname as MiddleName,lname as LastName,amountdue as Balance,uwStatus as Status from "
                    + "unitwatershareexpense where prevDateread ='"+wpaiddate+"' and uwStatus = 'Paid'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            wtable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void DisplayRentalPrice(){
        try{
            String sql = "Select * from monthlyrentals";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String rentalprice = rs.getString("monthlyamount");
                currrental.setText("P "+rentalprice);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void DisplayPenaltyPrice(){
        try{
            String sql = "Select * from monthlypenalty";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String penaltyprice = rs.getString("penaltyAmount");
                currpenalty.setText("P "+penaltyprice);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Main = new javax.swing.JPanel();
        MainMenu = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        notifywaters = new javax.swing.JLabel();
        listselect = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        wbuilding = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        wdate = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        wtable = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rbuilding = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        rdate = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        rtable = new javax.swing.JTable();
        currpenalty = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        currrental = new javax.swing.JLabel();
        DesktopPaneHolder = new javax.swing.JPanel();
        desktop = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Makati Homes");

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/makati_logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel2.setText("MAKATI HOMES II");

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/account.png"))); // NOI18N
        jLabel3.setText("NAME");

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/building (2).png"))); // NOI18N
        jButton1.setText("Building & Unit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/tenants (2).png"))); // NOI18N
        jButton2.setText("Tenants");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/water reading.png"))); // NOI18N
        jButton3.setText("Water Reading");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Rentals.png"))); // NOI18N
        jButton4.setText("Rentals");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/directories.png"))); // NOI18N
        jButton5.setText("Directories");

        jButton6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/reports (2).png"))); // NOI18N
        jButton6.setText("Reports");

        jButton7.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/payment.png"))); // NOI18N
        jButton7.setText("Payment");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/complaint.png"))); // NOI18N
        jButton8.setText("Complaints");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/maintenance_1.png"))); // NOI18N
        jButton9.setText("Maintenance");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Main Menu.png"))); // NOI18N
        jButton11.setText("Main Menu");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(46, 46, 46))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 102));

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel5.setText("Time:");

        time.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        time.setText("jLabel6");

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel6.setText("Date:");

        date.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        date.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Logout (2).png"))); // NOI18N
        jLabel8.setText("Logout");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(time)
                .addGap(42, 42, 42)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(date)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(date)
                    .addComponent(jLabel6)
                    .addComponent(time)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        Main.setBackground(new java.awt.Color(255, 255, 255));
        Main.setLayout(new java.awt.CardLayout());

        MainMenu.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel4.setText("Water Reading Notification");

        notifywaters.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        notifywaters.setText("jLabel7");

        listselect.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        listselect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "List of Balances", "List of Paid" }));
        listselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listselectActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("Water");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel10.setText("Building:");

        wbuilding.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        wbuilding.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));

        jLabel11.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel11.setText("Select Balance Date:");

        wdate.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        wdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month" }));
        wdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wdateActionPerformed(evt);
            }
        });

        wtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(wtable);

        jButton10.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Reports.png"))); // NOI18N
        jButton10.setText("Print Water");

        jLabel12.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Rental");

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel13.setText("Building:");

        rbuilding.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbuilding.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel14.setText("Select Balance Date:");

        rdate.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month" }));
        rdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdateActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/makatihomes/Images/Reports.png"))); // NOI18N
        jButton12.setText("Print Rental");

        rtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(rtable);

        currpenalty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        currpenalty.setText("jLabel16");

        jLabel15.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel15.setText("Current Penalty Price:");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel7.setText("Current Rental Price:");

        currrental.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        currrental.setText("jLabel15");

        javax.swing.GroupLayout MainMenuLayout = new javax.swing.GroupLayout(MainMenu);
        MainMenu.setLayout(MainMenuLayout);
        MainMenuLayout.setHorizontalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainMenuLayout.createSequentialGroup()
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(listselect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(notifywaters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currrental)
                            .addComponent(currpenalty)))
                    .addGroup(MainMenuLayout.createSequentialGroup()
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainMenuLayout.createSequentialGroup()
                                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(MainMenuLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(wbuilding, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11))
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(wdate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainMenuLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbuilding, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(MainMenuLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton12)))))
                .addContainerGap(323, Short.MAX_VALUE))
        );
        MainMenuLayout.setVerticalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainMenuLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(notifywaters))
                    .addGroup(MainMenuLayout.createSequentialGroup()
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(currrental))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(currpenalty))))
                .addGap(33, 33, 33)
                .addComponent(listselect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jButton10)
                    .addComponent(jButton12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(wbuilding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(wdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(rbuilding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(rdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        Main.add(MainMenu, "card2");

        DesktopPaneHolder.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1382, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 663, Short.MAX_VALUE)
        );

        DesktopPaneHolder.add(desktop, "card2");

        Main.add(DesktopPaneHolder, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            BuildingUnits t = new BuildingUnits();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            Tenants t = new Tenants();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(MainMenu);        
        Main.repaint();
        Main.revalidate();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
          Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            WaterReading t = new WaterReading();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            Rental t = new Rental();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            Payment t = new Payment();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            Complaint t = new Complaint();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Main.removeAll();
        Main.repaint();
        Main.revalidate();
    
        Main.add(DesktopPaneHolder);        
        Main.repaint();
        Main.revalidate();
         try{
            Maintenance t = new Maintenance();
            desktop.add(t);
            desktop.moveToFront(t);
            t.setSize(desktop.getWidth(),desktop.getHeight());
            t.setLocation(0,0);
            t.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void rdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdateActionPerformed
       if(listselect.getSelectedItem().toString().equals("List of Balances")){
           ListRentalBal();
        }else if(listselect.getSelectedItem().toString().equals("List of Paid")){
            ListRentalPaid();
        }
    }//GEN-LAST:event_rdateActionPerformed

    private void wdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wdateActionPerformed
        if(listselect.getSelectedItem().toString().equals("List of Balances")){
           ListWaterBal();
        }else if(listselect.getSelectedItem().toString().equals("List of Paid")){
            ListWaterPaid();
        }
    }//GEN-LAST:event_wdateActionPerformed

    private void listselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listselectActionPerformed

    }//GEN-LAST:event_listselectActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DesktopPaneHolder;
    private javax.swing.JPanel Main;
    private javax.swing.JPanel MainMenu;
    private javax.swing.JLabel currpenalty;
    private javax.swing.JLabel currrental;
    private javax.swing.JLabel date;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JComboBox<String> listselect;
    private javax.swing.JLabel notifywaters;
    private javax.swing.JComboBox<String> rbuilding;
    private javax.swing.JComboBox<String> rdate;
    private javax.swing.JTable rtable;
    private javax.swing.JLabel time;
    private javax.swing.JComboBox<String> wbuilding;
    private javax.swing.JComboBox<String> wdate;
    private javax.swing.JTable wtable;
    // End of variables declaration//GEN-END:variables
}
