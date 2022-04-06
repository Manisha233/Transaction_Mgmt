package stms;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MonthlyAllocationFund extends javax.swing.JFrame {

    
    public MonthlyAllocationFund() {
        initComponents();
        display_all();
    }
        public void close()
    {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
    void insert()
    {   
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dt = dateFormat.format(dateTo.getDate());
        String df = dateFormat.format(dateFrom.getDate());
        
        int amt=Integer.parseInt(txtAmt.getText());
        String sid="ABCSS";
        
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Transaction_Mgmt","root","");
            PreparedStatement pst=conn.prepareStatement("insert into monthly_fund_allocation(School_ID,Date_from,Date_to,Monthly_fund) values(?,?,?,?)");
            pst.setString(1,sid);
            pst.setString(2,df);
            pst.setString(3,dt);
            pst.setInt(4,amt);
            
            
            int i=pst.executeUpdate();
            if(i>0)
            {
                JOptionPane.showMessageDialog(this,"Data inserted successfully");
                //System.out.println("Data inserted successfully...");
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Data not inserted!");
                //System.out.println("Data not inserted!!!");
            }
             
        }catch(Exception ex){ex.printStackTrace();}
    }

    boolean verify_date_database() throws ClassNotFoundException, SQLException 
    {
        int counter=0;
        //try
        {
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            String datefrom=dateFormat.format(dateFrom.getDate());
            String dot_txt[] = datefrom.split("-");
            int month_txt = Integer.parseInt(dot_txt[1]);
            Month m_txt = Month.of(month_txt);
            int year_txt = Integer.parseInt(dot_txt[0]);
            Year y_txt = Year.of(year_txt);
            
            Statement stmt=null;
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from monthly_fund_allocation where school_id='abcss'");
            while(rs.next())
            {
                Date dt=rs.getDate(3);
                Date dateObj = rs.getDate(3);
                String date = dateObj.toString();

                String dot[] = date.split("-");
                int month = Integer.parseInt(dot[1]);
                Month m = Month.of(month);
                int year = Integer.parseInt(dot[0]);
                Year y = Year.of(year);
                
                if(y.equals(y_txt))
                {
                    if(m_txt==m)
                    {
//                        System.out.println(y);
//                        System.out.println(m);
                        counter++;
                    }
                }
            }
            if(counter>0)
            {
                JOptionPane.showMessageDialog(this, "Money has already been transferred to the school in the month of "+m_txt+" "+y_txt);
                counter=0;
                return false;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Date of transfer accepted");
                return true;
            }
        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
    }
    void display_all(){
        
        Statement stmt=null;
        String query=("select * from monthly_fund_allocation where school_id='abcss'" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            
            while(rs.next())
            {
               
                String schid=rs.getString("monthly_fund_allocation.School_ID");
                int amt=rs.getInt("Monthly_fund");  
                Date dF=rs.getDate("date_from");
                Date dT=rs.getDate("date_to");
                Object[] obj={schid,amt,dF,dT};
                DefaultTableModel model;
                model=(DefaultTableModel)tblFunds.getModel();
                model.addRow(obj);    
                       
            }
            
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    void display_entry(){
        
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dt = dateFormat.format(dateTo.getDate());
        String df = dateFormat.format(dateFrom.getDate());
        Statement stmt=null;
        String query=("select School_ID,Monthly_fund,Date_from,Date_to from monthly_fund_allocation where Date_to <='"+dt+"' and Date_from >='"+df+"' and school_id='abcss'" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            
            while(rs.next())
            {
               
                String schid=rs.getString("monthly_fund_allocation.School_ID");
                int amt=rs.getInt("Monthly_fund");  
                Date dF=rs.getDate("date_from");
                Date dT=rs.getDate("date_to");
                Object[] obj={schid,amt,dF,dT};
                DefaultTableModel model;
                model=(DefaultTableModel)tblFunds.getModel();
                model.addRow(obj);    
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    
    }
    public void clearTable()
    {
        DefaultTableModel model = (DefaultTableModel)tblFunds.getModel();
        model.setRowCount(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        dateTo = new com.toedter.calendar.JDateChooser();
        dateFrom = new com.toedter.calendar.JDateChooser();
        txtAmt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFunds = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(6, 6, 17));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 90)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money3.png"))); // NOI18N
        jLabel1.setText("Monthly Allocation of Fund");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        jButton2.setBackground(new java.awt.Color(6, 6, 17));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/backbtn.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 160, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 180));

        jPanel2.setBackground(new java.awt.Color(6, 6, 17));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>Amount<br>Allocated</html> ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Date To");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 120, 50));

        btnAdd.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnAdd.setText("Add Record");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnAddActionPerformed(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(MonthlyAllocationFund.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MonthlyAllocationFund.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 590, 300, 90));

        btnView.setBackground(new java.awt.Color(204, 204, 204));
        btnView.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnView.setText("View Record");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jPanel2.add(btnView, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 300, 90));

        dateTo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 26)); // NOI18N
        jPanel2.add(dateTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 280, 50));

        dateFrom.setFont(new java.awt.Font("Segoe UI Semibold", 0, 26)); // NOI18N
        jPanel2.add(dateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 280, 50));

        txtAmt.setFont(new java.awt.Font("Segoe UI Semibold", 0, 26)); // NOI18N
        txtAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmtActionPerformed(evt);
            }
        });
        jPanel2.add(txtAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 280, 50));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Date From");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 150, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 520, 730));

        jPanel4.setBackground(new java.awt.Color(18, 44, 70));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblFunds.setFont(new java.awt.Font("Segoe UI Semibold", 0, 28)); // NOI18N
        tblFunds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "School ID", "Amount Allocated", "Date From", "Date To"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFunds);
        if (tblFunds.getColumnModel().getColumnCount() > 0) {
            tblFunds.getColumnModel().getColumn(0).setResizable(false);
            tblFunds.getColumnModel().getColumn(1).setResizable(false);
            tblFunds.getColumnModel().getColumn(2).setResizable(false);
            tblFunds.getColumnModel().getColumn(3).setResizable(false);
        }
        tblFunds.setRowHeight(60);
        JTableHeader header = tblFunds.getTableHeader();
        header.setFont(new Font("SansSerif",Font.BOLD,21));
        Color c1 = new Color(6,22,38);
        header.setForeground(c1);

        TableCellRenderer rendererFromHeader = tblFunds.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 1280, 730));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 1350, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {                                        
       
        clearTable();
        display_entry();
    }                                       

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        AdminDashboard ad=new AdminDashboard();
        ad.setVisible(true);
        close();
    }                                        

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {                                       
        if(verify_date_database())
        {
            insert();
            clearTable();
            display_all();
        }
    }                                      

    private void txtAmtActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                     

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
            java.util.logging.Logger.getLogger(MonthlyAllocationFund.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonthlyAllocationFund.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonthlyAllocationFund.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonthlyAllocationFund.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonthlyAllocationFund().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnView;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFunds;
    private javax.swing.JTextField txtAmt;
    // End of variables declaration                   
}
