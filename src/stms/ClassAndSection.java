package stms;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
/**
 *
 * @author User
 */
public class ClassAndSection extends javax.swing.JFrame {

    /**
     * Creates new form ClassAndSection
     */
    public ClassAndSection() {
        initComponents();
        view_data();
    }
    public void close()
    {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
    boolean verify_date_database() throws ClassNotFoundException, SQLException 
    {
        int counter=0;
        //try
        {
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            String fromDate=dateFormat.format(jDateChooser1.getDate());
            String toDate=dateFormat.format(jDateChooser2.getDate());
            String classsection=txtClass.getText();
            String dot_txt[] = fromDate.split("-");
            int month_txt = Integer.parseInt(dot_txt[1]);
            Month m_txt = Month.of(month_txt);
            
            Statement stmt=null;
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from class_funds_abcss where class_section='"+classsection+"'");
            while(rs.next())
            {
                Date dt=rs.getDate(3);
                Date dateObj = rs.getDate(3);
                String date = dateObj.toString();

                String dot[] = date.split("-");
                int month = Integer.parseInt(dot[1]);
                Month m = Month.of(month);
                if(m_txt==m)
                {
                    counter++;
                }
            }
            if(counter>0)
            {
                JOptionPane.showMessageDialog(this, "Money has already been transferred to the section in the month of "+m_txt);
                counter=0;
                return false;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Date of transfer accepted");
                return true;
            }
        }
    }
      
      
    public void clearTable()
    {
        DefaultTableModel model = (DefaultTableModel)tblClass.getModel();
        model.setRowCount(0);
    }
    
    void view_data()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            PreparedStatement pst=conn.prepareStatement("SELECT class_details_abcss.class_section,class_details_abcss.class_teacher,class_funds_abcss.strength,class_funds_abcss.date_from,class_funds_abcss.date_to,class_funds_abcss.amount_allocated FROM class_details_abcss INNER JOIN class_funds_abcss ON class_details_abcss.class_section=class_funds_abcss.class_section");
            ResultSet rs=pst.executeQuery();
            
             while(rs.next())
            {
                String classsection=rs.getString("class_details_abcss.class_section");
                int classstrength=rs.getInt("class_funds_abcss.strength");
                int amount=rs.getInt("class_funds_abcss.amount_allocated");
                SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
                Date dateFrom=rs.getDate("class_funds_abcss.date_from");
                Date dateTo=rs.getDate("class_funds_abcss.date_to");

                
                Object[] obj={classsection,classstrength,dateFrom,dateTo,amount};
                DefaultTableModel model;
                model=(DefaultTableModel)tblClass.getModel();
                model.addRow(obj);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
     
    void insert_data_class_funds()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate=dateFormat.format(jDateChooser1.getDate());
        String toDate=dateFormat.format(jDateChooser2.getDate());
        String classsection=txtClass.getText();
        int strength=Integer.parseInt(txtStrength.getText());
        int amount=Integer.parseInt(txtAmount.getText());

        Statement stmt=null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rss=stmt.executeQuery("select * from class_funds_abcss");
            while(rss.next())
            {
                PreparedStatement psts=conn.prepareStatement("insert into class_funds_abcss(class_section,strength,date_from,date_to,amount_allocated) values(?,?,?,?,?)");
                psts.setString(1,classsection);
                psts.setInt(2,strength);
                psts.setString(3,fromDate);
                psts.setString(4, toDate);
                psts.setInt(5, amount);
                psts.executeUpdate();
                JOptionPane.showMessageDialog(this,"Class funds added successfully");
            }

        }
            catch(Exception ex)
            {
               System.out.printf("");
            } 
      }
      
      void update_data_class_funds()
      {
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate=dateFormat.format(jDateChooser1.getDate());
        String toDate=dateFormat.format(jDateChooser2.getDate());
        String classsection=txtClass.getText();
        int strength=Integer.parseInt(txtStrength.getText());
        int amount=Integer.parseInt(txtAmount.getText());

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rss=stmt.executeQuery("select * from class_funds_abcss where class_section='"+classsection+"' and date_from='"+fromDate+"' and date_to='"+toDate+"'");
            while(rss.next())    
            {    
                PreparedStatement pst=conn.prepareStatement("update class_funds_abcss set strength=?, date_from=?, date_to=?, amount_allocated=? where class_section=? and date_from=? and date_to=?");
                pst.setInt(1,strength);
                pst.setString(2,fromDate);
                pst.setString(3,toDate);
                pst.setInt(4,amount);
                pst.setString(5,classsection);
                pst.setString(6, rss.getString(3));
                pst.setString(7, rss.getString(4));
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this,"Class funds updated successfully");
            }
            

        }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }
          
      }

      void delete_data_class_funds()
      {
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate=dateFormat.format(jDateChooser1.getDate());
        String toDate=dateFormat.format(jDateChooser2.getDate());
        String classsection=txtClass.getText();
        int strength=Integer.parseInt(txtStrength.getText());
        int amount=Integer.parseInt(txtAmount.getText());

        Statement stmt=null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
                PreparedStatement pst=conn.prepareStatement("delete from class_funds_abcss where class_section=? AND date_from=? AND date_to=?");
                pst.setString(1,classsection);
                pst.setString(2,fromDate);
                pst.setString(3, toDate);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this,"Class funds deleted successfully");
                jDateChooser1.setCalendar(null);
                jDateChooser2.setCalendar(null);
                txtClass.setText("");
                txtStrength.setText("");
                txtAmount.setText("");
                
          
        }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }
          
      }
      


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        txtClass = new javax.swing.JTextField();
        txtStrength = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(18, 44, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 392, 640, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-260, 390, 260, -1));

        btnUpdate.setBackground(new java.awt.Color(12, 13, 15));
        btnUpdate.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnUpdate.setText(" Update Record");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 640, 290, 100));

        btnDelete.setBackground(new java.awt.Color(12, 13, 15));
        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDelete.setText(" Delete Record");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 640, 290, 100));

        tblClass.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class Section", "Strength", "Date from", "Date to", "Amount Allocated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClassMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClass);
        if (tblClass.getColumnModel().getColumnCount() > 0) {
            tblClass.getColumnModel().getColumn(0).setResizable(false);
            tblClass.getColumnModel().getColumn(1).setResizable(false);
            tblClass.getColumnModel().getColumn(2).setResizable(false);
            tblClass.getColumnModel().getColumn(3).setResizable(false);
            tblClass.getColumnModel().getColumn(4).setResizable(false);
        }
        tblClass.setRowHeight(70);
        JTableHeader header = tblClass.getTableHeader();
        header.setFont(new Font("SansSerif",Font.BOLD,23));
        Color c1 = new Color(6,22,38);

        header.setForeground(c1);

        TableCellRenderer rendererFromHeader = tblClass.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 620));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 1210, 770));

        jPanel2.setBackground(new java.awt.Color(18, 44, 70));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAdd.setBackground(new java.awt.Color(12, 13, 15));
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add2.png"))); // NOI18N
        btnAdd.setText(" Add Record");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnAddActionPerformed(evt);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClassAndSection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClassAndSection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 640, 270, 100));

        jLabel3.setBackground(new java.awt.Color(18, 44, 70));
        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>Class<br>Section</html>");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 130, 80));

        jLabel5.setBackground(new java.awt.Color(18, 44, 70));
        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Strength");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 130, 80));

        jLabel6.setBackground(new java.awt.Color(18, 44, 70));
        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("<html>Amount<br>Allocated</html>");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 130, 80));

        txtAmount.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });
        jPanel2.add(txtAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 280, 50));

        txtClass.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jPanel2.add(txtClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 280, 50));

        txtStrength.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jPanel2.add(txtStrength, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 280, 50));

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 280, 50));

        jLabel13.setBackground(new java.awt.Color(18, 44, 70));
        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("To");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 100, 80));

        jLabel15.setBackground(new java.awt.Color(18, 44, 70));
        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("From");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 100, 80));

        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jPanel2.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 280, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 540, 770));

        jPanel3.setBackground(new java.awt.Color(6, 22, 38));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 90)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/text-book-opened-from-top-view.png"))); // NOI18N
        jLabel1.setText(" Classes and Sections");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        btnReturn.setBackground(new java.awt.Color(6, 6, 17));
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/backbtn.png"))); // NOI18N
        jPanel3.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 15, 150, 125));
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1750, 150));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        
    
        private void btnAddActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {                                       
        if(verify_date_database())
        {
            insert_data_class_funds();
            clearTable();
            view_data();
        }
    }                                      

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        update_data_class_funds();
        clearTable();
        view_data();
    }                                         

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        delete_data_class_funds();
        clearTable();
        view_data();
    }                                         

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void tblClassMouseClicked(java.awt.event.MouseEvent evt) {                                      
        int rowNo=tblClass.getSelectedRow();
        TableModel model=tblClass.getModel();
        txtClass.setText(model.getValueAt(rowNo,0).toString());
        txtStrength.setText(model.getValueAt(rowNo,1).toString());
        txtAmount.setText(model.getValueAt(rowNo,4).toString());
        jDateChooser1.setDate((Date) model.getValueAt(rowNo,2));
        jDateChooser2.setDate((Date) model.getValueAt(rowNo,3));
    }                                     
  private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        PrincipalDashboard pd=new PrincipalDashboard();
        pd.setVisible(true);
        close();
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
            java.util.logging.Logger.getLogger(ClassAndSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassAndSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassAndSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassAndSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassAndSection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnUpdate;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tblClass;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtClass;
    private javax.swing.JTextField txtStrength;
    // End of variables declaration                   
}
